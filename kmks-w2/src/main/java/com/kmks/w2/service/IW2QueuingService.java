package com.kmks.w2.service;

import com.kmks.w2.domain.bo.W2KcxxBo;
import com.kmks.w2.domain.bo.W2RecordsBo;
import com.kmks.w2.domain.dto.SplitCarDto;
import com.kmks.w2.domain.vo.W2KcxxVo;
import com.kmks.w2.domain.vo.W2QueuingVo;
import com.kmks.w2.domain.bo.W2QueuingBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 排队信息Service接口
 *
 * @author Lynn
 * @date 2023-03-28
 */
public interface IW2QueuingService {

    /**
     * 查询排队信息
     */
    W2QueuingVo queryById(Long id);

    /**
     * 查询排队信息列表
     */
    TableDataInfo<W2QueuingVo> queryPageList(W2QueuingBo bo, PageQuery pageQuery);

    /**
     * 查询排队信息列表
     */
    List<W2QueuingVo> queryList(W2QueuingBo bo);

    /**
     * 新增排队信息
     */
    Boolean insertByBo(W2QueuingBo bo);

    /**
     * 修改排队信息
     */
    Boolean updateByBo(W2QueuingBo bo);

    /**
     * 校验并批量删除排队信息信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 获取车辆对应考试人数
     */
    Map<String,Long >  getCarPersonNumMap(W2QueuingBo bo);

    /**
     * 查询约考列表
     */
    TableDataInfo<W2QueuingVo> queryPageListByAbout(W2QueuingBo bo, PageQuery pageQuery);

    /**
     * 查询任务调度列表
     */
    TableDataInfo<W2QueuingVo> queryPageListByTask(W2QueuingBo bo, PageQuery pageQuery);

    Boolean applyExam(String ids);

    /**
     * 取消考试
     */
    Boolean cancelExam(String ids);

    /**
     * 换车
     */
    Boolean changeByBo(W2QueuingBo bo);

    /**
     * 随机换车
     */
    Boolean randomChangeByBo(W2QueuingBo bo);

    /**
     * 上下移动
     */
    Boolean upDownBdxh(Long newIndex,Long oldIndex,Long id);

    /**
     * 考车列表
     **/
    List<W2KcxxVo> queryCarList(W2KcxxBo bo);

    Long getMaxBdxh();

    void handleImportData(List<W2QueuingBo> queuingBos, List<W2RecordsBo> recordsBos);

    void syncToHistory();
}
