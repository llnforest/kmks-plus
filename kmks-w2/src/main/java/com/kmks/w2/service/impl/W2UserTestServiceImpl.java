package com.kmks.w2.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.kmks.w2.domain.*;
import com.kmks.w2.mapper.*;
import com.ruoyi.common.constant.CacheNames;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.kmks.w2.domain.bo.W2UserTestBo;
import com.kmks.w2.domain.vo.W2UserTestVo;
import com.kmks.w2.service.IW2UserTestService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 测试数据Service业务层处理
 *
 * @author lynn
 * @date 2024-09-24
 */
@RequiredArgsConstructor
@Service
public class W2UserTestServiceImpl implements IW2UserTestService {

    private final W2UserTestMapper baseMapper;

    private final W2QueuingMapper queuingMapper;

    private final W2QueuhisMapper queuhisMapper;

    private final W2RecordsMapper recordsMapper;

    private final W2FlowrecMapper flowrecMapper;

    private final W2FlowlogMapper flowlogMapper;

    private final ISysConfigService configService;

    /**
     * 查询测试数据
     */
    @Override
    public W2UserTestVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询测试数据列表
     */
    @Override
    public TableDataInfo<W2UserTestVo> queryPageList(W2UserTestBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<W2UserTest> lqw = buildQueryWrapper(bo);
        Page<W2UserTestVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询测试数据列表
     */
    @Override
    public List<W2UserTestVo> queryList(W2UserTestBo bo) {
        LambdaQueryWrapper<W2UserTest> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<W2UserTest> buildQueryWrapper(W2UserTestBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<W2UserTest> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getXm()), W2UserTest::getXm, bo.getXm());
        lqw.like(StringUtils.isNotBlank(bo.getZjhm()), W2UserTest::getZjhm, bo.getZjhm());
        lqw.eq(StringUtils.isNotBlank(bo.getYkrq()), W2UserTest::getYkrq, bo.getYkrq());
        return lqw;
    }

    /**
     * 新增测试数据
     */
    @Transactional
    @Override
    public Boolean insertByBo(W2UserTestBo bo) {
        bo.setYkrq(DateUtil.today());
        W2Queuing w2Queuing = new W2Queuing();
        // 排队列表
        w2Queuing.setXm(bo.getXm());
        w2Queuing.setZjhm(bo.getZjhm());
        w2Queuing.setKscx(bo.getKscx());
        w2Queuing.setKsyy("A");
        w2Queuing.setKsrq(DateUtil.parse(DateUtil.today()));
        w2Queuing.setYycs("1");
        w2Queuing.setKscc(1l);
        w2Queuing.setBdxh(0l);
        w2Queuing.setKscs(1l);
        w2Queuing.setDjc(0l);
        w2Queuing.setSqks("1");
        w2Queuing.setScore(0l);
        w2Queuing.setBcyykscs("1");


        //成绩列表
        W2Records w2Records = new W2Records();
        w2Records.setXm(bo.getXm());
        w2Records.setZjhm(bo.getZjhm());
        w2Records.setKscx(bo.getKscx());
        w2Records.setKsyy("A");
        w2Records.setKsrq(DateUtil.parse(DateUtil.today()));
        w2Records.setYycs(1l);
        w2Records.setKscc("1");
        w2Records.setKscj1("0");
        w2Records.setKscj2("0");
        w2Records.setKscs(1l);
        w2Records.setBcyykscs("1");
        w2Records.setKszt("0");

        // 根据科目二科目三分别插入数据
        if (configService.selectConfigByKey(CacheNames.COURSE_KEY).equals("2,3")) {
            w2Queuing.setKskm("2");
            w2Records.setKskm("2");
            queuingMapper.insert(w2Queuing);
            recordsMapper.insert(w2Records);

            w2Queuing.setKskm("3");
            w2Records.setKskm("3");
        } else {
            w2Queuing.setKskm(configService.selectConfigByKey(CacheNames.COURSE_KEY));
            w2Records.setKskm(configService.selectConfigByKey(CacheNames.COURSE_KEY));
        }
        queuingMapper.insert(w2Queuing);
        recordsMapper.insert(w2Records);

        W2UserTest add = BeanUtil.toBean(bo, W2UserTest.class);
        validEntityBeforeSave(add);

        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改测试数据
     */
    @Override
    public Boolean updateByBo(W2UserTestBo bo) {
        W2UserTest update = BeanUtil.toBean(bo, W2UserTest.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(W2UserTest entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除测试数据
     */
    @Override
    @Transactional
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            List<W2UserTestVo> w2UserTestVos = baseMapper.selectVoBatchIds(ids, W2UserTestVo.class);
            w2UserTestVos.stream().forEach(vo -> {
                // 删除排队表中的数据
                queuingMapper.delete(Wrappers.lambdaQuery(W2Queuing.class).eq(W2Queuing::getZjhm, vo.getZjhm()).eq(W2Queuing::getKsrq, DateUtil.parse(vo.getYkrq())));
                // 删除排队历史表中的数据
                queuhisMapper.delete(Wrappers.lambdaQuery(W2Queuhis.class).eq(W2Queuhis::getZjhm, vo.getZjhm()).eq(W2Queuhis::getKsrq, DateUtil.parse(vo.getYkrq())));
                // 删除考试记录数据
                recordsMapper.delete(Wrappers.lambdaQuery(W2Records.class).eq(W2Records::getZjhm, vo.getZjhm()).eq(W2Records::getYkrq, vo.getYkrq()));
                // 删除考试明细数据
                flowrecMapper.delete(Wrappers.lambdaQuery(W2Flowrec.class).eq(W2Flowrec::getZjhm, vo.getZjhm()).eq(W2Flowrec::getYkrq, vo.getYkrq()));
                flowlogMapper.delete(Wrappers.lambdaQuery(W2Flowlog.class).eq(W2Flowlog::getZjhm, vo.getZjhm()).eq(W2Flowlog::getYkrq, vo.getYkrq()));
            });
            //TODO 做一些业务上的校验,判断是否需要校验
            // 删除排队表中的数据

            // 删除排队历史表中的数据
            // 删除考试记录数据
            // 删除考试明细数据
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
