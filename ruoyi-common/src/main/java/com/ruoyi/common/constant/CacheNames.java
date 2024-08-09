package com.ruoyi.common.constant;

/**
 * 缓存组名称常量
 * <p>
 * key 格式为 cacheNames#ttl#maxIdleTime#maxSize
 * <p>
 * ttl 过期时间 如果设置为0则不过期 默认为0
 * maxIdleTime 最大空闲时间 根据LRU算法清理空闲数据 如果设置为0则不检测 默认为0
 * maxSize 组最大长度 根据LRU算法清理溢出数据 如果设置为0则无限长 默认为0
 * <p>
 * 例子: test#60s、test#0#60s、test#0#1m#1000、test#1h#0#500
 *
 * @author Lion Li
 */
public interface CacheNames {

    /**
     * 演示案例
     */
    String DEMO_CACHE = "demo:cache#60s#10m#20";

    /**
     * 系统配置
     */
    String SYS_CONFIG = "sys_config";

    /**
     * 路线配置
     */
    String W2_LINE_CONFIG = "line_config";

    /**
     * 数据字典
     */
    String SYS_DICT = "sys_dict";

    /**
     * OSS内容
     */
    String SYS_OSS = "sys_oss#30d";

    /**
     * OSS配置
     */
    String SYS_OSS_CONFIG = "sys_oss_config";

    /**
     * 在线用户
     */
    String ONLINE_TOKEN = "online_tokens";

    /**
     * 科目键名
    **/
    String COURSE_KEY = "sys.config.course";

    /**
     * 考场ID
     **/
    String SCHOOL_ID = "sys.config.school.id";
    /**
     * 考场名称
     **/
    String SCHOOL_NAME = "sys.config.school.name";

    /**
     * 人脸识别方式键名
     **/
    String FACE_KEY = "sys.config.face.recognize";

    String PROJECT_IDS_KEY = "sys.config.projects.ids";
    String PROJECT_IDS_EXPECT_KEY = "sys.config.projects.expect";

    String SYSTEM_KEY = "sys.config.ksxtxh";

    String MAX_BDXH_KEY = "max_bdxh";

    String QUALIFIED_SCORE_KEY = "sys.config.qualified.score";

    /**
     * 科目键名
     **/
    String VALID_KEY = "sys.config.isOpenValidCode";
    /**
     * 分车人数
     **/
    String SPLIT_NUM = "sys.config.car.split";
    /**
     * 分车状态
     **/
    String SPLIT_STATUS = "sys.auto.split.car";



}
