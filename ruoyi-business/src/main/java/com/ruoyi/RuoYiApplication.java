package com.ruoyi;

import com.kmks.jianguan.domain.bo.A0221000002Bo;
import com.kmks.jianguan.domain.bo.A0221000006Bo;
import com.kmks.jianguan.domain.vo.A0221000006Vo;
import com.kmks.jianguan.service.IJgService;
import com.ruoyi.common.utils.bean.BeanHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

/**
 * 启动程序
 *
 * @author ruoyi
 */

@Slf4j
@SpringBootApplication(scanBasePackages = {"com.ruoyi", "com.kmks" })
public class RuoYiApplication {

    public static void main(String[] args) {
        System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication application = new SpringApplication(RuoYiApplication.class);
        application.setApplicationStartup(new BufferingApplicationStartup(2048));
        application.run(args);
        System.out.println("(♥◠‿◠)ﾉﾞ  国华光电中心服务启动成功   ლ(´ڡ`ლ)ﾞ");
    }


}
