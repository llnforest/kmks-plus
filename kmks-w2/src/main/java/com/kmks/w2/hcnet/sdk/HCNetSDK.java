package com.kmks.w2.hcnet.sdk;

import com.sun.jna.*;
import com.sun.jna.ptr.IntByReference;

/**
 * @author Star
 * @description: TODO
 * @date 2024/8/15 15:31
 */
public interface HCNetSDK  extends Library{
    /*** 宏定义 ***/
    //常量

    public static final int MAX_NAMELEN = 16;    //DVR本地登陆名
    public static final int MAX_RIGHT = 32;    //设备支持的权限（1-12表示本地权限，13-32表示远程权限）
    public static final int NAME_LEN = 32;    //用户名长度
    public static final int PASSWD_LEN = 16;    //密码长度
    public static final int SERIALNO_LEN = 48;   //序列号长度
    public static final int MACADDR_LEN = 6;      //mac地址长度
    public static final int MAX_ETHERNET = 2;   //设备可配以太网络
    public static final int PATHNAME_LEN = 128;   //路径长度
    public static final int MAX_TIMESEGMENT_V30 = 8;    //9000设备最大时间段数
    public static final int MAX_TIMESEGMENT = 4;   //8000设备最大时间段数
    public static final int MAX_SHELTERNUM = 4;   //8000设备最大遮挡区域数
    public static final int MAX_DAYS = 7;      //每周天数
    public static final int PHONENUMBER_LEN = 32;   //pppoe拨号号码最大长度
    public static final int MAX_DISKNUM_V30 = 33;        //9000设备最大硬盘数/* 最多33个硬盘(包括16个内置SATA硬盘、1个eSATA硬盘和16个NFS盘) */
    public static final int MAX_DISKNUM = 16;     //8000设备最大硬盘数
    public static final int MAX_DISKNUM_V10 = 8;   //1.2版本之前版本
    public static final int MAX_WINDOW_V30 = 32; //9000设备本地显示最大播放窗口数
    public static final int MAX_WINDOW = 16;    //8000设备最大硬盘数
    public static final int MAX_VGA_V30 = 4;     //9000设备最大可接VGA数
    public static final int MAX_VGA = 1;    //8000设备最大可接VGA数
    public static final int MAX_USERNUM_V30 = 32;  //9000设备最大用户数
    public static final int MAX_USERNUM = 16;  //8000设备最大用户数
    public static final int MAX_EXCEPTIONNUM_V30 = 32;  //9000设备最大异常处理数
    public static final int MAX_EXCEPTIONNUM = 16;   //8000设备最大异常处理数
    public static final int MAX_LINK = 6;    //8000设备单通道最大视频流连接数
    public static final int MAX_DECPOOLNUM = 4;   //单路解码器每个解码通道最大可循环解码数
    public static final int MAX_DECNUM = 4;    //单路解码器的最大解码通道数（实际只有一个，其他三个保留）
    public static final int MAX_TRANSPARENTNUM = 2;   //单路解码器可配置最大透明通道数
    public static final int MAX_CYCLE_CHAN = 16;   //单路解码器最大轮循通道数
    public static final int MAX_DIRNAME_LENGTH = 80;   //最大目录长度
    public static final int MAX_STRINGNUM_V30 = 8;        //9000设备最大OSD字符行数数
    public static final int MAX_STRINGNUM = 4;   //8000设备最大OSD字符行数数
    public static final int MAX_STRINGNUM_EX = 8;   //8000定制扩展
    public static final int MAX_AUXOUT_V30 = 16;   //9000设备最大辅助输出数
    public static final int MAX_AUXOUT = 4;      //8000设备最大辅助输出数
    public static final int MAX_HD_GROUP = 16;   //9000设备最大硬盘组数
    public static final int MAX_NFS_DISK = 8;    //8000设备最大NFS硬盘数
    public static final int IW_ESSID_MAX_SIZE = 32;    //WIFI的SSID号长度
    public static final int IW_ENCODING_TOKEN_MAX = 32;   //WIFI密锁最大字节数
    public static final int MAX_SERIAL_NUM = 64;    //最多支持的透明通道路数
    public static final int MAX_DDNS_NUMS = 10;   //9000设备最大可配ddns数
    public static final int MAX_DOMAIN_NAME = 64;    /* 最大域名长度 */

    public static final int MAX_EMAIL_ADDR_LEN = 48;  //最大email地址长度
    public static final int MAX_EMAIL_PWD_LEN = 32;     //最大email密码长度
    public static final int MAXPROGRESS = 100;  //回放时的最大百分率
    public static final int MAX_SERIALNUM = 2;    //8000设备支持的串口数 1-232， 2-485
    public static final int CARDNUM_LEN = 20;    //卡号长度
    public static final int MAX_VIDEOOUT_V30 = 4;      //9000设备的视频输出数
    public static final int MAX_VIDEOOUT = 2;      //8000设备的视频输出数
    public static final int MAX_PRESET_V30 = 256;    /* 9000设备支持的云台预置点数 */
    public static final int MAX_TRACK_V30 = 256;    /* 9000设备支持的云台路线数 */
    public static final int MAX_CRUISE_V30 = 256;    /* 9000设备支持的云台巡航数 */
    public static final int MAX_PRESET = 128;    /* 8000设备支持的云台预置点数 */
    public static final int MAX_TRACK = 128;    /* 8000设备支持的云台路线数 */
    public static final int MAX_CRUISE = 128;    /* 8000设备支持的云台巡航数 */
    public static final int CRUISE_MAX_PRESET_NUMS = 32;    /* 一条巡航最多的巡航点 */
    public static final int MAX_SERIAL_PORT = 8;    //9000设备支持232串口数
    public static final int MAX_PREVIEW_MODE = 8;    /* 设备支持最大预览模式数目 1画面,4画面,9画面,16画面.... */
    public static final int MAX_MATRIXOUT = 16;  /* 最大模拟矩阵输出个数 */
    public static final int LOG_INFO_LEN = 11840; /* 日志附加信息 */
    public static final int DESC_LEN = 16;    /* 云台描述字符串长度 */
    public static final int PTZ_PROTOCOL_NUM = 200;   /* 9000最大支持的云台协议数 */
    public static final int MAX_AUDIO = 1;    //8000语音对讲通道数
    public static final int MAX_AUDIO_V30 = 2;   //9000语音对讲通道数
    public static final int MAX_CHANNUM = 16;   //8000设备最大通道数
    public static final int MAX_ALARMIN = 16;  //8000设备最大报警输入数
    public static final int MAX_ALARMOUT = 4;    //8000设备最大报警输出数
    //9000 IPC接入
    public static final int MAX_ANALOG_CHANNUM = 32;    //最大32个模拟通道
    public static final int MAX_ANALOG_ALARMOUT = 32;    //最大32路模拟报警输出
    public static final int MAX_ANALOG_ALARMIN = 32;    //最大32路模拟报警输入
    public static final int MAX_IP_ALARMIN_V40 = 4096;    //允许加入的最多报警输入数
    public static final int MAX_IP_ALARMOUT_V40 = 4096;    //允许加入的最多报警输出数
    public static final int MAX_ALARMOUT_V40 = (MAX_IP_ALARMOUT_V40 + MAX_ANALOG_ALARMOUT); //4128
    public static final int MAX_ALARMIN_V40 = (MAX_IP_ALARMIN_V40 + MAX_ANALOG_ALARMOUT); //4128
    public static final int MAX_CHANNUM_V40 = 512;
    public static final int MAX_IP_DEVICE = 32;    //允许接入的最大IP设备数
    public static final int MAX_IP_CHANNEL = 32;   //允许加入的最多IP通道数
    public static final int MAX_IP_ALARMIN = 128;   //允许加入的最多报警输入数
    public static final int MAX_IP_ALARMOUT = 64; //允许加入的最多报警输出数

