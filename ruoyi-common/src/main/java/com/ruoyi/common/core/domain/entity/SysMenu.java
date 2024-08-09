package com.ruoyi.common.core.domain.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ruoyi.common.annotation.Column;
import com.ruoyi.common.core.domain.TreeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 菜单权限表 sys_menu
 *
 * @author Lion Li
 */

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_menu")
@KeySequence(value = "SEQ_ORA_INTEGER_KEY")
public class SysMenu extends TreeEntity<SysMenu> {

    /**
     * 菜单ID
     */
    @TableId(value = "menu_id")
    @Column(value = "菜单ID")
    private Long menuId;

    /**
     * 菜单名称
     */
    @NotBlank(message = "菜单名称不能为空")
    @Size(min = 0, max = 50, message = "菜单名称长度不能超过50个字符")
    @Column(value = "菜单名称")
    private String menuName;

    /**
     * 显示顺序
     */
    @NotNull(message = "显示顺序不能为空")
    @Column(value = "显示顺序")
    private Integer orderNum;

    /**
     * 路由地址
     */
    @Size(min = 0, max = 200, message = "路由地址不能超过200个字符")
    @Column(value = "路由地址")
    private String path;

    /**
     * 组件路径
     */
    @Size(min = 0, max = 200, message = "组件路径不能超过255个字符")
    @Column(value = "组件路径")
    private String component;

    /**
     * 路由参数
     */
    @Column(value = "路由参数")
    private String queryParam;

    /**
     * 是否为外链（0是 1否）
     */
    @Column(value = "是否为外链")
    private String isFrame;

    /**
     * 是否缓存（0缓存 1不缓存）
     */
    @Column(value = "是否缓存")
    private String isCache;

    /**
     * 类型（M目录 C菜单 F按钮）
     */
    @NotBlank(message = "菜单类型不能为空")
    @Column(value = "菜单类型")
    private String menuType;

    /**
     * 显示状态（0显示 1隐藏）
     */
    @Column(value = "显示状态")
    private String visible;

    /**
     * 菜单状态（0正常 1停用）
     */
    @Column(value = "菜单状态")
    private String status;

    /**
     * 权限字符串
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Size(min = 0, max = 100, message = "权限标识长度不能超过100个字符")
    @Column(value = "权限字符串")
    private String perms;

    /**
     * 菜单图标
     */
    @Column(value = "菜单图标")
    private String icon;

    /**
     * 备注
     */
    @Column(value = "备注")
    private String remark;

    /**
     * 业务类型
     */
    @Column(value = "警员操作")
    private String operator;

    /**
     * 核心业务
     */
    @Column(value = "核心业务")
    private String isCore;

    /**
     * 非常规业务
     */
    @Column(value = "非常规业务")
    private String isUncommon;

}
