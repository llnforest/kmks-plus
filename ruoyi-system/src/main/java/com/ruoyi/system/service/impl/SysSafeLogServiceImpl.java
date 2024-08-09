package com.ruoyi.system.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.core.domain.dto.UserOnlineDTO;
import com.ruoyi.common.core.domain.event.SafeLogEvent;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.utils.redis.RedisUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.domain.vo.SysSafeLogStatVo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.bo.SysSafeLogBo;
import com.ruoyi.system.domain.vo.SysSafeLogVo;
import com.ruoyi.system.domain.SysSafeLog;
import com.ruoyi.system.mapper.SysSafeLogMapper;
import com.ruoyi.system.service.ISysSafeLogService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 安全日志Service业务层处理
 *
 * @author ghgd
 * @date 2023-03-03
 */
@RequiredArgsConstructor
@Service
public class SysSafeLogServiceImpl implements ISysSafeLogService {

    private final SysSafeLogMapper baseMapper;

    /**
     * 查询安全日志
     */
    @Override
    public SysSafeLogVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询安全日志列表
     */
    @Override
    public TableDataInfo<SysSafeLogVo> queryPageList(SysSafeLogBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<SysSafeLog> lqw = buildQueryWrapper(bo);
        Page<SysSafeLogVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询安全日志列表
     */
    @Override
    public List<SysSafeLogVo> queryList(SysSafeLogBo bo) {
        LambdaQueryWrapper<SysSafeLog> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<SysSafeLog> buildQueryWrapper(SysSafeLogBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<SysSafeLog> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getUserNo()), SysSafeLog::getUserNo, bo.getUserNo());
        lqw.like(StringUtils.isNotBlank(bo.getUserName()), SysSafeLog::getUserName, bo.getUserName());
        lqw.eq(StringUtils.isNotBlank(bo.getCategory()), SysSafeLog::getCategory, bo.getCategory());
        lqw.eq(StringUtils.isNotBlank(bo.getOperation()), SysSafeLog::getOperation, bo.getOperation());
        lqw.eq(StringUtils.isNotBlank(bo.getMac()), SysSafeLog::getMac, bo.getMac());
        lqw.orderByDesc(SysSafeLog::getCreateTime);
        return lqw;
    }

    /**
     * 新增安全日志
     */
    @Override
    public Boolean insertByBo(SysSafeLogBo bo) {
        SysSafeLog add = BeanUtil.toBean(bo, SysSafeLog.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    @Override
    public void insertSafeLog(SysSafeLog sysSafeLog) {
        sysSafeLog.setCode(SecureUtil.md5(sysSafeLog.getUserName()+sysSafeLog.getContent()));
        sysSafeLog.setCreateTime(new Date());
        baseMapper.insert(sysSafeLog);
    }

    /**
     * 修改安全日志
     */
    @Override
    public Boolean updateByBo(SysSafeLogBo bo) {
        SysSafeLog update = BeanUtil.toBean(bo, SysSafeLog.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }


    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(SysSafeLog entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除安全日志
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public List<SysSafeLogStatVo> queryListState() {
        return baseMapper.queryListState();
    }

    /**
     * 记录登录信息
     *
     * @param safeLogEvent 安全事件
     */
    @Async
    @EventListener
    public void recordSafeLog(SafeLogEvent safeLogEvent) {
        HttpServletRequest request = safeLogEvent.getRequest();
        final UserAgent userAgent = UserAgentUtil.parse(request.getHeader("User-Agent"));
        final String ip = ServletUtils.getClientIP(request);

        // 获取客户端操作系统
//        String os = userAgent.getOs().getName();
        // 获取客户端浏览器
        String browser = userAgent.getBrowser().getName();
        // 封装对象
        SysSafeLog safeLog = new SysSafeLog();
        String tokenValue =safeLogEvent.getTokenValue();
        if(ObjectUtil.isNotNull(tokenValue)){
            UserOnlineDTO dto = RedisUtils.getCacheObject(CacheConstants.ONLINE_TOKEN_KEY + tokenValue);
            safeLog.setUserName(dto.getNickName());
            safeLog.setUserNo(dto.getUserNo());
        }else{
            safeLog.setUserName(safeLogEvent.getUserName());
        }
        safeLog.setCategory(safeLogEvent.getCategory());
        safeLog.setOperation(safeLogEvent.getOperation());
        safeLog.setState(safeLogEvent.getState());
        safeLog.setYwtpye(safeLogEvent.getYwtype());
        safeLog.setUserIp(ip);
        safeLog.setMac(NetUtil.getLocalMacAddress());
        safeLog.setHostName(browser);
        safeLog.setContent(safeLogEvent.getContent());
        // 插入数据
        insertSafeLog(safeLog);
//        safeLog.setUserNo("");
    }

    /**
     * 记录登录信息
     *
     * @param username 用户名
     * @param category  操作类别
     * @param operation  操作类型
     * @param state   操作结果
     * @param ywtype  业务类别
     * @param content  消息内容
     * @return
     */
    @Override
    public void addSafeLoginEvent(String username, String category, String operation, String state,String ywtype,String content) {
        SafeLogEvent safeLogEvent = new SafeLogEvent();
        safeLogEvent.setUserName(username);
        safeLogEvent.setState(state);
        safeLogEvent.setRequest(ServletUtils.getRequest());
        safeLogEvent.setCategory(category);
        safeLogEvent.setOperation(operation);
        safeLogEvent.setYwtype(ywtype);
        safeLogEvent.setContent(content);
        safeLogEvent.setTokenValue(StpUtil.getTokenValue());
        SpringUtils.context().publishEvent(safeLogEvent);
    }
}