    /* 最大支持的通道数 最大模拟加上最大IP支持 */
    public static final int MAX_CHANNUM_V30 = (MAX_ANALOG_CHANNUM + MAX_IP_CHANNEL);//64
    public static final int MAX_ALARMOUT_V30 = (MAX_ANALOG_ALARMOUT + MAX_IP_ALARMOUT);//96
    public static final int MAX_ALARMIN_V30 = (MAX_ANALOG_ALARMIN + MAX_IP_ALARMIN);//160
    public static final int MAX_IP_DEVICE_V40 = 64;
    public static final int STREAM_ID_LEN = 32;

    public static final int MAX_LICENSE_LEN = 16;
    public static final int MAX_LICENSE_LEN_EX = 32; //车牌号最大长度
    public static final int MAX_CARDNO_LEN = 48;     //卡号最大长度
    public static final int VCA_MAX_POLYGON_POINT_NUM = 10;

    public static final int MAX_ID_NUM_LEN = 32;  //最大身份证号长度
    public static final int MAX_ID_NAME_LEN = 128;   //最大姓名长度
    public static final int MAX_ID_ADDR_LEN = 280;   //最大住址长度
    public static final int MAX_ID_ISSUING_AUTHORITY_LEN = 128; //最大签发机关长度
    public static final int MAX_CARD_READER_NUM_512 = 512;  //最大读卡器数
    public static final int ERROR_MSG_LEN = 32;   //下发错误信息
    public static final int MAX_FACE_NUM = 2;    //最大人脸数
    public static final int MAX_FINGER_PRINT_LEN = 768;  //最大指纹长度

    public static final int DEV_TYPE_NAME_LEN = 24;      //设备类型名称长度
    public static final int MAX_FACE_PIC_NUM = 30;      /*人脸子图个数*/
    public static final int CARDNUM_LEN_V30 = 40;

    public static final int MAX_NOTICE_NUMBER_LEN = 32;   //公告编号最大长度
    public static final int MAX_NOTICE_THEME_LEN = 64;   //公告主题最大长度
    public static final int MAX_NOTICE_DETAIL_LEN = 1024; //公告详情最大长度
    public static final int MAX_NOTICE_PIC_NUM = 6;    //公告信息最大图片数量
    public static final int MAX_DEV_NUMBER_LEN = 32;   //设备编号最大长度
    public static final int LOCK_NAME_LEN = 32;  //锁名称

    public static final int NET_SDK_EMPLOYEE_NO_LEN = 32;  //工号长度
    public static final int NET_SDK_UUID_LEN = 36;  //UUID长度

    public static final int MAX_INQUEST_CDRW_NUM = 4;     //最大刻录机数目
    public static final int INQUEST_MESSAGE_LEN = 44;    //审讯重点标记信息长度
    public static final int INQUEST_MAX_ROOM_NUM = 2;     //最大审讯室个数
    public static final int MAX_RESUME_SEGMENT = 2;     //支持同时恢复的片段数目

    public static final int SUPPORT_PD_NUM = 16;
    public static final int SUPPORT_ARRAY_NUM = 8;
    public static final int SUPPORT_VD_NUM = 128;
    public static final int SUPPORT_PD_NUM_ = 16;
    public static final int SUPPORT_PD_NUM_PARTTWO = 8;

    public static final int CARDNUM_LEN_OUT = 32; //外部结构体卡号长度
    public static final int GUID_LEN = 16;        //GUID长度

