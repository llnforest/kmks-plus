package com.kmks.w2.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kmks.w2.domain.EquipmentBean;
import com.kmks.w2.domain.vo.W2CdxmbhVo;
import com.kmks.w2.domain.bo.W2CdxmbhBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 场地项目编号Service接口
 *
 * @author ruoyi
 * @date 2023-03-28
 */
public interface IW2CdxmbhService {

    /**
     * 查询场地项目编号
     */
    W2CdxmbhVo queryById(Long nid);

    /**
     * 查询场地项目编号列表
     */
    TableDataInfo<W2CdxmbhVo> queryPageList(W2CdxmbhBo bo, PageQuery pageQuery);

    /**
     * 查询场地项目编号列表
     */
    List<W2CdxmbhVo> queryList(W2CdxmbhBo bo);

    /**
     * 新增场地项目编号
     */
    Boolean insertByBo(W2CdxmbhBo bo);

    /**
     * 修改场地项目编号
     */
    Boolean updateByBo(W2CdxmbhBo bo);

    /**
     * 校验并批量删除场地项目编号信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);


    /**
     * 获取设备项统计
     *
     */
    Page<EquipmentBean> listCdxmbhByLogNew(Page page);

    Map<String, W2CdxmbhVo> resetCdxmConfigMapCache();

    W2CdxmbhVo getCdxmConfig(String paramDm);

    String getMdmByGadm(List gadms);
}
