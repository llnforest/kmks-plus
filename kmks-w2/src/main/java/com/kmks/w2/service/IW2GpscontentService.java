package com.kmks.w2.service;

import com.kmks.w2.domain.vo.W2GpscontentVo;
import com.kmks.w2.domain.bo.W2GpscontentBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 轨迹数据Service接口
 *
 * @author ruoyi
 * @date 2023-04-19
 */
public interface IW2GpscontentService {

    /**
     * 查询轨迹数据
     */
    W2GpscontentVo queryById(Long timeid);

    /**
     * 查询轨迹数据列表
     */
    TableDataInfo<W2GpscontentVo> queryPageList(W2GpscontentBo bo, PageQuery pageQuery);

    /**
     * 查询轨迹数据列表
     */
    List<W2GpscontentVo> queryList(W2GpscontentBo bo);

    <T> List<T> queryListByTable(W2GpscontentBo bo, Class<T> clazz);

    /**
     * 新增轨迹数据
     */
    Boolean insertByBo(W2GpscontentBo bo);

    /**
     * 修改轨迹数据
     */
    Boolean updateByBo(W2GpscontentBo bo);

    /**
     * 校验并批量删除轨迹数据信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
