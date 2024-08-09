package com.kmks.w2.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.hutool.core.collection.CollectionUtil;
import com.kmks.w2.domain.bo.W2KsxmdmJgBo;
import com.kmks.w2.domain.vo.W2KsxmdmJgVo;
import lombok.RequiredArgsConstructor;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.core.validate.QueryGroup;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.kmks.w2.domain.vo.W2KsxmkfdmJgVo;
import com.kmks.w2.domain.bo.W2KsxmkfdmJgBo;
import com.kmks.w2.service.IW2KsxmkfdmJgService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 扣分代码
 *
 * @author lynn
 * @date 2024-04-23
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/w2/ksxmkfdmJg")
public class W2KsxmkfdmJgController extends BaseController {

    private final IW2KsxmkfdmJgService iW2KsxmkfdmJgService;

    /**
     * 查询扣分代码列表
     */
    @SaCheckPermission("w2:ksxmkfdmJg:list")
    @GetMapping("/list")
    public TableDataInfo<W2KsxmkfdmJgVo> list(W2KsxmkfdmJgBo bo, PageQuery pageQuery) {
        return iW2KsxmkfdmJgService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出扣分代码列表
     */
    @SaCheckPermission("w2:ksxmkfdmJg:export")
    @Log(title = "扣分代码", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(W2KsxmkfdmJgBo bo, HttpServletResponse response) {
        List<W2KsxmkfdmJgVo> list = iW2KsxmkfdmJgService.queryList(bo);
        ExcelUtil.exportExcel(list, "扣分代码", W2KsxmkfdmJgVo.class, response);
    }

    /**
     * 获取扣分代码详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("w2:ksxmkfdmJg:query")
    @GetMapping("/{id}")
    public R<W2KsxmkfdmJgVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(iW2KsxmkfdmJgService.queryById(id));
    }

    /**
     * 新增扣分代码
     */
    @SaCheckPermission("w2:ksxmkfdmJg:add")
    @Log(title = "扣分代码", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody W2KsxmkfdmJgBo bo) {
        return toAjax(iW2KsxmkfdmJgService.insertByBo(bo));
    }

    /**
     * 修改扣分代码
     */
    @SaCheckPermission("w2:ksxmkfdmJg:edit")
    @Log(title = "扣分代码", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody W2KsxmkfdmJgBo bo) {
        return toAjax(iW2KsxmkfdmJgService.updateByBo(bo));
    }

    /**
     * 删除扣分代码
     *
     * @param ids 主键串
     */
    @SaCheckPermission("w2:ksxmkfdmJg:remove")
    @Log(title = "扣分代码", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(iW2KsxmkfdmJgService.deleteWithValidByIds(Arrays.asList(ids), true));
    }

    /**
     * 选择查询
     *
     * @param bo
     */
    @PostMapping("/selectMap")
    public R<Map<String, W2KsxmkfdmJgVo>> selectMap(@RequestBody W2KsxmkfdmJgBo bo) {
        List<W2KsxmkfdmJgVo> w2KsxmdmJgVos = iW2KsxmkfdmJgService.queryList(bo);
        HashMap<String, W2KsxmkfdmJgVo> map = new HashMap<>();
        CollectionUtil.toMap(w2KsxmdmJgVos, map, vo -> vo.getGakfdm(), vo -> vo);
        return R.ok(map);
    }
}
