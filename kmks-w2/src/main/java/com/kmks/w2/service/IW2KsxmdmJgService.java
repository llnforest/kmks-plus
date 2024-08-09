package com.kmks.w2.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kmks.w2.domain.KfCodeReport;
import com.kmks.w2.domain.vo.W2KsxmdmJgVo;
import com.kmks.w2.domain.bo.W2KsxmdmJgBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 项目代码Service接口
 *
 * @author ruoyi
 * @date 2023-03-28
 */
public interface IW2KsxmdmJgService {

    /**
     * 查询项目代码
     */
    W2KsxmdmJgVo queryById(String id);

    /**
     * 查询项目代码列表
     */
    TableDataInfo<W2KsxmdmJgVo> queryPageList(W2KsxmdmJgBo bo, PageQuery pageQuery);

    /**
     * 查询项目代码列表
     */
    List<W2KsxmdmJgVo> queryList(W2KsxmdmJgBo bo);

    /**
     * 新增项目代码
     */
    Boolean insertByBo(W2KsxmdmJgBo bo);

    /**
     * 修改项目代码
     */
    Boolean updateByBo(W2KsxmdmJgBo bo);

    /**
     * 校验并批量删除项目代码信息
     */
    Boolean deleteWithValidByIds(Collection<String> ids, Boolean isValid);

    /**
     * 误判审批统计
     * @param kskm
     * @return
     */
    TableDataInfo<W2KsxmdmJgVo> getAllKsxmByStatistics( PageQuery pageQuery);

    /**
     * 获取项目名称
     * select name from w2_ksxmdm_jg where   kskm='" + kskm + "' and custxh='" + info.KSXM + "'
     * select name from w2_ksxmdm where   kskm='" + kskm + "' and custxh='" + info.KSXM + "'"
     * @param kskm
     * @return
     */
    String getXMMCName(String kskm,String ksxm);

    /**
     * 获取统计记录
     * @return
     */
    Page<KfCodeReport>  getTotalKfCodeReport(Page page, String kskm);

    /**
     * 扣分项统计
     * @param page
     * @return
     */
    Page<W2KsxmdmJgVo>  listKsxmdmJgByLogNew(Page page);

}
