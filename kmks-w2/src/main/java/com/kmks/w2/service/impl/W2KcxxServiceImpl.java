package com.kmks.w2.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.kmks.w2.domain.dto.JudgeDto;
import com.kmks.w2.netty.handler.MessageHandler;
import com.kmks.w2.utils.TcpUtils;
import com.ruoyi.common.constant.CacheNames;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.utils.bean.BeanHelper;
import com.ruoyi.system.service.impl.SysConfigServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.kmks.w2.domain.bo.W2KcxxBo;
import com.kmks.w2.domain.vo.W2KcxxVo;
import com.kmks.w2.domain.W2Kcxx;
import com.kmks.w2.mapper.W2KcxxMapper;
import com.kmks.w2.service.IW2KcxxService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 过程明细Service业务层处理
 *
 * @author lynn
 * @date 2023-03-14
 */

@RequiredArgsConstructor
@Service
public class W2KcxxServiceImpl implements IW2KcxxService {

    private final W2KcxxMapper baseMapper;

    private final SysConfigServiceImpl configService;


    /**
     * 查询过程明细
     */
    @Override
    public W2KcxxVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询过程明细列表
     */
    @Override
    public TableDataInfo<W2KcxxVo> queryPageList(W2KcxxBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<W2Kcxx> lqw = buildQueryWrapper(bo);
        Page<W2KcxxVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询过程明细列表
     */
    @Override
    public List<W2KcxxVo> queryList(W2KcxxBo bo) {
        LambdaQueryWrapper<W2Kcxx> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    /**
     * 查询过程明细
     */
    @Override
    public W2KcxxVo queryOne(W2KcxxBo bo) {
        LambdaQueryWrapper<W2Kcxx> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoOne(lqw);
    }

    /**
     * 是否存在
     *
     * @param bo bo
     * @return {@link Boolean}
     */
    @Override
    public Boolean exists(W2KcxxBo bo) {
        return baseMapper.exists(buildQueryWrapper(bo));
    }

    private LambdaQueryWrapper<W2Kcxx> buildQueryWrapper(W2KcxxBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<W2Kcxx> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getKch()), W2Kcxx::getKch, bo.getKch());
        lqw.eq(StringUtils.isNotBlank(bo.getCph()), W2Kcxx::getCph, bo.getCph());
        lqw.like(StringUtils.isNotBlank(bo.getKcmc()), W2Kcxx::getKcmc, bo.getKcmc());
        lqw.eq(StringUtils.isNotBlank(bo.getKscx()), W2Kcxx::getKscx, bo.getKscx());
        lqw.eq(StringUtils.isNotBlank(bo.getZt()), W2Kcxx::getZt, bo.getZt());
        lqw.eq(StringUtils.isNotBlank(bo.getCarIp()), W2Kcxx::getCarIp, bo.getCarIp());
        lqw.eq(StringUtils.isNotBlank(bo.getCarMac()), W2Kcxx::getCarMac, bo.getCarMac());
        lqw.eq(StringUtils.isNotBlank(bo.getCarVersion()), W2Kcxx::getCarVersion, bo.getCarVersion());
        lqw.eq(W2Kcxx::getSchoolId,configService.selectConfigByKey(CacheNames.SCHOOL_ID));
        lqw.orderByAsc(W2Kcxx::getKch);
        return lqw;
    }

    /**
     * 新增过程明细
     */
    @Override
    public Boolean insertByBo(W2KcxxBo bo) {
        W2Kcxx add = BeanUtil.toBean(bo, W2Kcxx.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改过程明细
     */
    @Override
    public Boolean updateByBo(W2KcxxBo bo) {
        W2Kcxx update = BeanUtil.toBean(bo, W2Kcxx.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(W2Kcxx entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除过程明细
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    /**
     * 下发评判
     *
     * @param judgeDto 法官dto
     * @return {@link Boolean}
     */
    @Override
    public Boolean judge(JudgeDto judgeDto) {
        MessageHandler bean = BeanHelper.getBean(MessageHandler.class);
        // 指令标志;车辆编号;时间;身份证;姓名;
        String[] message = new String[]{judgeDto.getKcbh(), DateUtil.now(), judgeDto.getGakfdm(), judgeDto.getKf(), judgeDto.getKfxx()};
        bean.zx6000(TcpUtils.getCarChannel(judgeDto.getKcbh()),"6000",message);
        return true;
    }

    /**
     * 新增考车自检
     */
    @Override
    public Boolean resetKcxxCheck() {
        LambdaUpdateWrapper<W2Kcxx> lqw = Wrappers.lambdaUpdate(W2Kcxx.class)
                .set(W2Kcxx::getCheckResult1, 0)
                .set(W2Kcxx::getCheckResult2, 0)
                .set(W2Kcxx::getCheckResult3, 0)
                .set(W2Kcxx::getCheckTime, "")
                .set(W2Kcxx::getCheckTime1, "");
        return baseMapper.update(null,lqw) > 0;
    }
}
