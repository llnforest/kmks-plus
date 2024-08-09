package com.kmks.w2.service;

import com.kmks.w2.domain.bo.W2RecordsBo;
import com.kmks.w2.domain.dto.RecordsResetDto;
import com.kmks.w2.domain.vo.*;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 成绩管理Service接口
 *
 * @author lynn
 * @date 2023-04-06
 */
public interface IW2RecordsService {

    /**
     * 查询成绩管理
     */
    W2RecordsVo queryById(Long id);

    /**
     * 查询成绩管理列表
     */
    TableDataInfo<W2RecordsVo> queryPageList(W2RecordsBo bo, PageQuery pageQuery);

    /**
     * 查询成绩管理列表
     */
    List<W2RecordsVo> queryList(W2RecordsBo bo);

    List<PassRateVo> queryRateList(W2RecordsBo bo);

    List<PassRateTotalVo> queryRateTotalList(W2RecordsBo bo);

    List<PassRateDetailVo> queryRateDetailList(W2RecordsBo bo);

    /**
     * 新增成绩管理
     */
    Boolean insertByBo(W2RecordsBo bo);

    /**
     * 修改成绩管理
     */
    Boolean updateByBo(W2RecordsBo bo);

    /**
     * 校验并批量删除成绩管理信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);


    /**
     * 获取扣分
     * select count(id) from w2_records where instr(kfxx1,'" + info.GAKFDM + "')>0 and " + where
     */
    public Integer selectCountByGakfdm1(String gakfdm);

    /**
     * 获取扣分
     * select count(id) from w2_records where instr(kfxx2,'" + info.GAKFDM + "')>0 and " + where
     */
    public Integer selectCountByGakfdm2( String gakfdm);

    /**
     * 获取人数
     * select count(id) from w2_records where length(kfxx1)>0
     * @return
     */
    public Integer selectCountByKfxx1();

    /**
     * 获取人数
     * select count(id) from w2_records where length(kfxx2)>0
     * @return
     */
    public Integer selectCountByKfxx2();

    /**
     * 异地人员查询
     *
     *
     */
    public List<String> selectSfzByZjhm();

    List<W2FlowVo> getFlowList(String bh, String ksrq);

    Boolean resetRecord(RecordsResetDto dto);
}
