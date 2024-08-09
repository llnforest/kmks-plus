package com.kmks.w2.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import com.baomidou.mybatisplus.core.toolkit.AES;
import com.ruoyi.common.core.domain.R;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

/**
 * 工具类接口
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
    private final String AES_KEY = "Ghgd.123456789##";

    /**
     * 获取解密文本
     *
     * @param text text
     * @return {@link R}<{@link String}>
     */
    @RequestMapping("/getDecryptedText")
    public R<String> getDecryptedText(String text){
        String decrypt = AES.decrypt(text, AES_KEY);
        return R.ok(decrypt);
    }

    /**
     * 获取加密文本
     *
     * @param text text明文
     * @return {@link R}<{@link String}>
     */
    @RequestMapping("/getEncryptText")
    public R<String> getEncryptText(String text){
        String encrypt = AES.encrypt(text, AES_KEY);
        return R.ok(encrypt);
    }
}
