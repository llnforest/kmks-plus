package com.kmks.w2.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import com.baomidou.mybatisplus.core.toolkit.AES;
import com.kmks.w2.domain.gateDto.EncryptBo;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.R;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    /**
     * 获取解密文本
     *
     * @param encryptBo text
     * @return {@link R}<{@link String}>
     */
    @PostMapping("/getDecryptedText")
    public R<String> getDecryptedText(@Validated @RequestBody EncryptBo encryptBo){
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
    public R<String> getEncryptText(@Validated @RequestBody EncryptBo encryptBo){
        String encrypt = AES.encrypt(encryptBo.getText(), Constants.DB_AES_KEY);
        return R.ok(encrypt);
    }
}
