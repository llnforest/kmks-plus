package com.kmks.w2.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.kmks.w2.domain.KfCodeReport;
import com.ruoyi.common.constant.CacheNames;
import com.ruoyi.common.helper.LoginHelper;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.system.service.impl.SysConfigServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.kmks.w2.domain.bo.W2KsxmdmJgBo;
import com.kmks.w2.domain.vo.W2KsxmdmJgVo;
import com.kmks.w2.domain.W2KsxmdmJg;
import com.kmks.w2.mapper.W2KsxmdmJgMapper;
import com.kmks.w2.service.IW2KsxmdmJgService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 项目代码Service业务层处理
 *
 * @author ruoyi
 * @date 2023-03-28
 */
@RequiredArgsConstructor
@Service
public class W2KsxmdmJgServiceImpl implements IW2KsxmdmJgService {

    private final W2KsxmdmJgMapper baseMapper;
    private final SysConfigServiceImpl configService;

    /**
     * 查询项目代码
     */
    @Override
    public W2KsxmdmJgVo queryById(String id) {
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询项目代码列表
     */
    @Override
    public TableDataInfo<W2KsxmdmJgVo> queryPageList(W2KsxmdmJgBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<W2KsxmdmJg> lqw = buildQueryWrapper(bo);
        Page<W2KsxmdmJgVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询项目代码列表
     */
    @Override
    public List<W2KsxmdmJgVo> queryList(W2KsxmdmJgBo bo) {
        LambdaQueryWrapper<W2KsxmdmJg> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<W2KsxmdmJg> buildQueryWrapper(W2KsxmdmJgBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<W2KsxmdmJg> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getName()), W2KsxmdmJg::getName, bo.getName());
        if(StringUtils.isNotBlank(bo.getKskm())){
            lqw.in( W2KsxmdmJg::getKskm, bo.getKskm().split(","));
        }else{
            lqw.in(W2KsxmdmJg::getKskm, configService.selectConfigByKey(CacheNames.COURSE_KEY).split(","));
        }
        lqw.eq(StringUtils.isNotBlank(bo.getCustxh()), W2KsxmdmJg::getCustxh, bo.getCustxh());
        lqw.eq(bo.getKmtime1() != null, W2KsxmdmJg::getKmtime1, bo.getKmtime1());
        lqw.eq(bo.getKmtime2() != null, W2KsxmdmJg::getKmtime2, bo.getKmtime2());
        lqw.eq(bo.getKmtime3() != null, W2KsxmdmJg::getKmtime3, bo.getKmtime3());
        lqw.orderByAsc(W2KsxmdmJg::getId);
        return lqw;
    }

    /**
     * 新增项目代码
     */
    @Override
    public Boolean insertByBo(W2KsxmdmJgBo bo) {
        W2KsxmdmJg add = BeanUtil.toBean(bo, W2KsxmdmJg.class);
        validEntityBeforeSave(add);
        LambdaQueryWrapper<W2KsxmdmJg> eq = Wrappers.lambdaQuery(W2KsxmdmJg.class).eq(W2KsxmdmJg::getId, add.getId());
        boolean flag = false;
        if(baseMapper.exists(eq)){
            flag = baseMapper.updateById(add) > 0;
        }else{
            flag = baseMapper.insert(add) > 0;
        }
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改项目代码
     */
    @Override
    public Boolean updateByBo(W2KsxmdmJgBo bo) {
        W2KsxmdmJg update = BeanUtil.toBean(bo, W2KsxmdmJg.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(W2KsxmdmJg entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除项目代码
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<String> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public TableDataInfo<W2KsxmdmJgVo> getAllKsxmByStatistics( PageQuery pageQuery) {
        LambdaQueryWrapper<W2KsxmdmJg> lqw = Wrappers.lambdaQuery();
        lqw.ne(W2KsxmdmJg::getId, 10000);
        lqw.ne(W2KsxmdmJg::getId, 30000);
        lqw.eq(W2KsxmdmJg::getKskm, "3");
        Page<W2KsxmdmJgVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }



    @Override
    public String getXMMCName(String kskm,String ksxm) {
        LambdaQueryWrapper<W2KsxmdmJg> lqw = Wrappers.lambdaQuery();
        lqw.eq(StrUtil.isNotBlank(kskm),W2KsxmdmJg::getKskm,kskm);
        lqw.eq(StrUtil.isNotBlank(ksxm),W2KsxmdmJg::getCustxh,ksxm);
        W2KsxmdmJgVo w2KsxmdmJg = baseMapper.selectVoOne(lqw);
        return w2KsxmdmJg.getName();
    }

    @Override
    public Page<KfCodeReport>  getTotalKfCodeReport(Page page, String kskm) {
        return baseMapper.getTotalKfCodeReport(page,kskm);
    }

    @Override
    public Page<W2KsxmdmJgVo> listKsxmdmJgByLogNew(Page page) {
        return baseMapper.listKsxmdmJgByLogNew(page);
    }


}
