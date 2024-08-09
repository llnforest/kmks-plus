package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.annotation.Column;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.domain.event.OperLogEvent;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.service.DictService;
import com.ruoyi.common.enums.HttpMethod;
import com.ruoyi.common.utils.JsonUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.ip.AddressUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.domain.SysOperLogParam;
import com.ruoyi.system.domain.SysOperLog;
import com.ruoyi.system.domain.vo.SysOperStatisticsLogVo;
import com.ruoyi.system.mapper.SysOperLogMapper;
import com.ruoyi.system.mapper.SysOperLogParamMapper;
import com.ruoyi.system.service.ISysOperLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 操作日志 服务层处理
 *
 * @author Lion Li
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SysOperLogServiceImpl implements ISysOperLogService {

    private final SysOperLogMapper baseMapper;

    private final SysOperLogParamMapper operLogParamMapper;

    /**
     * 操作日志记录
     *
     * @param operLogEvent 操作日志事件
     */
    @Async
    @EventListener
    public void recordOper(OperLogEvent operLogEvent) {
        StringBuilder remarkBuilder = new StringBuilder();
        //根据反射及注解完善参数说明
        if(operLogEvent.getParameter() != null){
            //获取字段
            if(operLogEvent.getParameter().getType() == Long[].class || operLogEvent.getParameter().getType() == Long.class|| operLogEvent.getParameter().getType() == String.class){
                remarkBuilder.append("操作记录【"+operLogEvent.getOperParam()+"】,");
            }else{
                Field[] fields = operLogEvent.getParameter().getType().getDeclaredFields();
                Map<String,Object> dataMap = JsonUtils.parseMap(operLogEvent.getOperParam());
                if(dataMap != null) {
                    for (Field field : fields) {
                        if (ObjectUtil.isNull(dataMap.get(field.getName()))) continue;
                        //获取注解
                        Column column = field.getDeclaredAnnotation(Column.class);
                        if (column == null) continue;
                        //字典值处理
                        if (StringUtils.isNotBlank(column.dict())) {
                            remarkBuilder.append(column.value() + ":【" + SpringUtils.getBean(DictService.class).getDictLabel(column.dict(), String.valueOf(dataMap.get(field.getName())), column.separator()) + "】,");
                        } else {
                            remarkBuilder.append(column.value() + ":【" + dataMap.get(field.getName()) + "】,");

                        }
                    }
                }
            }
        }else if(operLogEvent.getRequestMethod().equals(HttpMethod.DELETE.name())){
            remarkBuilder.append("删除记录【"+operLogEvent.getOperParam()+"】,");
        }

        SysOperLog operLog = BeanUtil.toBean(operLogEvent, SysOperLog.class);
        if(remarkBuilder.length() > 0) {
            if(StringUtils.isNotBlank(operLog.getRemark())){
                remarkBuilder.append(operLog.getRemark());
            }else{
                remarkBuilder.deleteCharAt(remarkBuilder.length()-1);
            }

        }else{
            remarkBuilder.append(operLog.getRemark());
        }
        operLog.setRemark(StringUtils.substring(remarkBuilder.toString(),0,2000));
        // 远程查询操作地点
        operLog.setOperLocation(AddressUtils.getRealAddressByIP(operLog.getOperIp()));

        insertOperlog(operLog);
    }

    @Override
    public TableDataInfo<SysOperLog> selectPageOperLogList(SysOperLog operLog, PageQuery pageQuery) {
        Map<String, Object> params = operLog.getParams();
        LambdaQueryWrapper<SysOperLog> lqw = new LambdaQueryWrapper<SysOperLog>()
            .like(StringUtils.isNotBlank(operLog.getTitle()), SysOperLog::getTitle, operLog.getTitle())
            .eq(operLog.getBusinessType() != null && operLog.getBusinessType() > 0,
                SysOperLog::getBusinessType, operLog.getBusinessType())
                .eq(operLog.getOperatorType() != null && operLog.getOperatorType() >= 0,
                        SysOperLog::getOperatorType, operLog.getOperatorType())
            .func(f -> {
                if (ArrayUtil.isNotEmpty(operLog.getBusinessTypes())) {
                    f.in(SysOperLog::getBusinessType, Arrays.asList(operLog.getBusinessTypes()));
                }
            })
            .eq(operLog.getStatus() != null,
                SysOperLog::getStatus, operLog.getStatus())
            .like(StringUtils.isNotBlank(operLog.getOperName()), SysOperLog::getOperName, operLog.getOperName())
            .between(params.get("beginTime") != null && params.get("endTime") != null,
                SysOperLog::getOperTime, params.get("beginTime"), params.get("endTime"));
        if (StringUtils.isBlank(pageQuery.getOrderByColumn())) {
            pageQuery.setOrderByColumn("oper_id");
            pageQuery.setIsAsc("desc");
        }
        Page<SysOperLog> page = baseMapper.selectPage(pageQuery.build(), lqw);
        return TableDataInfo.build(page);
    }


    /**
     * 新增操作日志
     *
     * @param operLog 操作日志对象
     */
    @Override
    public void insertOperlog(SysOperLog operLog) {
        operLog.setOperTime(new Date());
        operLog.setMac(NetUtil.getLocalMacAddress());
        if(operLog.getJsonResult() != null && operLog.getJsonResult().length() > 1000)  operLog.setJsonResult("");
        baseMapper.insert(operLog);
    }


    /**
     * 查询系统操作日志集合
     *
     * @param operLog 操作日志对象
     * @return 操作日志集合
     */
    @Override
    public List<SysOperLog> selectOperLogList(SysOperLog operLog) {
        Map<String, Object> params = operLog.getParams();
        return baseMapper.selectList(new LambdaQueryWrapper<SysOperLog>()
            .like(StringUtils.isNotBlank(operLog.getTitle()), SysOperLog::getTitle, operLog.getTitle())
            .eq(operLog.getBusinessType() != null && operLog.getBusinessType() > 0,
                SysOperLog::getBusinessType, operLog.getBusinessType())
            .func(f -> {
                if (ArrayUtil.isNotEmpty(operLog.getBusinessTypes())) {
                    f.in(SysOperLog::getBusinessType, Arrays.asList(operLog.getBusinessTypes()));
                }
            })
            .eq(operLog.getStatus() != null && operLog.getStatus() > 0,
                SysOperLog::getStatus, operLog.getStatus())
            .like(StringUtils.isNotBlank(operLog.getOperName()), SysOperLog::getOperName, operLog.getOperName())
            .between(params.get("beginTime") != null && params.get("endTime") != null,
                SysOperLog::getOperTime, params.get("beginTime"), params.get("endTime"))
            .orderByDesc(SysOperLog::getOperId));
    }

    /**
     * 批量删除系统操作日志
     *
     * @param operIds 需要删除的操作日志ID
     * @return 结果
     */
    @Override
    public int deleteOperLogByIds(Long[] operIds) {
        return baseMapper.deleteBatchIds(Arrays.asList(operIds));
    }

    /**
     * 查询操作日志详细
     *
     * @param operId 操作ID
     * @return 操作日志对象
     */
    @Override
    public SysOperLog selectOperLogById(Long operId) {
        return baseMapper.selectById(operId);
    }

    /**
     * 清空操作日志
     */
    @Override
    public void cleanOperLog() {
        baseMapper.delete(new LambdaQueryWrapper<>());
    }

    @Override
    public List<SysOperStatisticsLogVo> statisticsItems() {
        return baseMapper.statisticsItems();
    }

    @Override
    @DS("param")
    public void insertParamOperlog(SysOperLogParam operLog) {
        operLog.setOperTime(new Date());
        if(operLog.getJsonResult() != null && operLog.getJsonResult().length() > 1000)  operLog.setJsonResult("");
        operLogParamMapper.insert(operLog);
    }

    @Override
    @DS("param")
    public void deleteParamOperlog(String title) {
        operLogParamMapper.delete(Wrappers.lambdaQuery(SysOperLogParam.class).eq(SysOperLogParam::getTitle,title));
    }
}
