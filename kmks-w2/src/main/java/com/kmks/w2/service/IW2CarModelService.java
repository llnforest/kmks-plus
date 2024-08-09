package com.kmks.w2.service;

import com.kmks.w2.domain.bo.W2CarModelBo;
import com.kmks.w2.domain.vo.W2CarModelVo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;

/**
 * 车辆模型Service接口
 *
 * @author ghgd
 * @date 2023-03-14
 */
public interface IW2CarModelService {

    /**
     * 查询车辆模型
     */
    W2CarModelVo queryById(String modelname);

    /**
     * 查询车辆模型列表
     */
    TableDataInfo<W2CarModelVo> queryPageList(W2CarModelBo bo, PageQuery pageQuery);

    /**
     * 查询车辆模型列表
     */
    List<W2CarModelVo> queryList(W2CarModelBo bo);

    List<W2CarModelVo> queryListByModelName(List<String> modelNames);

    W2CarModelVo queryOne(W2CarModelBo bo);

    /**
     * 新增车辆模型
     */
    Boolean insertByBo(W2CarModelBo bo);

    /**
     * 修改车辆模型
     */
    Boolean updateByBo(W2CarModelBo bo);

    /**
     * 校验并批量删除车辆模型信息
     */
    Boolean deleteWithValidByIds(Collection<String> ids, Boolean isValid);

    void exportCarModel(W2CarModelVo w2CarModelVo, HttpServletResponse response);
}