    public static final int MAX_ROIDETECT_NUM = 8;    //支持的ROI区域数
    public static final int MAX_LANERECT_NUM = 5;    //最大车牌识别区域数
    public static final int MAX_FORTIFY_NUM = 10;   //最大布防个数
    public static final int MAX_INTERVAL_NUM = 4;    //最大时间间隔个数
    public static final int MAX_CHJC_NUM = 3;    //最大车辆省份简称字符个数
    public static final int MAX_VL_NUM = 5;    //最大虚拟线圈个数
    public static final int MAX_DRIVECHAN_NUM = 16;  //最大车道数
    public static final int MAX_COIL_NUM = 3;    //最大线圈个数
    public static final int MAX_SIGNALLIGHT_NUM = 6;   //最大信号灯个数
    public static final int MAX_IOSPEED_GROUP_NUM = 4; //IO测速组个数
    public static final int MAX_IOOUT_NUM = 4;  //最大IO输出口个数
    public static final int MAX_IOIN_NUM = 8;  //最大IO输入口个数
    public static final int MAX_RELAY_NUM = 12; //继电器控制设备最大数 2013-11-04
    public static final int MAX_VEHICLE_TYPE_NUM = 8;  //车辆信息管控最大数2013-11-04
    public static final int MAX_IOIN_NUMEX = 10; //最大IO输入口个数(扩展)
    public static final int MAX_ITC_LANE_NUM = 6;  //最大车道个数
    public static final int MAX_LANEAREA_NUM = 2;  //单车道最大区域个数
    public static final int ITC_MAX_POLYGON_POINT_NUM = 20;    //检测区域最多支持20个点的多边形
    public static final int MAX_ITC_SERIALCHECK_NUM = 8; //串口校验类型个数
    public static final int MAX_LIGHT_NUM = 6; //最大交通灯数
    public static final int MAX_VIDEO_INTERVAL_NUM = 2;  //最大抓拍间隔数
    public static final int MAX_VIDEO_DETECT_LIGHT_NUM = 12;  //视频检测的最大检测区域
    public static final int MAX_CALIB_RECOG_NUM = 2;  //标定区域个数
    public static final int MAX_RS485_NUM = 12; //485口最大支持数
    public static final int MAX_MOBILE_POLYGON_NUM = 3; //移动布防支持最大牌识区域个数
    public static final int MAX_MOBILE_DETECTLINE_NUM = 3; //移动布防支持最大违规检测线个数
    public static final int MAX_IOOUT_K_NUM = 8;  //K系列最大IO输出口个数

    public static final int NET_SDK_MAX_FDID_LEN = 256; //人脸库ID最大长度
    public static final int NET_SDK_MAX_PICID_LEN = 256; //人脸ID最大长度
    public static final int NET_SDK_MAX_INDENTITY_KEY_LEN = 64; //交互操作口令长度

    public static final int SEARCH_EVENT_INFO_LEN = 300;    //事件信息长度
    public static final int SEARCH_EVENT_INFO_LEN_V40 = 800;
    public static final int MAX_POS_KEYWORDS_NUM = 3; //支持关键字查找条数
    public static final int MAX_POS_KEYWORD_LEN = 128; //每条关键字长度
    public static final int INQUEST_CASE_LEN = 64;    //审讯信息长度

    public static final int SEARCH_CASE_NO_LEN = 56;
    public static final int SEARCH_CASE_NAME_LEN = 100;
    public static final int SEARCH_LITIGANT_LEN = 32;
    public static final int SEARCH_CHIEF_JUDGE_LEN = 32;
    public static final int CASE_NO_RET_LEN = 52;
    public static final int CASE_NAME_RET_LEN = 64;
    public static final int LITIGANT_RET_LEN = 24;
    public static final int CHIEF_JUDGE_RET_LEN = 24;
    public static final int NET_SDK_CASETYPE_LEN = 32;
    public static final int NET_SDK_MAX_TAPE_INDEX_LEN = 32;  //磁带编号最大长度
    public static final int NET_SDK_MAX_FILE_LEN = 256;  //文件名最大长度

    public static final int NET_DVR_DEV_ADDRESS_MAX_LEN = 129;
    public static final int NET_DVR_LOGIN_USERNAME_MAX_LEN = 64;
    public static final int NET_DVR_LOGIN_PASSWD_MAX_LEN = 64;
    /******************************************************************/

