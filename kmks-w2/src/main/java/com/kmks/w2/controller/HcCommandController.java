package com.kmks.w2.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.net.NetUtil;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.kmks.w2.domain.W2Kcxx;
import com.kmks.w2.domain.bo.*;
import com.kmks.w2.domain.carDto.*;
import com.kmks.w2.domain.vo.*;
import com.kmks.w2.hcnet.HCNetTool;
import com.kmks.w2.hcnet.service.HCNetService;
import com.kmks.w2.service.*;
import com.ruoyi.common.constant.CacheNames;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.exception.api.FailException;
import com.ruoyi.common.utils.bean.BeanHelper;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.domain.SysOperLogParam;
import com.ruoyi.system.service.ISysComputerService;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.ISysOperLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 海康调试命令接口
 *
 * @author lynn
 * @date 2023-05-04
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/hc")
@Slf4j
@SaIgnore
public class HcCommandController extends BaseController {
    private final IW2CarsignalService carsignalService;
    private final HCNetService hcNetService;
    private final IW2KcxxService kcxxService;
    private final IW2ConfigDeviceService configDeviceService;

    @RequestMapping("/test1")
    @DSTransactional
    public R<Void> test(){
        W2KcxxBo w2KcxxBo = new W2KcxxBo();
        w2KcxxBo.setId(5l);
        w2KcxxBo.setKscx("C1");
        kcxxService.updateByBo(w2KcxxBo);
        kcxxService.queryById(1l);
        W2ConfigDeviceVo w2ConfigDeviceVo = configDeviceService.queryById(3l);
        W2ConfigDeviceBo w2ConfigDeviceBo = BeanUtil.toBean(w2ConfigDeviceVo, W2ConfigDeviceBo.class);
        w2ConfigDeviceBo.setDeviceip("192.168.1.1");
        configDeviceService.updateByBo(w2ConfigDeviceBo);
        throw new FailException("操作失败");

//        return R.ok();
    }

    @RequestMapping("/test2")
    public R<Void> test2(){
        kcxxService.queryById(1l);
        configDeviceService.queryById(1l);
        return R.ok();
    }

    @RequestMapping("/text")
    public R<Void> text(){
        if("192.50.62.120".contains("192.50.62.120")){
            log.info("true");
        }else{
            log.info("false");
        }
//        HCNetTool.load();
//        HCNetTool.init();
//        HCNetTool.login("192.168.1.200",(short) 8000,"admin","ghgd12345");
//
//        HCNetTool.setOsdText();
//        HCNetTool.setVideo();
//
//        HCNetTool.logout();
        return R.ok();
    }
//
//    @RequestMapping("/showImg")
//    public R<Void> showImg(){
//        HCNetTool.load();
//        HCNetTool.init();
//        HCNetTool.login("192.168.1.200",(short) 8000,"admin","ghgd12345");
//
//        HCNetTool.showImg();
//
//        HCNetTool.logout();
//        return R.ok();
//    }
//    @RequestMapping("/uploadImg")
//    public R<Void> uploadImg(){
//        HCNetTool.load();
//        HCNetTool.init();
//        HCNetTool.login("192.168.1.200",(short) 8000,"admin","ghgd12345");
//
//        HCNetTool.uploadImg();
//        try {
//            HCNetTool.getUploadStatus(0l);
//            Thread.sleep(1000);
//        }catch (Exception e){
//            log.info("异常：{}",e.getMessage());
//        }
////        HCNetTool.setImgInfo();
//        HCNetTool.showImg();
//
//
//        HCNetTool.logout();
//        return R.ok();
//    }
//
//    @RequestMapping("/getUploadStatus")
//    public R<Void> getUploadStatus(){
//        HCNetTool.load();
//        HCNetTool.init();
//        HCNetTool.login("192.168.1.200",(short) 8000,"admin","ghgd12345");
//
//        try {
//            HCNetTool.getUploadStatus(0l);
//
//        }catch (Exception e){
//
//        }
//
//        HCNetTool.logout();
//        return R.ok();
//    }
//
//    @RequestMapping("/deleteImg")
//    public R<Void> deleteImg(){
//        HCNetTool.load();
//        HCNetTool.init();
//        HCNetTool.login("192.168.1.200",(short) 8000,"admin","ghgd12345");
//
//        HCNetTool.removeImg();
//
//        HCNetTool.logout();
//        return R.ok();
//    }


}
