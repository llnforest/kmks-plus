package com.kmks.w2.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import com.kmks.w2.domain.gateDto.CheckInBo;
import com.kmks.w2.domain.gateDto.FaceRecognizeBo;
import com.kmks.w2.domain.gateDto.SignInBo;
import com.kmks.w2.service.IGateService;
import com.kmks.w2.service.impl.FaceServiceImpl;
import com.kmks.w2.utils.FileUtil;
import com.ruoyi.common.constant.CacheNames;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 闸机、报道接口
 *
 * @author lynn
 * @date 2023-05-04
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/gate")
@Slf4j
@SaIgnore
public class GateController extends BaseController {
    private final ISysConfigService configService;

    private final FaceServiceImpl faceService;

    private final IGateService gateService;

    /**
     * 人脸识别
     *
     * @param zjzp 证件照片
     * @param jbzp 鉴别照片
     * @return {@link R}<{@link Void}>
     */
    @GetMapping(value = "/faceRecognitionUpload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R<Void> faceRecognitionUpload(@RequestPart("zjzp") MultipartFile zjzp, @RequestPart("jbzp") MultipartFile jbzp,@RequestParam("zjhm") String zjhm) {
        if (zjzp == null || jbzp == null) {
            return R.fail("缺少上传文件");
        }
        Boolean similar = faceService.faceRecognize(zjzp,jbzp,zjhm);
        if(similar){
            return R.ok("人脸比对成功");
        }
        return R.fail("人脸比对失败");
    }


    /**
     * 人脸识别
     *
     * @param faceRecognizeBo 人脸识别bo
     * @return {@link R}<{@link Void}>
     */
    @PostMapping("/faceRecognize")
    public R<Void> faceRecognize(@Validated(AddGroup.class) @RequestBody FaceRecognizeBo faceRecognizeBo) {
        Boolean similar = faceService.faceRecognize(faceRecognizeBo);
        if(similar){
            return R.ok("人脸比对成功");
        }
        return R.fail("人脸比对失败");
    }

    /**
     * 报到
     *
     * @param checkInBo 报到bo
     * @return {@link R}<{@link Void}>
     */
    @PostMapping( "/checkIn")
    public R<Void> checkIn(@Validated(AddGroup.class) @RequestBody CheckInBo checkInBo) {
        if(gateService.handleCheckIn(checkInBo)){
            return R.ok("报到成功");
        }
        return R.fail("报到失败");
    }

    /**
     * 签到
     *
     * @param signInBo 签到bo
     * @return {@link R}<{@link Void}>
     */
    @PostMapping( "/signIn")
    public R<Void> signIn(@Validated(AddGroup.class) @RequestBody SignInBo signInBo) {
        if(gateService.handleSignIn(signInBo)){
            return R.ok("签到成功");
        }
        return R.fail("签到失败");
    }





}