    /*******************
     * 全局错误码 begin
     **********************/
    public static final int NET_DVR_NOERROR = 0;    //没有错误
    public static final int NET_DVR_PASSWORD_ERROR = 1;    //用户名密码错误
    public static final int NET_DVR_NOENOUGHPRI = 2;//权限不足
    public static final int NET_DVR_NOINIT = 3;//没有初始化
    public static final int NET_DVR_CHANNEL_ERROR = 4;    //通道号错误
    public static final int NET_DVR_OVER_MAXLINK = 5;    //连接到DVR的客户端个数超过最大
    public static final int NET_DVR_VERSIONNOMATCH = 6;    //版本不匹配
    public static final int NET_DVR_NETWORK_FAIL_CONNECT = 7;//连接服务器失败
    public static final int NET_DVR_NETWORK_SEND_ERROR = 8;    //向服务器发送失败
    public static final int NET_DVR_NETWORK_RECV_ERROR = 9;    //从服务器接收数据失败
    public static final int NET_DVR_NETWORK_RECV_TIMEOUT = 10;    //从服务器接收数据超时
    public static final int NET_DVR_NETWORK_ERRORDATA = 11;    //传送的数据有误
    public static final int NET_DVR_ORDER_ERROR = 12;    //调用次序错误
    public static final int NET_DVR_OPERNOPERMIT = 13;    //无此权限
    public static final int NET_DVR_COMMANDTIMEOUT = 14;    //DVR命令执行超时
    public static final int NET_DVR_ERRORSERIALPORT = 15;    //串口号错误
    public static final int NET_DVR_ERRORALARMPORT = 16;    //报警端口错误
    public static final int NET_DVR_PARAMETER_ERROR = 17;//参数错误
    public static final int NET_DVR_CHAN_EXCEPTION = 18;    //服务器通道处于错误状态
    public static final int NET_DVR_NODISK = 19;    //没有硬盘
    public static final int NET_DVR_ERRORDISKNUM = 20;    //硬盘号错误
    public static final int NET_DVR_DISK_FULL = 21;    //服务器硬盘满
    public static final int NET_DVR_DISK_ERROR = 22;//服务器硬盘出错
    public static final int NET_DVR_NOSUPPORT = 23;//服务器不支持
    public static final int NET_DVR_BUSY = 24;//服务器忙
    public static final int NET_DVR_MODIFY_FAIL = 25;//服务器修改不成功
    public static final int NET_DVR_PASSWORD_FORMAT_ERROR = 26;//密码输入格式不正确
    public static final int NET_DVR_DISK_FORMATING = 27;    //硬盘正在格式化，不能启动操作
    public static final int NET_DVR_DVRNORESOURCE = 28;    //DVR资源不足
    public static final int NET_DVR_DVROPRATEFAILED = 29; //DVR操作失败
    public static final int NET_DVR_OPENHOSTSOUND_FAIL = 30; //打开PC声音失败
    public static final int NET_DVR_DVRVOICEOPENED = 31; //服务器语音对讲被占用
    public static final int NET_DVR_TIMEINPUTERROR = 32; //时间输入不正确
    public static final int NET_DVR_NOSPECFILE = 33;  //回放时服务器没有指定的文件
    public static final int NET_DVR_CREATEFILE_ERROR = 34;    //创建文件出错
    public static final int NET_DVR_FILEOPENFAIL = 35; //打开文件出错
    public static final int NET_DVR_OPERNOTFINISH = 36; //上次的操作还没有完成
    public static final int NET_DVR_GETPLAYTIMEFAIL = 37; //获取当前播放的时间出错
    public static final int NET_DVR_PLAYFAIL = 38; //播放出错
    public static final int NET_DVR_FILEFORMAT_ERROR = 39;//文件格式不正确
    public static final int NET_DVR_DIR_ERROR = 40;    //路径错误
    public static final int NET_DVR_ALLOC_RESOURCE_ERROR = 41;//资源分配错误
    public static final int NET_DVR_AUDIO_MODE_ERROR = 42;    //声卡模式错误
    public static final int NET_DVR_NOENOUGH_BUF = 43;    //缓冲区太小
    public static final int NET_DVR_CREATESOCKET_ERROR = 44;    //创建SOCKET出错
    public static final int NET_DVR_SETSOCKET_ERROR = 45;    //设置SOCKET出错
    public static final int NET_DVR_MAX_NUM = 46;    //个数达到最大
    public static final int NET_DVR_USERNOTEXIST = 47;    //用户不存在
    public static final int NET_DVR_WRITEFLASHERROR = 48;//写FLASH出错
    public static final int NET_DVR_UPGRADEFAIL = 49;//DVR升级失败
    public static final int NET_DVR_CARDHAVEINIT = 50; //解码卡已经初始化过
    public static final int NET_DVR_PLAYERFAILED = 51;    //调用播放库中某个函数失败
    public static final int NET_DVR_MAX_USERNUM = 52; //设备端用户数达到最大
    public static final int NET_DVR_GETLOCALIPANDMACFAIL = 53;//获得客户端的IP地址或物理地址失败
    public static final int NET_DVR_NOENCODEING = 54;    //该通道没有编码
    public static final int NET_DVR_IPMISMATCH = 55;    //IP地址不匹配
    public static final int NET_DVR_MACMISMATCH = 56;//MAC地址不匹配
    public static final int NET_DVR_UPGRADELANGMISMATCH = 57;//升级文件语言不匹配
    public static final int NET_DVR_MAX_PLAYERPORT = 58;//播放器路数达到最大
    public static final int NET_DVR_NOSPACEBACKUP = 59;//备份设备中没有足够空间进行备份
    public static final int NET_DVR_NODEVICEBACKUP = 60;    //没有找到指定的备份设备
    public static final int NET_DVR_PICTURE_BITS_ERROR = 61;    //图像素位数不符，限24色
    public static final int NET_DVR_PICTURE_DIMENSION_ERROR = 62;//图片高*宽超限， 限128*256
    public static final int NET_DVR_PICTURE_SIZ_ERROR = 63;    //图片大小超限，限100K
    public static final int NET_DVR_LOADPLAYERSDKFAILED = 64;    //载入当前目录下Player Sdk出错
    public static final int NET_DVR_LOADPLAYERSDKPROC_ERROR = 65;    //找不到Player Sdk中某个函数入口
    public static final int NET_DVR_LOADDSSDKFAILED = 66;    //载入当前目录下DSsdk出错
    public static final int NET_DVR_LOADDSSDKPROC_ERROR = 67;    //找不到DsSdk中某个函数入口
    public static final int NET_DVR_DSSDK_ERROR = 68;    //调用硬解码库DsSdk中某个函数失败
    public static final int NET_DVR_VOICEMONOPOLIZE = 69;    //声卡被独占
    public static final int NET_DVR_JOINMULTICASTFAILED = 70;    //加入多播组失败
    public static final int NET_DVR_CREATEDIR_ERROR = 71;    //建立日志文件目录失败
    public static final int NET_DVR_BINDSOCKET_ERROR = 72;    //绑定套接字失败
    public static final int NET_DVR_SOCKETCLOSE_ERROR = 73;    //socket连接中断，此错误通常是由于连接中断或目的地不可达
    public static final int NET_DVR_USERID_ISUSING = 74;    //注销时用户ID正在进行某操作
    public static final int NET_DVR_SOCKETLISTEN_ERROR = 75;    //监听失败
    public static final int NET_DVR_PROGRAM_EXCEPTION = 76;    //程序异常
    public static final int NET_DVR_WRITEFILE_FAILED = 77;    //写文件失败
    public static final int NET_DVR_FORMAT_READONLY = 78;//禁止格式化只读硬盘
    public static final int NET_DVR_WITHSAMEUSERNAME = 79;//用户配置结构中存在相同的用户名
    public static final int NET_DVR_DEVICETYPE_ERROR = 80; /*导入参数时设备型号不匹配*/
    public static final int NET_DVR_LANGUAGE_ERROR = 81; /*导入参数时语言不匹配*/
    public static final int NET_DVR_PARAVERSION_ERROR = 82; /*导入参数时软件版本不匹配*/
    public static final int NET_DVR_IPCHAN_NOTALIVE = 83; /*预览时外接IP通道不在线*/
    public static final int NET_DVR_RTSP_SDK_ERROR = 84;    /*加载高清IPC通讯库StreamTransClient.dll失败*/
    public static final int NET_DVR_CONVERT_SDK_ERROR = 85;    /*加载转码库失败*/
    public static final int NET_DVR_IPC_COUNT_OVERFLOW = 86; /*超出最大的ip接入通道数*/
    public static final int NET_PLAYM4_NOERROR = 500;    //no error
    public static final int NET_PLAYM4_PARA_OVER = 501;//input parameter is invalid;
    public static final int NET_PLAYM4_ORDER_ERROR = 502;//The order of the function to be called is error.
    public static final int NET_PLAYM4_TIMER_ERROR = 503;//Create multimedia clock failed;
    public static final int NET_PLAYM4_DEC_VIDEO_ERROR = 504;//Decode video data failed.
    public static final int NET_PLAYM4_DEC_AUDIO_ERROR = 505;//Decode audio data failed.
    public static final int NET_PLAYM4_ALLOC_MEMORY_ERROR = 506;    //Allocate memory failed.
    public static final int NET_PLAYM4_OPEN_FILE_ERROR = 507;    //Open the file failed.
    public static final int NET_PLAYM4_CREATE_OBJ_ERROR = 508;//Create thread or event failed
    public static final int NET_PLAYM4_CREATE_DDRAW_ERROR = 509;//Create DirectDraw object failed.
    public static final int NET_PLAYM4_CREATE_OFFSCREEN_ERROR = 510;//failed when creating off-screen surface.
    public static final int NET_PLAYM4_BUF_OVER = 511;    //buffer is overflow
    public static final int NET_PLAYM4_CREATE_SOUND_ERROR = 512;    //failed when creating audio device.
    public static final int NET_PLAYM4_SET_VOLUME_ERROR = 513;//Set volume failed
    public static final int NET_PLAYM4_SUPPORT_FILE_ONLY = 514;//The function only support play file.
    public static final int NET_PLAYM4_SUPPORT_STREAM_ONLY = 515;//The function only support play stream.
    public static final int NET_PLAYM4_SYS_NOT_SUPPORT = 516;//System not support.
    public static final int NET_PLAYM4_FILEHEADER_UNKNOWN = 517;    //No file header.
    public static final int NET_PLAYM4_VERSION_INCORRECT = 518;    //The version of decoder and encoder is not adapted.
    public static final int NET_PALYM4_INIT_DECODER_ERROR = 519;    //Initialize decoder failed.
    public static final int NET_PLAYM4_CHECK_FILE_ERROR = 520;    //The file data is unknown.
    public static final int NET_PLAYM4_INIT_TIMER_ERROR = 521;    //Initialize multimedia clock failed.
    public static final int NET_PLAYM4_BLT_ERROR = 522;//Blt failed.
    public static final int NET_PLAYM4_UPDATE_ERROR = 523;//Update failed.
    public static final int NET_PLAYM4_OPEN_FILE_ERROR_MULTI = 524; //openfile error, streamtype is multi
    public static final int NET_PLAYM4_OPEN_FILE_ERROR_VIDEO = 525; //openfile error, streamtype is video
    public static final int NET_PLAYM4_JPEG_COMPRESS_ERROR = 526; //JPEG compress error
    public static final int NET_PLAYM4_EXTRACT_NOT_SUPPORT = 527;    //Don't support the version of this file.
    public static final int NET_PLAYM4_EXTRACT_DATA_ERROR = 528;    //extract video data failed.
    /*******************全局错误码 end**********************/
    /*************************************************
     * NET_DVR_IsSupport()返回值
     * 1－9位分别表示以下信息（位与是TRUE)表示支持；
     **************************************************/
    public static final int NET_DVR_SUPPORT_DDRAW = 0x01;//支持DIRECTDRAW，如果不支持，则播放器不能工作；
    public static final int NET_DVR_SUPPORT_BLT = 0x02;//显卡支持BLT操作，如果不支持，则播放器不能工作；
    public static final int NET_DVR_SUPPORT_BLTFOURCC = 0x04;//显卡BLT支持颜色转换，如果不支持，播放器会用软件方法作RGB转换；
    public static final int NET_DVR_SUPPORT_BLTSHRINKX = 0x08;//显卡BLT支持X轴缩小；如果不支持，系统会用软件方法转换；
    public static final int NET_DVR_SUPPORT_BLTSHRINKY = 0x10;//显卡BLT支持Y轴缩小；如果不支持，系统会用软件方法转换；
    public static final int NET_DVR_SUPPORT_BLTSTRETCHX = 0x20;//显卡BLT支持X轴放大；如果不支持，系统会用软件方法转换；
    public static final int NET_DVR_SUPPORT_BLTSTRETCHY = 0x40;//显卡BLT支持Y轴放大；如果不支持，系统会用软件方法转换；
    public static final int NET_DVR_SUPPORT_SSE = 0x80;//CPU支持SSE指令，Intel Pentium3以上支持SSE指令；
    public static final int NET_DVR_SUPPORT_MMX = 0x100;//CPU支持MMX指令集，Intel Pentium3以上支持SSE指令；

