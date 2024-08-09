package com.kmks.w2.service;
import com.kmks.w2.domain.bo.W2LogNewBo;
import com.kmks.w2.domain.vo.W2LogNewVo;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.page.TableDataInfo;

import java.util.List;

/**
 * 日志管理Service接口
 *
 * @author lynn
 * @date 2023-04-06
 */
public interface IW2LogNewService {

    public W2LogNewVo queryById(Long lIncode);

    public TableDataInfo<W2LogNewVo> queryPageList(W2LogNewBo bo, PageQuery pageQuery);

    public List<W2LogNewVo> queryList(W2LogNewBo bo);
}
