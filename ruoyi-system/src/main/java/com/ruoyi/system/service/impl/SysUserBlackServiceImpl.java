package com.ruoyi.system.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.core.domain.dto.UserOnlineDTO;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.event.SafeLogEvent;
import com.ruoyi.common.core.domain.event.UserBlackEvent;
import com.ruoyi.common.exception.user.UserException;
import com.ruoyi.common.exception.user.ValidException;
import com.ruoyi.common.helper.LoginHelper;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.utils.redis.RedisUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.common.utils.valid.ValidUtils;
import com.ruoyi.system.domain.SysSafeLog;
import com.ruoyi.system.utils.ConfigUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.bo.SysUserBlackBo;
import com.ruoyi.system.domain.vo.SysUserBlackVo;
import com.ruoyi.system.domain.SysUserBlack;
import com.ruoyi.system.mapper.SysUserBlackMapper;
import com.ruoyi.system.service.ISysUserBlackService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 黑名单管理Service业务层处理
 *
 * @author Lynn
 * @date 2023-03-07
 */
@RequiredArgsConstructor
@Service
public class SysUserBlackServiceImpl implements ISysUserBlackService {

    private final SysUserBlackMapper baseMapper;

    /**
     * 查询黑名单管理
     */
    @Override
    public SysUserBlackVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询黑名单管理列表
     */
    @Override
    public TableDataInfo<SysUserBlackVo> queryPageList(SysUserBlackBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<SysUserBlack> lqw = buildQueryWrapper(bo);
        Page<SysUserBlackVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        for(SysUserBlackVo userBlack:result.getRecords()){
            ValidUtils.setValid(userBlack,SysUserBlack.class);
        }
        return TableDataInfo.build(result);
    }

    /**
     * 查询黑名单管理列表
     */
    @Override
    public List<SysUserBlackVo> queryList(SysUserBlackBo bo) {
        LambdaQueryWrapper<SysUserBlack> lqw = buildQueryWrapper(bo);
        List<SysUserBlackVo> blackList = baseMapper.selectVoList(lqw);
        for(SysUserBlackVo userBlack:blackList){
            ValidUtils.setValid(userBlack,SysUserBlack.class);
        }
        return blackList;
    }

    private LambdaQueryWrapper<SysUserBlack> buildQueryWrapper(SysUserBlackBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<SysUserBlack> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getBlackName()), SysUserBlack::getBlackName, bo.getBlackName());
        lqw.eq(StringUtils.isNotBlank(bo.getBlackType()), SysUserBlack::getBlackType, bo.getBlackType());
        lqw.eq(StringUtils.isNotBlank(bo.getIsLock()), SysUserBlack::getIsLock, bo.getIsLock());
        lqw.between(params.get("beginCreateTime") != null && params.get("endCreateTime") != null,
            SysUserBlack::getCreateTime ,params.get("beginCreateTime"), params.get("endCreateTime"));
        lqw.orderByDesc(SysUserBlack::getCreateTime);
        return lqw;
    }

    /**
     * 新增黑名单管理
     */
    @Override
    public Boolean insertByBo(SysUserBlackBo bo) {
        SysUserBlack add = BeanUtil.toBean(bo, SysUserBlack.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改黑名单管理
     */
    @Override
    public Boolean updateByBo(SysUserBlackBo bo) {
        SysUserBlack update = BeanUtil.toBean(bo, SysUserBlack.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(SysUserBlack entity){
        //TODO 做一些数据校验,如唯一约束
        ValidUtils.setValid(entity);

    }

    /**
     * 批量删除黑名单管理
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
            clearLockCache(ids);
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    /**
     * 记录登录信息
     *
     * @param userBlackEvent 安全事件
     */
    @Async
    @EventListener
    public void recordUserBlack(UserBlackEvent userBlackEvent) {
        // 封装对象
        SysUserBlackBo blackBo = new SysUserBlackBo();
        blackBo.setBlackName(userBlackEvent.getBlackName());
        blackBo.setBlackType(userBlackEvent.getBlackType());
        blackBo.setRemark(userBlackEvent.getRemark());
        blackBo.setIsLock(userBlackEvent.getIsLock());
        // 插入数据
        insertByBo(blackBo);
    }

    /**
     * @Description 添加拉黑事件
     * @Param [blackName, blackType, remark]
     * @return void
     * @Author lynn 14:27 2023/3/7
    **/
    @Override
    public void addBlackEvent(String blackName, String blackType, String remark) {
        UserBlackEvent userBlackEvent = new UserBlackEvent();
        userBlackEvent.setBlackName(blackName);
        userBlackEvent.setBlackType(blackType);
        userBlackEvent.setRemark(remark);
        userBlackEvent.setIsLock("1");
        SpringUtils.context().publishEvent(userBlackEvent);
    }

    /**
     * @Description 解锁
     * @Param [ids]
     * @return java.lang.Boolean
     * @Author lynn 23:02 2023/3/8
    **/
    @Override
    public Boolean unLock(Collection<Long> ids) {
        List<SysUserBlack> userBlackList = clearLockCache(ids);
        for(SysUserBlack userBlack:userBlackList){
            updateByBo(BeanUtil.toBean(userBlack,SysUserBlackBo.class));
        }
        return true;
        // 更新数据
//        LambdaUpdateWrapper<SysUserBlack> updateWrapper = Wrappers.lambdaUpdate();
//        updateWrapper.in(SysUserBlack::getId,ids);
//        updateWrapper.set(SysUserBlack::getIsLock, "0").set(SysUserBlack::getUpdateTime,new Date()).set(SysUserBlack::getUpdateBy,LoginHelper.getLoginUser().getUsername());
//        return baseMapper.update(null,updateWrapper) > 0;
    }

    private List<SysUserBlack> clearLockCache(Collection<Long> ids){
        // 查找数据
        LambdaQueryWrapper<SysUserBlack> lqw = Wrappers.lambdaQuery();
        lqw.in(SysUserBlack::getId,ids);
        List<SysUserBlack> userBlackList = baseMapper.selectList(lqw);
        for(SysUserBlack userBlack : userBlackList){
            ValidUtils.verifyValid(ConfigUtil.getValid(),userBlack);
            userBlack.setIsLock("0");
        }
        //清除黑名单缓存
        for(SysUserBlack userBlack : userBlackList){
            RedisUtils.deleteObject(CacheConstants.USER_BLACK_KEY + userBlack.getBlackName());
        }
        return userBlackList;
    }

}
