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

    String SYS_JK_JG_API = "sys.jk.jg.api";

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
     * 人脸识别方式
     **/
    String FACE_KEY = "sys.face.recognize.type";

    /**
     * 人脸识别相似度
     **/
    String FACE_SCORE = "sys.face.score";

    /**
     * 人脸识别图片存储地址
     **/
    String FACE_STORAGE_PATH = "sys.face.storagePath";

    /**
     * 人脸识别后是否保留图片
     **/
    String FACE_SAVE_IMG = "sys.face.img.save";

    /**
     * 【虹软】人脸识别引擎地址
     **/
    String FACE_ARCFACE_ENGINE_PATH = "sys.arcFace.enginePath";

    /**
     * 【虹软】人脸识别appId
     **/
    String FACE_ARCFACE_APPID = "sys.arcFace.appId";

    /**
     * 【虹软】人脸识别sdkKey
     **/
    String FACE_ARCFACE_SDK_KEY = "sys.arcFace.sdkKey";

    /**
     * 【虹软】人脸识别sdkKey
     **/
    String FILE_UPLOAD_PATH = "sys.file.upload.path";

    String PROJECT_IDS_EXPECT_KEY = "sys.config.projects.expect";

    String SYSTEM_KEY = "jg.ksxtxh";

    String MAX_BDXH_KEY = "max_bdxh";

    /**
     * 及格分数
    **/
    String QUALIFIED_K2_SCORE_KEY = "sys.k2.qualified.score";
    String QUALIFIED_K3_SCORE_KEY = "sys.k3.qualified.score";

    /**
     * 成绩单打印模板
     **/
    String PRINT_SCORE_TEMPLATE = "sys.jk.template.print.name";
    /**
     * 成绩单抬头
     **/
    String PRINT_SCORE_TITLE = "sys.jk.template.print.title";

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

    /**
     * 合码器SDK位置
     **/
    String HC_OPEN = "sys.hc.open";

    /**
     * 合码器SDK位置
    **/
    String HC_SDK_PATH = "sys.hc.sdk.path";
    /**
     * 合码器轨迹照片存储位置
    **/
    String HC_trajectory_path = "hc.trajectory.path";
//---------------------安全参数------------------------
    /**
     * 系统最大会话并发数
    **/
    String SAFE_VISITED_MAX = "safe.visited.max";
    /**
     * 系统最大会话并发数
    **/
    String SAFE_VISITED_USER_HIGH = "safe.visited.user.high";
    /**
     * 系统最大会话并发数
    **/
    String SAFE_VISITED_CLIENT_STARTTIME = "safe.visited.client.starttime";
    /**
     * 系统最大会话并发数
    **/
    String SAFE_VISITED_CLIENT_ENDTIME = "safe.visited.client.endtime";
    /**
     * 账户长期未使用
     **/
    String SAFE_DAY_NO_LOGIN = "safe.day.no.login";
    /**
     * 用户访问失败阀值
     **/
    String SAFE_VISITED_USER_NUMBER = "safe.visited.user.number";
    /**
     * 终端访问失败阀值
     **/
    String SAFE_VISITED_CLIENT_NUMBER = "safe.visited.client.number";
    /**
     * 终端访问失败阀值
     **/
    String SAFE_FINISH_NO_OPERATE = "safe.finish.no.operate";





}
