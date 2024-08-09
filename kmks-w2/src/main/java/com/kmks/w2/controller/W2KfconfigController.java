package com.kmks.w2.controller;

import java.util.List;
import java.util.Arrays;
import java.util.Map;

import com.kmks.w2.service.IW2KfconfigService;
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
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.kmks.w2.domain.vo.W2KfconfigVo;
import com.kmks.w2.domain.bo.W2KfconfigBo;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 评判参数
 *
 * @author Lynn
 * @date 2023-03-21
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/w2/kfconfig")
public class W2KfconfigController extends BaseController {

    private final IW2KfconfigService iW2KfconfigService;

    /**
     * 查询评判参数列表
     */
    @SaCheckPermission({"w2:kfconfigk2program:list","w2:kfconfigk3program:list","w2:kfconfigk2special:list","w2:kfconfigk3special:list","w2:kfconfigk2common:list","w2:kfconfigk3common:list","w2:kfconfigk2province:list","w2:kfconfigk3province:list","w2:kfconfigk2car:list","w2:kfconfigk3car:list"})
    @GetMapping("/list")
    public TableDataInfo<W2KfconfigVo> list(W2KfconfigBo bo, PageQuery pageQuery) {
        return iW2KfconfigService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出评判参数列表
     */
    @SaCheckPermission({"w2:kfconfigk2program:export","w2:kfconfigk3program:export","w2:kfconfigk2special:export","w2:kfconfigk3special:export","w2:kfconfigk2common:export","w2:kfconfigk3common:export","w2:kfconfigk2province:export","w2:kfconfigk3province:export","w2:kfconfigk2car:export","w2:kfconfigk3car:export"})
    @Log(title = "评判参数", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(W2KfconfigBo bo, HttpServletResponse response) {
        List<W2KfconfigVo> list = iW2KfconfigService.queryList(bo);
        //根据参数调整列数
        ExcelUtil.exportExcel(list, "评判参数", W2KfconfigVo.class, response,"1");
    }

    /**
     * 获取评判参数详细信息
     *
     * @param gakey 主键
     */
    @SaCheckPermission({"w2:kfconfigk2program:query","w2:kfconfigk3program:query","w2:kfconfigk2special:query","w2:kfconfigk3special:query","w2:kfconfigk2common:query","w2:kfconfigk3common:query","w2:kfconfigk2province:query","w2:kfconfigk3province:query","w2:kfconfigk2car:query","w2:kfconfigk3car:query"})
    @GetMapping("/{gakey}")
    public R<W2KfconfigVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable String gakey) {
        return R.ok(iW2KfconfigService.queryById(gakey));
    }

    /**
     * 新增评判参数
     */
    @SaCheckPermission({"w2:kfconfigk2program:add","w2:kfconfigk3program:add","w2:kfconfigk2special:add","w2:kfconfigk3special:add","w2:kfconfigk2common:add","w2:kfconfigk3common:add","w2:kfconfigk2province:add","w2:kfconfigk3province:add","w2:kfconfigk2car:add","w2:kfconfigk3car:add"})
    @Log(title = "评判参数", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody W2KfconfigBo bo) {
        return toAjax(iW2KfconfigService.insertByBo(bo));
    }

    /**
     * 修改评判参数
     */
    @SaCheckPermission({"w2:kfconfigk2program:edit","w2:kfconfigk3program:edit","w2:kfconfigk2special:edit","w2:kfconfigk3special:edit","w2:kfconfigk2common:edit","w2:kfconfigk3common:edit","w2:kfconfigk2province:edit","w2:kfconfigk3province:edit","w2:kfconfigk2car:edit","w2:kfconfigk3car:edit"})
    @Log(title = "评判参数", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody W2KfconfigBo bo) {
        return toAjax(iW2KfconfigService.updateByBo(bo));
    }

    /**
     * 删除评判参数
     *
     * @param gakeys 主键串
     */
    @SaCheckPermission({"w2:kfconfigk2program:remove","w2:kfconfigk3program:remove","w2:kfconfigk2special:remove","w2:kfconfigk3special:remove","w2:kfconfigk2common:remove","w2:kfconfigk3common:remove","w2:kfconfigk2province:remove","w2:kfconfigk3province:remove","w2:kfconfigk2car:remove","w2:kfconfigk3car:remove"})
    @Log(title = "评判参数", businessType = BusinessType.DELETE)
    @DeleteMapping("/{gakeys}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable String[] gakeys) {
        return toAjax(iW2KfconfigService.deleteWithValidByIds(Arrays.asList(gakeys), true));
    }

    /**
     * 按组获取kfdm
     *
     * @return {@link R}<{@link Map}<{@link String},{@link List}<{@link W2KfconfigVo}>>>
     */
    @GetMapping("/getKfdmByGroup")
    public R<Map<String,List<W2KfconfigVo>>> getKfdmByGroup() {
        return R.ok(iW2KfconfigService.getKfConfigByGroup());
    }
}
