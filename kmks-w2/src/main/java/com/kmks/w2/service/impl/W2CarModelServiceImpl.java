package com.kmks.w2.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.kmks.w2.domain.ExtraData;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.utils.poi.ExcelUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.kmks.w2.domain.bo.W2CarModelBo;
import com.kmks.w2.domain.vo.W2CarModelVo;
import com.kmks.w2.domain.W2CarModel;
import com.kmks.w2.mapper.W2CarModelMapper;
import com.kmks.w2.service.IW2CarModelService;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;

/**
 * 车辆模型Service业务层处理
 *
 * @author ghgd
 * @date 2023-03-14
 */
@DS("param")
@RequiredArgsConstructor
@Service
public class W2CarModelServiceImpl implements IW2CarModelService {

    private final W2CarModelMapper baseMapper;

    /**
     * 查询车辆模型
     */
    @Override
    public W2CarModelVo queryById(String modelname){
        return baseMapper.selectVoById(modelname);
    }

    /**
     * 查询车辆模型列表
     */
    @Override
    public TableDataInfo<W2CarModelVo> queryPageList(W2CarModelBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<W2CarModel> lqw = buildQueryWrapper(bo);
        Page<W2CarModelVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询车辆模型列表
     */
    @Override
    public List<W2CarModelVo> queryList(W2CarModelBo bo) {
        LambdaQueryWrapper<W2CarModel> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }
    /**
     * 查询车辆模型列表依据模型名称
     */
    @Override
    public List<W2CarModelVo> queryListByModelName(List<String> modelNames) {
        LambdaQueryWrapper<W2CarModel> lqw = Wrappers.lambdaQuery(W2CarModel.class)
                .in(W2CarModel::getModelname, modelNames);
        return baseMapper.selectVoList(lqw);
    }

    /**
     * 查询车辆模型
     */
    @Override
    public W2CarModelVo queryOne(W2CarModelBo bo) {
        LambdaQueryWrapper<W2CarModel> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoOne(lqw);
    }

    private LambdaQueryWrapper<W2CarModel> buildQueryWrapper(W2CarModelBo bo) {
        LambdaQueryWrapper<W2CarModel> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getModelname()), W2CarModel::getModelname, bo.getModelname());
        lqw.eq(StringUtils.isNotBlank(bo.getModeltype()), W2CarModel::getModeltype, bo.getModeltype());
        lqw.eq(StringUtils.isNotBlank(bo.getState()), W2CarModel::getState, bo.getState());
        lqw.eq(StringUtils.isNotBlank(bo.getCph()), W2CarModel::getCph, bo.getCph());
        lqw.eq(bo.getRelationId()!= null,W2CarModel::getRelationId, bo.getRelationId());
        return lqw;
    }

    /**
     * 新增车辆模型
     */
    @Override
    public Boolean insertByBo(W2CarModelBo bo) {
        W2CarModel add = BeanUtil.toBean(bo, W2CarModel.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setModelname(add.getModelname());
        }
        return flag;
    }

    /**
     * 修改车辆模型
     */
    @Override
    public Boolean updateByBo(W2CarModelBo bo) {
        W2CarModel update = BeanUtil.toBean(bo, W2CarModel.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(W2CarModel entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除车辆模型
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<String> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public void exportCarModel(W2CarModelVo w2CarModelVo, HttpServletResponse response) {
        List<ExtraData> extraDataList = new ArrayList<>();
        String[] tempData = w2CarModelVo.getTemppointdata().split(";");
        for (String temp : tempData) {
            ExtraData extraData = new ExtraData();
            String[] tmp = temp.split(":");
            extraData.setRow1(tmp[0]);
            extraData.setRow2(tmp[1]);
            extraData.setRow3(tmp[2]);
            extraData.setRow4(tmp[3]);
            extraDataList.add(extraData);
        }
        ExcelUtil.exportExcel(extraDataList, "车辆模型", ExtraData.class, response);
    }
}
