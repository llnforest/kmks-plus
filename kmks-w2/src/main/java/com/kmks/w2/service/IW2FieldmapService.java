package com.kmks.w2.service;

import com.kmks.w2.domain.bo.W2FieldmapBo;
import com.kmks.w2.domain.vo.W2FieldmapVo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;

/**
 * 地图模型Service接口
 *
 * @author ghgd
 * @date 2023-03-15
 */
public interface IW2FieldmapService {

    /**
     * 查询地图模型
     */
    W2FieldmapVo queryById(String fieldname);

    /**
     * 查询地图模型列表
     */
    TableDataInfo<W2FieldmapVo> queryPageList(W2FieldmapBo bo, PageQuery pageQuery);

    /**
     * 查询地图模型列表
     */
    List<W2FieldmapVo> queryList(W2FieldmapBo bo);

    /**
     * 新增地图模型
     */
    Boolean insertByBo(W2FieldmapBo bo);

    /**
     * 修改地图模型
     */
    Boolean updateByBo(W2FieldmapBo bo);

    /**
     * 校验并批量删除地图模型信息
     */
    Boolean deleteWithValidByIds(Collection<String> ids, Boolean isValid);

    public void exportFileMapModel(W2FieldmapVo w2FieldmapVo, HttpServletResponse response);
}
