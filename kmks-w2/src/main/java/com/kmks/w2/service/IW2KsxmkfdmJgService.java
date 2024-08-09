package com.kmks.w2.service;

import com.kmks.w2.domain.W2KsxmkfdmJg;
import com.kmks.w2.domain.vo.W2KsxmkfdmJgVo;
import com.kmks.w2.domain.bo.W2KsxmkfdmJgBo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 扣分代码Service接口
 *
 * @author lynn
 * @date 2024-04-23
 */
public interface IW2KsxmkfdmJgService {

    /**
     * 查询扣分代码
     */
    W2KsxmkfdmJgVo queryById(Long id);

    /**
     * 查询扣分代码列表
     */
    TableDataInfo<W2KsxmkfdmJgVo> queryPageList(W2KsxmkfdmJgBo bo, PageQuery pageQuery);

    /**
     * 查询扣分代码列表
     */
    List<W2KsxmkfdmJgVo> queryList(W2KsxmkfdmJgBo bo);

    /**
     * 新增扣分代码
     */
    Boolean insertByBo(W2KsxmkfdmJgBo bo);

    /**
     * 修改扣分代码
     */
    Boolean updateByBo(W2KsxmkfdmJgBo bo);

    /**
     * 校验并批量删除扣分代码信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
