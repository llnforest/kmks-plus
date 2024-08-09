package com.kmks.w2.controller;

import java.util.List;
import java.util.Arrays;

import lombok.RequiredArgsConstructor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.jdbc.core.JdbcTemplate;
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
import com.kmks.w2.domain.vo.W2ConfigVo;
import com.kmks.w2.domain.bo.W2ConfigBo;
import com.kmks.w2.service.IW2ConfigService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 驾考参数
 *
 * @author ruoyi
 * @date 2023-03-01
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/w2/config")
public class W2ConfigController extends BaseController {

    private final IW2ConfigService iW2ConfigService;

    @Resource
    private JdbcTemplate jdbcTemplate;

    /**
     * 测试
     */
    public List selectAll() {
        return  jdbcTemplate.queryForList("select *  from SYS.AUD$ where USERID = 'KMKSPLUS'");
    }

    /**
     * 查询驾考参数列表
     */
    @SaCheckPermission("w2:config:list")
    @GetMapping("/list")
    public TableDataInfo<W2ConfigVo> list(W2ConfigBo bo, PageQuery pageQuery) {
        return iW2ConfigService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出驾考参数列表
     */
    @SaCheckPermission("w2:config:export")
    @Log(title = "驾考参数", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(W2ConfigBo bo, HttpServletResponse response) {
        List<W2ConfigVo> list = iW2ConfigService.queryList(bo);
        ExcelUtil.exportExcel(list, "驾考参数", W2ConfigVo.class, response);
    }

    /**
     * 获取驾考参数详细信息
     *
     * @param lIncode 主键
     */
    @SaCheckPermission("w2:config:query")
    @GetMapping("/{lIncode}")
    public R<W2ConfigVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long lIncode) {
        return R.ok(iW2ConfigService.queryById(lIncode));
    }

    /**
     * 新增驾考参数
     */
    @SaCheckPermission("w2:config:add")
    @Log(title = "驾考参数", businessType = BusinessType.INSERT )
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody W2ConfigBo bo) {
        return toAjax(iW2ConfigService.insertByBo(bo));
    }

    /**
     * 修改驾考参数
     */
    @SaCheckPermission("w2:config:edit")
    @Log(title = "驾考参数", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody W2ConfigBo bo) {
        return toAjax(iW2ConfigService.updateByBo(bo));
    }

    /**
     * 删除驾考参数
     *
     * @param lIncodes 主键串
     */
    @SaCheckPermission("w2:config:remove")
    @Log(title = "驾考参数", businessType = BusinessType.DELETE)
    @DeleteMapping("/{lIncodes}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] lIncodes) {
        return toAjax(iW2ConfigService.deleteWithValidByIds(Arrays.asList(lIncodes), true));
    }


}