    /*************************************************
     * 接口
     **************************************************/
    boolean NET_DVR_Init();// 初始化

    int NET_DVR_Login_V40(NET_DVR_USER_LOGIN_INFO pLoginInfo, NET_DVR_DEVICEINFO_V40 lpDeviceInfo);// 登录

    boolean NET_DVR_Logout(int lUserID);// 退出

    boolean NET_DVR_Cleanup();// 释放sdk资源

    int NET_DVR_GetLastError(); //返回最后操作的错误码

    boolean NET_DVR_GetDVRConfig(int lUserID, int dwCommand, int lChannel, Pointer lpOutBuffer, int dwOutBufferSize, IntByReference lpBytesReturned); // 获取设备的配置信息

    boolean NET_DVR_SetDVRConfig(int lUserID, int dwCommand, int lChannel, Pointer lpInBuffer, int dwInBufferSize); //设置设备的配置信息

    boolean NET_DVR_MatrixStartDynamic_V41(int lUserID, int dwDecChanNum, Pointer lpDynamicInfo); //启动动态解码
    boolean NET_DVR_MatrixStopDynamic(int lUserID, int dwDecChanNum); //停止动态解码

    long NET_DVR_PicUpload(int lUserID, String sFileName, Pointer lp); //图片上传

    long NET_DVR_GetPicUploadState(long lUploadHandle); //获取图片上传的状态

    long NET_DVR_GetPicUploadProgress(long lUploadHandle);//获取图片上传的进度

    boolean NET_DVR_CloseUploadHandle(long lUploadHandle); //关闭图片上传

