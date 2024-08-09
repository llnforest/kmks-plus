package com.kmks.w2.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kmks.w2.domain.vo.W2CdxmbhVo;
import com.kmks.w2.domain.EquipmentBean;
import com.kmks.w2.domain.W2Cdxmbh;
import com.ruoyi.common.core.mapper.BaseMapperPlus;

/**
 * 场地项目编号Mapper接口
 *
 * @author ruoyi
 * @date 2023-03-28
 */
public interface W2CdxmbhMapper extends BaseMapperPlus<W2CdxmbhMapper, W2Cdxmbh, W2CdxmbhVo> {
    Page<EquipmentBean> listCdxmbhByLogNew(Page page);
}
