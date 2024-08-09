package com.ruoyi.common.constant;

/**
 * 缓存的key 常量
 *
 * @author ruoyi
 */
public interface CacheConstants {

    /**
     * 登录用户 redis key
     */
    String LOGIN_TOKEN_KEY = "Authorization:login:token:";

    /**
     * 在线用户 redis key
     */
    String ONLINE_TOKEN_KEY = "online_tokens:";

    /**
     * 唯一在线用户 redis key
     */
    String ONLINE_SINGLE_KEY = "online_single:";

    /**
     * 验证码 redis key
     */
    String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 验证码 redis key
     */
    String LAST_LOGIN_MAP_KEY = "last_login_map:";

    /**
     * 参数管理 cache key
     */
    String SYS_CONFIG_KEY = "sys_config:";

    /**
     * 字典管理 cache key
     */
    String SYS_DICT_KEY = "sys_dict:";

    /**
     * 防重提交 redis key
     */
    String REPEAT_SUBMIT_KEY = "repeat_submit:";

    /**
     * 限流 redis key
     */
    String RATE_LIMIT_KEY = "rate_limit:";

    /**
     * 登录账户密码错误次数 redis key
     */
    String PWD_ERR_CNT_KEY = "pwd_err_cnt:";
    String USER_BLACK_KEY = "user_black:";
    /**
     * 每日访问限制频次
     */
    String DAY_LOGIN_LIMIT_KEY = "day_login_limit:";
    String LAST_LOGIN_KEY = "last_login:";
    /**
     * 安全规范配置
    **/
    String LOGIN_SAFE_CONFIG_KEY = "login_safe_config:";

    /**
     * 科目键名
     **/
    String ACCOUNT_OPERATE_TIME = "account_operation_time:";

    /**
     * 路线配置
     **/
    String W2_LINE_CONFIG_KEY = "w2_line_config:";

    /**
     * 场地项目配置
     **/
    String W2_CDXM_CONFIG_KEY = "w2_cdxm_config:";

    /**
     * 场地项目配置
     **/
    String W2_KF_CONFIG_KEY = "w2_kf_config:";

    /**
     * 菜单核心操作
     **/
    String W2_MENU_CORE_KEY = "sys_menu_core:";

    /**
     * 菜单非常规操作
     **/
    String W2_MENU_UNCOMMON_KEY = "sys_menu_uncommon:";

    /**
     * 菜单警员操作
     **/
    String W2_MENU_OPERATOR_KEY = "sys_menu_operator:";
}
