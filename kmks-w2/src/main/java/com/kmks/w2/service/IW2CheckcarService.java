package com.kmks.w2.service;

import com.kmks.w2.domain.bo.W2CheckcarBo;
import com.kmks.w2.domain.vo.W2CheckcarVo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 车辆自检Service接口
 *
 * @author ruoyi
 * @date 2023-03-28
 */
public interface IW2CheckcarService {

    /**
     * 查询车辆自检
     */
    W2CheckcarVo queryById(String sCarno);

    /**
     * 查询车辆自检列表
     */
    TableDataInfo<W2CheckcarVo> queryPageList(W2CheckcarBo bo, PageQuery pageQuery);

    /**
     * 查询车辆自检列表
     */
    List<W2CheckcarVo> queryList(W2CheckcarBo bo);

    /**
     * 新增车辆自检
     */
    Boolean insertByBo(W2CheckcarBo bo);

    /**
     * 修改车辆自检
     */
    Boolean updateByBo(W2CheckcarBo bo);

    /**
     * 校验并批量删除车辆自检信息
     */
    Boolean deleteWithValidByIds(Collection<String> ids, Boolean isValid);
}