    boolean NET_DVR_GetIPCProtoList(int lUserID, Pointer lpDynamicInfo); //获取设备支持的 IPC 协议表

    boolean NET_DVR_RemoteControl(int lUserID, int dwCommand, Pointer lpInBuffer, int dwInBufferSize);//删除图片

    /***************************************************************
     dwCommand 宏定义值
     ***************************************************************/
    public static final int NET_DVR_SET_OUTPUT_PIC_INFO = 9201; //设置输出口图片参数
    public static final int NET_DVR_SET_OUTPUT_PIC_WIN_CFG = 9203; //设置输出口图片窗口参数
    public static final int NET_DVR_SET_OUTPUT_OSD_CFG = 9207;//设置输出口 OSD 参数

    public static final int NET_DVR_GET_OUTPUT_OSD_CFG = 9206;//获取输出口 OSD 参数
    public static final int NET_DVR_DELETE_OUPUT_PIC = 9205;//获取输出口 OSD 参数


    /***************************************************************
     lpOutBuffer 对应结构体
    ***************************************************************/
    //NET_DVR_Login_V40()参数
    public static class NET_DVR_USER_LOGIN_INFO extends Structure {
        public byte[] sDeviceAddress = new byte[NET_DVR_DEV_ADDRESS_MAX_LEN];
        public byte byUseTransport;
        public short wPort;
        public byte[] sUserName = new byte[NET_DVR_LOGIN_USERNAME_MAX_LEN];
        public byte[] sPassword = new byte[NET_DVR_LOGIN_PASSWD_MAX_LEN];
        public FLoginResultCallBack cbLoginResult;
        public Pointer pUser;
        public boolean bUseAsynLogin;
        public byte byProxyType; //0:不使用代理，1：使用标准代理，2：使用EHome代理
        public byte byUseUTCTime;    //0-不进行转换，默认,1-接口上输入输出全部使用UTC时间,SDK完成UTC时间与设备时区的转换,2-接口上输入输出全部使用平台本地时间，SDK完成平台本地时间与设备时区的转换
        public byte byLoginMode; //0-Private 1-ISAPI 2-自适应
        public byte byHttps;    //0-不适用tls，1-使用tls 2-自适应
        public int iProxyID;    //代理服务器序号，添加代理服务器信息时，相对应的服务器数组下表值
        public byte byVerifyMode;  //认证方式，0-不认证，1-双向认证，2-单向认证；认证仅在使用TLS的时候生效;
        public byte[] byRes2 = new byte[119];
    }

    public static interface FLoginResultCallBack extends Callback {
        public int invoke(int lUserID, int dwResult, NET_DVR_DEVICEINFO_V30 lpDeviceinfo, Pointer pUser);
    }

    //NET_DVR_Login_V40()参数
    public static class NET_DVR_DEVICEINFO_V40 extends Structure {
        public NET_DVR_DEVICEINFO_V30 struDeviceV30 = new NET_DVR_DEVICEINFO_V30();
        public byte bySupportLock;
        public byte byRetryLoginTime;
        public byte byPasswordLevel;
        public byte byRes1;
        public int dwSurplusLockTime;
        public byte byCharEncodeType;//字符编码类型：0- 无字符编码信息(老设备)，1- GB2312(简体中文)，2- GBK，3- BIG5(繁体中文)，4- Shift_JIS(日文)，5- EUC-KR(韩文)，6- UTF-8，7- ISO8859-1，8- ISO8859-2，9- ISO8859-3，…，依次类推，21- ISO8859-15(西欧)
        public byte bySupportDev5; //支持v50版本的设备参数获取，设备名称和设备类型名称长度扩展为64字节
        public byte bySupport;  //能力集扩展，位与结果：0- 不支持，1- 支持
        public byte byLoginMode; //登录模式 0-Private登录 1-ISAPI登录
        public int dwOEMCode;
        public int iResidualValidity;   //该用户密码剩余有效天数，单位：天，返回负值，表示密码已经超期使用，例如“-3表示密码已经超期使用3天”
        public byte byResidualValidity; // iResidualValidity字段是否有效，0-无效，1-有效
        public byte bySingleStartDTalkChan;    //独立音轨接入的设备，起始接入通道号，0-为保留字节，无实际含义，音轨通道号不能从0开始
        public byte bySingleDTalkChanNums;    //独立音轨接入的设备的通道总数，0-表示不支持
        public byte byPassWordResetLevel; //0-无效，1-管理员创建一个非管理员用户为其设置密码，该非管理员用户正确登录设备后要提示“请修改初始登录密码”，未修改的情况下，用户每次登入都会进行提醒；2-当非管理员用户的密码被管理员修改，该非管理员用户再次正确登录设备后，需要提示“请重新设置登录密码”，未修改的情况下，用户每次登入都会进行提醒。
        public byte bySupportStreamEncrypt;  //能力集扩展，位与结果：0- 不支持，1- 支持 bySupportStreamEncrypt & 0x1:表示是否支持RTP/TLS取流 bySupportStreamEncrypt & 0x2:  表示是否支持SRTP/UDP取流 bySupportStreamEncrypt & 0x4:  表示是否支持SRTP/MULTICAST取流
        public byte byMarketType;//0-无效（未知类型）,1-经销型，2-行业型
        public byte[] byRes2 = new byte[238];
    }

