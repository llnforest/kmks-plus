package com.kmks.w2.service;

import com.kmks.w2.domain.vo.W2ConfigVo;
import com.kmks.w2.domain.bo.W2ConfigBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 驾考参数Service接口
 *
 * @author ruoyi
 * @date 2023-03-01
 */
public interface IW2ConfigService {

    /**
     * 查询驾考参数
     */
    W2ConfigVo queryById(Long lIncode);

    /**
     * 查询驾考参数列表
     */
    TableDataInfo<W2ConfigVo> queryPageList(W2ConfigBo bo, PageQuery pageQuery);

    /**
     * 查询驾考参数列表
     */
    List<W2ConfigVo> queryList(W2ConfigBo bo);

    /**
     * 新增驾考参数
     */
    Boolean insertByBo(W2ConfigBo bo);

    /**
     * 修改驾考参数
     */
    Boolean updateByBo(W2ConfigBo bo);

    /**
     * 校验并批量删除驾考参数信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    Map<String,String> getCodeValueMap(W2ConfigBo bo);

    //设置报表开头信息
    void setReportConfigInfo(Map<String, String> map);
}
