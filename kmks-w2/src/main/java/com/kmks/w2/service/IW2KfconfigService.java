package com.kmks.w2.service;

import com.kmks.w2.domain.W2Kfconfig;
import com.kmks.w2.domain.vo.W2KfconfigVo;
import com.kmks.w2.domain.bo.W2KfconfigBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 评判参数Service接口
 *
 * @author Lynn
 * @date 2023-03-21
 */
public interface IW2KfconfigService {

    /**
     * 查询评判参数
     */
    W2KfconfigVo queryById(String gakey);

    /**
     * 查询评判参数列表
     */
    TableDataInfo<W2KfconfigVo> queryPageList(W2KfconfigBo bo, PageQuery pageQuery);

    /**
     * 查询评判参数列表
     */
    List<W2KfconfigVo> queryList(W2KfconfigBo bo);

    /**
     * 新增评判参数
     */
    Boolean insertByBo(W2KfconfigBo bo);

    /**
     * 修改评判参数
     */
    Boolean updateByBo(W2KfconfigBo bo);

    List<W2Kfconfig>  queryInIds(String ids);

    /**
     * 校验并批量删除评判参数信息
     */
    Boolean deleteWithValidByIds(Collection<String> ids, Boolean isValid);


    Map<String, W2KfconfigVo>  resetKfConfigMapCache();

    W2KfconfigVo getKfConfig(String kfdm);

    Map<String, List<W2KfconfigVo>> getKfConfigByGroup();
}
