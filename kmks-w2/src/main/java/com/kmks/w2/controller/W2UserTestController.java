package com.kmks.w2.controller;

import java.util.List;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

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
import com.kmks.w2.domain.vo.W2UserTestVo;
import com.kmks.w2.domain.bo.W2UserTestBo;
import com.kmks.w2.service.IW2UserTestService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 测试数据
 *
 * @author lynn
 * @date 2024-09-24
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/w2/userTest")
public class W2UserTestController extends BaseController {

    private final IW2UserTestService iW2UserTestService;

    /**
     * 查询测试数据列表
     */
    @SaCheckPermission("w2:userTest:list")
    @GetMapping("/list")
    public TableDataInfo<W2UserTestVo> list(W2UserTestBo bo, PageQuery pageQuery) {
        return iW2UserTestService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出测试数据列表
     */
    @SaCheckPermission("w2:userTest:export")
    @Log(title = "测试数据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(W2UserTestBo bo, HttpServletResponse response) {
        List<W2UserTestVo> list = iW2UserTestService.queryList(bo);
        ExcelUtil.exportExcel(list, "测试数据", W2UserTestVo.class, response);
    }

    /**
     * 获取测试数据详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("w2:userTest:query")
    @GetMapping("/{id}")
    public R<W2UserTestVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(iW2UserTestService.queryById(id));
    }

    /**
     * 新增测试数据
     */
    @SaCheckPermission("w2:userTest:add")
    @Log(title = "测试数据", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody W2UserTestBo bo) {
        return toAjax(iW2UserTestService.insertByBo(bo));
    }

    /**
     * 修改测试数据
     */
    @SaCheckPermission("w2:userTest:edit")
    @Log(title = "测试数据", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody W2UserTestBo bo) {
        return toAjax(iW2UserTestService.updateByBo(bo));
    }

    /**
     * 删除测试数据
     *
     * @param ids 主键串
     */
    @SaCheckPermission("w2:userTest:remove")
    @Log(title = "测试数据", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(iW2UserTestService.deleteWithValidByIds(Arrays.asList(ids), true));
    }
}
