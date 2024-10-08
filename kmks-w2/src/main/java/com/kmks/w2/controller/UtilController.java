package com.kmks.w2.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import com.baomidou.mybatisplus.core.toolkit.AES;
import com.kmks.w2.domain.gateDto.EncryptBo;
import com.kmks.w2.domain.gateDto.ImgDataBo;
import com.kmks.w2.utils.FileUtil;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.R;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Base64;

/**
 * 工具类接口
 *
 * @author Star
 * @description: TODO
 * @date 2024/7/22 17:35
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/util")
@Slf4j
@SaIgnore
public class UtilController {

    /**
     * 获取图片资源
     *
     * @param imgDataBo imgDataBo
     * @return {@link R}<{@link String}>
     */
    @PostMapping("/imgData")
    public R<String> imgData(@Validated @RequestBody ImgDataBo imgDataBo) {
        return R.ok("success", FileUtil.convertImageToBase64(imgDataBo.getUrl()));
    }

    /**
     * 获取解密文本
     *
     * @param encryptBo text
     * @return {@link R}<{@link String}>
     */
    @PostMapping("/getDecryptedText")
    public R<String> getDecryptedText(@Validated @RequestBody EncryptBo encryptBo) {
        String decrypt = AES.decrypt(encryptBo.getText(), Constants.DB_AES_KEY);
        return R.ok(decrypt);
    }

    /**
     * 获取加密文本
     *
     * @param encryptBo text明文
     * @return {@link R}<{@link String}>
     */
    @PostMapping("/getEncryptText")
    public R<String> getEncryptText(@Validated @RequestBody EncryptBo encryptBo) {
        String encrypt = AES.encrypt(encryptBo.getText(), Constants.DB_AES_KEY);
        return R.ok(encrypt);
    }

    /**
     * 获取视频窗口号
     *
     * @param a
     * @param b
     * @param c
     * @return {@link R}<{@link Integer}>
     */
    @GetMapping("/getVideoChannel")
    public R<Integer> getVideoChannel(@NotNull(message = "电视墙号不能为空") Integer a,
                                      @NotNull(message = "子窗口号不能为空") Integer b,
                                      @NotNull(message = "窗口号不能为空") Integer c) {
        int channelNum = (a << 24) | (b << 16) | c;
        return R.ok(channelNum);
    }
}
