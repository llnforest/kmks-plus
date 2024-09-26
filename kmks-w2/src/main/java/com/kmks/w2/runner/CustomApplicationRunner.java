package com.kmks.w2.runner;

import com.kmks.jianguan.domain.bo.A0221000006Bo;
import com.kmks.jianguan.domain.vo.A0221000006Vo;
import com.kmks.jianguan.service.IJgService;
import com.kmks.w2.service.*;
import com.kmks.w2.service.impl.supervise.SuperviseHandler;
import com.kmks.w2.utils.RedisUtil;
import com.ruoyi.common.constant.CacheNames;
import com.ruoyi.common.utils.bean.BeanHelper;
import com.ruoyi.common.utils.redis.RedisUtils;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.ISysMenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.time.format.DateTimeFormatter;

/**
 * @author Star
 * @description: TODO
 * @date 2023/10/13 14:11
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class CustomApplicationRunner implements ApplicationRunner {
    private final IJgService jgService;
    private final ISysConfigService configService;

    private final IW2QueuingService queuingService;

    private final IW2LineconfigService lineconfigService;

    private final IW2KfconfigService kfconfigService;

    private final IW2CdxmbhService cdxmbhService;

    private final ISysMenuService menuService;

    private final IW2FlowlogService flowlogService;

    private final IW2GpscontentService gpscontentService;

    private final SuperviseHandler superviseHandler;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("-----------自定义初始化参数-------------");
        log.info("-----------1.今日之前的数据备份，更新报到序号---------------------");
        flowlogService.syncToHistory();
        queuingService.syncToHistory();
        RedisUtil.setMaxBdxh(queuingService.getMaxBdxh());
        log.info("报到序号:{}", RedisUtil.getMaxBdxh());

        log.info("-----------2.更新路线代码缓存Map---------");
        lineconfigService.resetLineConfigMapCache();

        log.info("-----------3.更新场地项目缓存Map---------");
        cdxmbhService.resetCdxmConfigMapCache();

        log.info("-----------4.更新扣分代码缓存Map---------");
        kfconfigService.resetKfConfigMapCache();

        log.info("-----------4.设置核心和非常规功能及警员操作---------");
        menuService.initMenuOperator();

        log.info("-----------5.同步监管时间-------------------");
        superviseHandler.service().syncTime();

        log.info("-----------6.检查轨迹备份表-------------------");
        gpscontentService.checkAndCreateTable();

    }
}
