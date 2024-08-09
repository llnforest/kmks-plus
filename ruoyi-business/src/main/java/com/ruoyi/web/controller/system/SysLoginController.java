package com.ruoyi.web.controller.system;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.symmetric.AES;
import com.kmks.w2.service.SafeLoginService;
import com.ruoyi.common.annotation.LoginResult;
import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.constant.MapKeyConstants;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.entity.SysMenu;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginBody;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.domain.model.SmsLoginBody;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.exception.api.FailException;
import com.ruoyi.common.exception.base.BaseException;
import com.ruoyi.common.helper.LoginHelper;
import com.ruoyi.common.utils.redis.CacheUtils;
import com.ruoyi.common.utils.redis.RedisUtils;
import com.ruoyi.system.domain.SysLogininfor;
import com.ruoyi.system.domain.vo.RouterVo;
import com.ruoyi.system.service.ISysLogininforService;
import com.ruoyi.system.service.ISysMenuService;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.system.service.SysLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.spec.SecretKeySpec;
import javax.validation.constraints.NotBlank;
import java.nio.charset.StandardCharsets;
import java.util.*;


/**
 * 登录验证
 *
 * @author Lion Li
 */
@Validated
@RequiredArgsConstructor
@RestController
@Slf4j
public class SysLoginController {

    private final SysLoginService loginService;
    private final ISysMenuService menuService;
    private final ISysUserService userService;

    private final SafeLoginService safeLoginService;

    private final ISysLogininforService logininforService;

    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @LoginResult
    @SaIgnore
    @PostMapping("/login")
    public R<Map<String, Object>> login(@Validated @RequestBody LoginBody loginBody) {
        Map<String, Object> ajax = new HashMap<>();
        // 生成令牌
        String token = "";
        try{
            //----加密----
//            loginBody.setPassword(new String(Base64.getDecoder().decode(loginBody.getPassword()), StandardCharsets.UTF_8));
            AES aes = new AES(Mode.ECB, Padding.ZeroPadding,new SecretKeySpec(Constants.AES_KEY.getBytes(),"AES"));
            loginBody.setPassword(new String(aes.decrypt(loginBody.getPassword()), StandardCharsets.UTF_8));
            String signText = loginBody.getPassword()+loginBody.getUsername()+loginBody.getUuid();
            if(!DigestUtil.sha256Hex(signText).equals(loginBody.getSign())){
                throw new FailException("鉴别信息错误");
            }
//            loginBody.setPassword(original.substring(0,original.length()-suffix.length()));
//            loginBody.setPassword(original);
            token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),loginBody.getUuid());
            CacheUtils.put(CacheConstants.ACCOUNT_OPERATE_TIME,"Bearer "+token, DateUtil.current());
        }catch (BaseException ex){
            safeLoginService.afterLogin(loginBody.getUsername(),ex);
            throw ex;
        }
        ajax.put(Constants.TOKEN, token);
        return R.ok(ajax);
    }

    /**
     * 短信登录(示例)
     *
     * @param smsLoginBody 登录信息
     * @return 结果
     */
    @SaIgnore
    @PostMapping("/smsLogin")
    public R<Map<String, Object>> smsLogin(@Validated @RequestBody SmsLoginBody smsLoginBody) {
        Map<String, Object> ajax = new HashMap<>();
        // 生成令牌
        String token = loginService.smsLogin(smsLoginBody.getPhonenumber(), smsLoginBody.getSmsCode());
        ajax.put(Constants.TOKEN, token);
        return R.ok(ajax);
    }

    /**
     * 小程序登录(示例)
     *
     * @param xcxCode 小程序code
     * @return 结果
     */
    @SaIgnore
    @PostMapping("/xcxLogin")
    public R<Map<String, Object>> xcxLogin(@NotBlank(message = "{xcx.code.not.blank}") String xcxCode) {
        Map<String, Object> ajax = new HashMap<>();
        // 生成令牌
        String token = loginService.xcxLogin(xcxCode);
        ajax.put(Constants.TOKEN, token);
        return R.ok(ajax);
    }

    /**
     * 退出登录
     */
    @SaIgnore
    @PostMapping("/logout")
    public R<Void> logout() {
        loginService.logout();
        return R.ok("退出成功");
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public R<Map<String, Object>> getInfo() {
        LoginUser loginUser = LoginHelper.getLoginUser();
        SysUser user = userService.selectUserById(loginUser.getUserId());

        // 获取上次登录的ip和时间
        Map<String,Object> lastMap = RedisUtils.getCacheMap(CacheConstants.LAST_LOGIN_MAP_KEY+user.getUserName());
        user.setLastLoginDate(Convert.toDate(lastMap.get(MapKeyConstants.LAST_LOGIN_DATE)));
        user.setLastLoginIp(Convert.toStr(lastMap.get(MapKeyConstants.LAST_LOGIN_IP)));

        Map<String, Object> ajax = new HashMap<>();

        ajax.put("user", user);
        ajax.put("roles", loginUser.getRolePermission());
        ajax.put("permissions", loginUser.getMenuPermission());
        return R.ok(ajax);
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public R<List<RouterVo>> getRouters() {
        Long userId = LoginHelper.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        return R.ok(menuService.buildMenus(menus));
    }

    /**
     * 获取系统访问记录列表
     */
    @GetMapping("/failLogin/list")
    public TableDataInfo<SysLogininfor> list(SysLogininfor logininfor, PageQuery pageQuery) {
        logininfor.setStatus("1");
        logininfor.setUserName(LoginHelper.getUsername());
        return logininforService.selectPageLogininforList(logininfor, pageQuery);
    }
}