    //NET_DVR_Login_V30()参数结构
    public static class NET_DVR_DEVICEINFO_V30 extends Structure {
        public byte[] sSerialNumber = new byte[SERIALNO_LEN];  //序列号
        public byte byAlarmInPortNum;    //报警输入个数
        public byte byAlarmOutPortNum;   //报警输出个数
        public byte byDiskNum;           //硬盘个数
        public byte byDVRType;         //设备类型, 1:DVR 2:ATM DVR 3:DVS ......
        public byte byChanNum;         //模拟通道个数
        public byte byStartChan;      //起始通道号,例如DVS-1,DVR - 1
        public byte byAudioChanNum;    //语音通道数
        public byte byIPChanNum;     //最大数字通道个数，低位
        public byte byZeroChanNum;    //零通道编码个数 //2010-01-16
        public byte byMainProto;      //主码流传输协议类型 0-private, 1-rtsp,2-同时支持private和rtsp
        public byte bySubProto;        //子码流传输协议类型0-private, 1-rtsp,2-同时支持private和rtsp
        public byte bySupport;        //能力，位与结果为0表示不支持，1表示支持，
        public byte bySupport1;        // 能力集扩充，位与结果为0表示不支持，1表示支持
        public byte bySupport2; /*能力*/
        public short wDevType;              //设备型号
        public byte bySupport3; //能力集扩展
        public byte byMultiStreamProto;//是否支持多码流,按位表示,0-不支持,1-支持,bit1-码流3,bit2-码流4,bit7-主码流，bit-8子码流
        public byte byStartDChan;        //起始数字通道号,0表示无效
        public byte byStartDTalkChan;    //起始数字对讲通道号，区别于模拟对讲通道号，0表示无效
        public byte byHighDChanNum;        //数字通道个数，高位
        public byte bySupport4;        //能力集扩展
        public byte byLanguageType;// 支持语种能力,按位表示,每一位0-不支持,1-支持
        //  byLanguageType 等于0 表示 老设备
        //  byLanguageType & 0x1表示支持中文
        //  byLanguageType & 0x2表示支持英文
        public byte byVoiceInChanNum;   //音频输入通道数
        public byte byStartVoiceInChanNo; //音频输入起始通道号 0表示无效
        public byte bySupport5;
        public byte bySupport6;   //能力
        public byte byMirrorChanNum;    //镜像通道个数，<录播主机中用于表示导播通道>
        public short wStartMirrorChanNo;  //起始镜像通道号
        public byte bySupport7;   //能力
        public byte byRes2;        //保留
    }

    //输出口 OSD 窗口参数
    public static class NET_DVR_OUTPUT_OSD_CFG extends Structure{
        public int dzSize = 0; //结构体大小
        public byte byEnabled = 1;  // 使能：0- 禁用，1- 启用
        public byte byFontSize = 1;  //字体大小，1-大，2-中，3-小
        public byte byOSDColor = 0;  // OSD 颜色配置：0- 默认，1- 黑，2- 白，3- 红，4- 绿，5- 蓝
        public byte byRes1 = 0; //保留，置为 0
        public byte[] byContent = new byte[64];  //OSD信息
        public NET_DVR_RECTCFG_EX struRect = new NET_DVR_RECTCFG_EX();
        public int dwOsdWinNo = 0;  //输出口 OSD 窗口号(组合)：1 字节设备号+1 字节输出口号+2 字节 OSD 窗口序号，获取所有时有效
        public byte[] byRes2 = new byte[32];

    }

    // IPC 协议列表
    public static class NET_DVR_IPC_PROTO_LIST extends Structure{
        public int dwSize; //结构体大小
        public int dwProtoNum;  // 有效的IPC协议个数
        public NET_DVR_PROTO_TYPE[] struProto = new NET_DVR_PROTO_TYPE[50];
        public byte[] byRes = new byte[8];

    }

    //协议参数
    public static class NET_DVR_PROTO_TYPE extends Structure{
        public int dwType; //协议值
        public byte[] byDescribe = new byte[16];//协议描述

    }

    // 输出口图片窗口参数
    public static class NET_DVR_OUTPUT_PIC_CFG extends Structure{
        public int dwSize = 0; //结构体大小
        public int dwOutputPicNo = 1; //图片序号，从 1 开始
        public byte byEnabled = 1;  // 是否显示：1- 显示，0- 隐藏
        public byte[] byRes1 = new byte[3];//保留，置为 0
        public NET_DVR_RECTCFG_EX struRect = new NET_DVR_RECTCFG_EX();//图片窗口位置，输出口范围总大小为 1920*1920
        public byte byFlash = 0;  //是否闪烁：1- 闪烁，0- 不闪烁
        public byte byTranslucent = 0;  // 是否半透明：1- 半透明，0- 不半透明
        public byte[] byRes2 = new byte[2]; //保留，置为 0
        public int dwOutputPicWinNo = 0;  //输出口图片窗口号(组合)：1 字节设备号+1 字节输出口号+2 字节输出口图片窗口序号，获取全部时
        public byte[] byRes3 = new byte[28];//保留，置为 0

    }

    //矩形窗口参数
    public static class NET_DVR_RECTCFG_EX extends Structure {
        public int dwXCoordinate; /*矩形左上角起始点X坐标*/
        public int dwYCoordinate; /*矩形左上角Y坐标*/
        public int dwWidth;       /*矩形宽度*/
        public int dwHeight;      /*矩形高度*/
        public byte[] byRes = new byte[4];

    }

    //输出口图片信息
    public static class NET_DVR_OUTPUT_PIC_INFO extends Structure
    {
        public int dwSize;
        public byte[] sPicName = new byte[32];//图片名称
        public byte byUsed; //是否已存在，0-不存在，1-存在
        public byte[] byRes = new byte[31];
    }

    //图片参数结构体
    public static class NET_DVR_PICCFG extends Structure {
        public int dwSize; //结构体大小
        public byte byUseType;    //1- 底图，2- GIF图片，3- CAD图片，4- 输出口图片
        public byte bySequence;        //序号。分布式不使用该参数，设为0
        public byte byWallNo;    // 电视墙号
        public byte byRes1;                //保留
        public NET_DVR_BASEMAP_CFG struBasemapCfg; //底图参数
        public byte[] sPicName = new byte[32]; // 图片名称
        public byte[] byRes2 = new byte[32]; // 保留
    }

    //原图参数结构体
    public static class NET_DVR_BASEMAP_CFG extends Structure {
        public byte byScreenIndex; //屏幕的序号
        public byte byMapNum;    //被分割成了多少块
        public byte[] res = new byte[2];                //保留
        public short wSourWidth;        //原图片的宽度
        public short wSourHeight;    // 原图片的高度
    }

    // 删除输出口图片结构体
    public static class NET_DVR_DELETE_OUPUT_PIC extends Structure { //图片参数结构体
        public int bySequence; //序号
    }

