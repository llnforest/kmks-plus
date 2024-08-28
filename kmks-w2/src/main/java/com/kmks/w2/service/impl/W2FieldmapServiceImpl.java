package com.kmks.w2.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.kmks.w2.domain.ExtraData;
import com.kmks.w2.domain.W2CarModel;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.utils.poi.ExcelUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.kmks.w2.domain.bo.W2FieldmapBo;
import com.kmks.w2.domain.vo.W2FieldmapVo;
import com.kmks.w2.domain.W2Fieldmap;
import com.kmks.w2.mapper.W2FieldmapMapper;
import com.kmks.w2.service.IW2FieldmapService;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 地图模型Service业务层处理
 *
 * @author ghgd
 * @date 2023-03-15
 */
@DS("param")
@RequiredArgsConstructor
@Service
public class W2FieldmapServiceImpl implements IW2FieldmapService {

    private final W2FieldmapMapper baseMapper;

    /**
     * 查询地图模型
     */
    @Override
    public W2FieldmapVo queryById(String fieldname){
        return baseMapper.selectVoById(fieldname);
    }

    /**
     * 查询地图模型列表
     */
    @Override
    public TableDataInfo<W2FieldmapVo> queryPageList(W2FieldmapBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<W2Fieldmap> lqw = buildQueryWrapper(bo);
        Page<W2FieldmapVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询地图模型列表
     */
    @Override
    public List<W2FieldmapVo> queryList(W2FieldmapBo bo) {
        LambdaQueryWrapper<W2Fieldmap> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<W2Fieldmap> buildQueryWrapper(W2FieldmapBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<W2Fieldmap> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getFieldname()), W2Fieldmap::getFieldname, bo.getFieldname());
        lqw.eq(StringUtils.isNotBlank(bo.getFieldid()), W2Fieldmap::getFieldid, bo.getFieldid());
        lqw.eq(StringUtils.isNotBlank(bo.getFieldtype()), W2Fieldmap::getFieldtype, bo.getFieldtype());
        lqw.eq(StringUtils.isNotBlank(bo.getPointcount()), W2Fieldmap::getPointcount, bo.getPointcount());
        lqw.eq(StringUtils.isNotBlank(bo.getState()), W2Fieldmap::getState, bo.getState());
        lqw.eq(StringUtils.isNotBlank(bo.getState1()), W2Fieldmap::getState1, bo.getState1());
        lqw.eq(StringUtils.isNotBlank(bo.getLineno()), W2Fieldmap::getLineno, bo.getLineno());
        lqw.eq(StringUtils.isNotBlank(bo.getScode()), W2Fieldmap::getScode, bo.getScode());

        lqw.like(StringUtils.isNotBlank(bo.getSchoolname()), W2Fieldmap::getSchoolname, bo.getSchoolname());
        lqw.eq(StringUtils.isNotBlank(bo.getTemppointdata()), W2Fieldmap::getTemppointdata, bo.getTemppointdata());
        lqw.eq(StringUtils.isNotBlank(bo.getPointdata()), W2Fieldmap::getPointdata, bo.getPointdata());
        lqw.eq(bo.getRelationId()!= null, W2Fieldmap::getRelationId, bo.getRelationId());
        return lqw;
    }

    /**
     * 新增地图模型
     */
    @Override
    public Boolean insertByBo(W2FieldmapBo bo) {
        W2Fieldmap add = BeanUtil.toBean(bo, W2Fieldmap.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setFieldname(add.getFieldname());
        }
        return flag;
    }

    /**
     * 修改地图模型
     */
    @Override
    public Boolean updateByBo(W2FieldmapBo bo) {
        W2Fieldmap update = BeanUtil.toBean(bo, W2Fieldmap.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(W2Fieldmap entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除地图模型
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<String> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }


    @Override
    public void exportFileMapModel(W2FieldmapVo w2FieldmapVo, HttpServletResponse response) {
        List<ExtraData> extraDataList = new ArrayList<>();
        String[] tempData = w2FieldmapVo.getTemppointdata().split(";");
        for (String temp : tempData) {
            ExtraData extraData = new ExtraData();
            String[] tmp = temp.split(":");
            extraData.setRow1(tmp[0]);
            extraData.setRow2(tmp[1]);
            extraData.setRow3(tmp[2]);
            extraData.setRow4(tmp[3]);
            extraDataList.add(extraData);
        }
        ExcelUtil.exportExcel(extraDataList, "地图模型", ExtraData.class, response);
    }
}