    // 动态解码参数
    public class NET_DVR_PU_STREAM_CFG_V41 extends Structure {
        public int dwSize;
        public byte byStreamMode;/*取流模式，0-无效，1-通过IP或域名取流，2-通过URL取流,3-通过动态域名解析向设备取流*/
        public byte[] byRes1 = new byte[3];
        public NET_DVR_DEC_STREAM_MODE uDecStreamMode = new NET_DVR_DEC_STREAM_MODE();//取流信息
        public byte[] byRes2 = new byte[64];
    }

    // 取流模式配置
    public static class NET_DVR_DEC_STREAM_MODE extends Union {
        public NET_DVR_DEC_STREAM_DEV_EX struDecStreamDev = new NET_DVR_DEC_STREAM_DEV_EX();
        public NET_DVR_PU_STREAM_URL struUrlInfo = new NET_DVR_PU_STREAM_URL();
        public NET_DVR_DEC_DDNS_DEV struDdnsDecInfo = new NET_DVR_DEC_DDNS_DEV();
        public byte[] byRes = new byte[300];
    }

    //通过 IP 地址或者域名从设备或者流媒体服务器取流
    public static class NET_DVR_DEC_STREAM_DEV_EX extends Structure {
        public NET_DVR_STREAM_MEDIA_SERVER struStreamMediaSvrCfg = new NET_DVR_STREAM_MEDIA_SERVER();
        public NET_DVR_DEV_CHAN_INFO_EX struDevChanInfo = new NET_DVR_DEV_CHAN_INFO_EX();
    }

    //DDNS方式取流
    public static class NET_DVR_DEC_DDNS_DEV extends Structure {
        public NET_DVR_DEV_DDNS_INFO struDdnsInfo;
        public NET_DVR_STREAM_MEDIA_SERVER struMediaServer;
    }

    //通过 URL 从设备或者流媒体服务器取流
    public class NET_DVR_PU_STREAM_URL extends Structure {
        public byte byEnable = 0;//是否启用：0- 禁用，1- 启用
        public byte[] strURL = new byte[240];//取流URL路径
        public byte byTransPortocol;//传输协议类型：0-TCP，1-UDP
        public short wIPID;//设备ID号，wIPID = iDevInfoIndex + iGroupNO*64 +1
        public byte byChannel;//设备通道号
        public byte[] byRes = new byte[7];//保留，置为0
    }

    // 流媒体服务器参数
    public static class NET_DVR_STREAM_MEDIA_SERVER extends Structure {
        public byte byValid; //是否启用，0-否，1-是
        public byte[] byRes1 = new byte[3];
        public byte[] byAddress = new byte[MAX_DOMAIN_NAME];   //IP或者域名
        public short wDevPort;            /*流媒体服务器端口*/
        public byte byTransmitType;        /*传输协议类型 0-TCP，1-UDP*/
        public byte[] byRes2 = new byte[5];
    }

    // 动态域名参数配置
    public static class NET_DVR_DEV_DDNS_INFO extends Structure {
        public byte[] byDevAddress = new byte[MAX_DOMAIN_NAME];    //域名(IPServer或hiDDNS时可填序列号或者别名)
        public byte byTransProtocol;        //传输协议类型0-TCP，1-UDP, 2-MCAST
        public byte byTransMode;            //传输码流模式 0－主码流 1－子码流
        public byte byDdnsType;         //域名服务器类型，0-IPServer 1－Dyndns 2－PeanutHull(花生壳)，3- NO-IP, 4- hiDDNS
        public byte byRes1;
        public byte[] byDdnsAddress = new byte[MAX_DOMAIN_NAME];  //DDNS服务器地址
        public short wDdnsPort;                 //DDNS服务器端口号
        public byte byChanType;              //0-普通通道,1-零通道,2-流ID
        public byte byFactoryType;            //前端设备厂家类型,通过接口获取
        public int dwChannel; //通道号
        public byte[] byStreamId = new byte[STREAM_ID_LEN]; //流ID
        public byte[] sUserName = new byte[NAME_LEN];    //布防主机登陆帐号
        public byte[] sPassword = new byte[PASSWD_LEN];    //布防主机密码
        public short wDevPort;                //前端设备通信端口
        public byte[] byRes2 = new byte[2];
    }

    //前端编码设备信息
    public static class NET_DVR_DEV_CHAN_INFO_EX extends Structure {
        public byte byChanType;              //通道类型，0-普通通道,1-零通道,2-流ID，3-本地输入源，4-虚拟屏服务器通道，5-拼接通道，6-屏幕服务器，7-分布式网络源，8-多相机融合通道，9-网络输入源
        public byte[] byStreamId = new byte[STREAM_ID_LEN]; //流ID，当byChanType=2、9时，该字段用于指定流或者网络ipc的ID号
        public byte[] byRes1 = new byte[3];
        public int dwChannel;  //通道号，通道类型为普通通道，零通道，本地输入源，虚拟屏服务器通道，拼接通道，屏幕服务器，分布式网络源时填此字段
        public byte[] byRes2 = new byte[24];
        public byte[] byAddress = new byte[MAX_DOMAIN_NAME];    //设备域名
        public short wDVRPort;                 //端口号
        public byte byChannel;                //通道号,dwChannel不为0时此字段无效
        public byte byTransProtocol;        //传输协议类型0-TCP，1-UDP
        public byte byTransMode;            //传输码流模式 0－主码流 1－子码流
        public byte byFactoryType;            /*前端设备厂家类型,通过接口获取*/
        public byte byDeviceType; //设备类型(视频综合平台智能板使用)，1-解码器（此时根据视频综合平台能力集中byVcaSupportChanMode字段来决定是使用解码通道还是显示通道），2-编码器
        public byte byDispChan;//显示通道号,智能配置使用
        public byte bySubDispChan;//显示通道子通道号，智能配置时使用
        public byte byResolution;    //; 1-CIF 2-4CIF 3-720P 4-1080P 5-500w大屏控制器使用，大屏控制器会根据该参数分配解码资源
        public byte[] byRes = new byte[2];
        public byte[] sUserName = new byte[NAME_LEN];    //布防主机登陆帐号
        public byte[] sPassword = new byte[PASSWD_LEN];    //布防主机密码
    }
}
