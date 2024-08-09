package com.kmks.w2.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.kmks.jianguan.domain.bo.A0221000008Bo;
import com.kmks.jianguan.domain.vo.A0221000008Vo;
import com.kmks.jianguan.service.IJgService;
import com.kmks.w2.domain.W2Kcxx;
import com.kmks.w2.domain.W2Records;
import com.kmks.w2.domain.bo.W2KcxxBo;
import com.kmks.w2.domain.dto.SplitCarDto;
import com.kmks.w2.domain.vo.W2KcxxVo;
import com.kmks.w2.mapper.W2KcxxMapper;
import com.kmks.w2.mapper.W2RecordsMapper;
import com.kmks.w2.netty.handler.MessageHandler;
import com.kmks.w2.service.ICarService;
import com.kmks.w2.service.IW2LineconfigService;
import com.kmks.w2.service.IW2QueuingService;
import com.kmks.w2.utils.RedisUtil;
import com.kmks.w2.utils.TcpUtils;
import com.ruoyi.common.constant.CacheNames;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.exception.api.FailException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.common.utils.bean.BeanHelper;
import com.ruoyi.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.kmks.w2.domain.bo.W2QueuingBo;
import com.kmks.w2.domain.vo.W2QueuingVo;
import com.kmks.w2.domain.W2Queuing;
import com.kmks.w2.mapper.W2QueuingMapper;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 排队信息Service业务层处理
 *
 * @author Lynn
 * @date 2023-03-28
 */
@RequiredArgsConstructor
@Service
public class W2QueuingServiceImpl implements IW2QueuingService {

    private final W2QueuingMapper baseMapper;
    private final W2KcxxMapper kcxxMapper;
    private final W2RecordsMapper recordsMapper;

    private final IJgService IJgService;

    private final ISysConfigService configService;

    private final IW2LineconfigService lineconfigService;



    /**
     * 查询排队信息
     */
    @Override
    public W2QueuingVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询排队信息列表
     */
    @Override
    public TableDataInfo<W2QueuingVo> queryPageList(W2QueuingBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<W2Queuing> lqw = buildQueryWrapper(bo);
        Page<W2QueuingVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询排队信息列表
     */
    @Override
    public List<W2QueuingVo> queryList(W2QueuingBo bo) {
        LambdaQueryWrapper<W2Queuing> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<W2Queuing> buildQueryWrapper(W2QueuingBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<W2Queuing> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getKsbh()), W2Queuing::getKsbh, bo.getKsbh());
        lqw.eq(StringUtils.isNotBlank(bo.getXm()), W2Queuing::getXm, bo.getXm());
        lqw.eq(StringUtils.isNotBlank(bo.getZjhm()), W2Queuing::getZjhm, bo.getZjhm());
        lqw.eq(StringUtils.isNotBlank(bo.getKcbh()), W2Queuing::getKcbh, bo.getKcbh());
        lqw.eq(StringUtils.isNotBlank(bo.getKchp()), W2Queuing::getKchp, bo.getKchp());
        lqw.eq(bo.getRLine() != null, W2Queuing::getRLine, bo.getRLine());
        lqw.eq(StringUtils.isNotBlank(bo.getKszt()), W2Queuing::getKszt, bo.getKszt());
        lqw.like(StringUtils.isNotBlank(bo.getKscx()), W2Queuing::getKscx, bo.getKscx());
        lqw.like(StringUtils.isNotBlank(bo.getKgname()), W2Queuing::getKgname, bo.getKgname());
        lqw.orderByAsc(W2Queuing::getBdxh);
        return lqw;
    }

    /**
     * 新增排队信息
     */
    @Override
    public Boolean insertByBo(W2QueuingBo bo) {
        W2Queuing add = BeanUtil.toBean(bo, W2Queuing.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改排队信息
     */
    @Override
    public Boolean updateByBo(W2QueuingBo bo) {
        W2Queuing update = BeanUtil.toBean(bo, W2Queuing.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }



    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(W2Queuing entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除排队信息
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    /**
     * 统计考车人数
     */
    @Override
    public Map<String,Long > getCarPersonNumMap(W2QueuingBo bo) {
        QueryWrapper<W2Queuing> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("kcbh","count(*) as totalNum")
                .select()
                .isNotNull("bdxh")
//                .and(q->{
//                    q.eq(W2Queuing::getKszt,"0").or().eq(W2Queuing::getKszt,"1");
//                })
                .in("kszt",new String[]{"0","1"})
                .eq(StringUtils.isNotBlank(bo.getKcbh()),"kcbh",bo.getKcbh())
                .groupBy("kcbh");
//        Log.info(queryWrapper.getSqlSegment());
        List<W2Queuing> w2Queuings = baseMapper.selectList(queryWrapper);
//        Map<String, String> collect = w2Queuings.stream().collect(Collectors.toMap(W2Queuing::getKcbh, W2Queuing::getCount));
        HashMap<String, Long> map = new HashMap<>();
        CollectionUtil.toMap(w2Queuings,map,W2Queuing::getKcbh,W2Queuing::getTotalNum);
        //调用原生sql
//        List<W2Queuing> w2Queuings = baseMapper.selectCarPersonList();
        return map;



//        LambdaQueryWrapper<W2Queuing> lqw = buildQueryWrapper(bo);
//        return baseMapper.selectVoList(lqw);
    }

    /**
     * 查询约考列表
     */
    @Override
    public TableDataInfo<W2QueuingVo> queryPageListByAbout(W2QueuingBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<W2Queuing> lqw = Wrappers.lambdaQuery();
        lqw.select(W2Queuing::getId,W2Queuing::getKsbh,W2Queuing::getKcbh,W2Queuing::getXm,W2Queuing::getKscx,W2Queuing::getZjhm,W2Queuing::getKsxm,W2Queuing::getJxmc)
            .eq(bo.getKsrq() != null,W2Queuing::getKsrq,bo.getKsrq())
            .eq(StringUtils.isNotBlank(bo.getKcbh()),W2Queuing::getKcbh,bo.getKcbh())
            .eq(StringUtils.isNotBlank(bo.getKcxh()),W2Queuing::getKcxh,bo.getKcxh())
            .eq(StringUtils.isNotBlank(bo.getZjhm()),W2Queuing::getZjhm,bo.getZjhm())
            .eq(StringUtils.isNotBlank(bo.getZkzh()),W2Queuing::getZkzh,bo.getZkzh())
            .eq(StringUtils.isNotBlank(bo.getJxdm()),W2Queuing::getJxdm,bo.getJxdm())
            .eq(StringUtils.isNotBlank(bo.getKszt()),W2Queuing::getKszt,bo.getKszt())
            .isNotNull(W2Queuing::getBdxh)
            .and(q->{
                q.eq(W2Queuing::getKcbh,"99").or().isNull(W2Queuing::getKcbh);
            })
            .orderByAsc(W2Queuing::getBdxh)
        ;
        Page<W2QueuingVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询任务调度列表
     */
    @Override
    public TableDataInfo<W2QueuingVo> queryPageListByTask(W2QueuingBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<W2Queuing> lqw = Wrappers.lambdaQuery();
        lqw.select(W2Queuing::getId,W2Queuing::getBdxh,W2Queuing::getKcbh,W2Queuing::getKsbh,W2Queuing::getXm,W2Queuing::getKsxm,W2Queuing::getYkcs,W2Queuing::getSfyz,W2Queuing::getJxmc)
                .eq(bo.getKsrq() != null,W2Queuing::getKsrq,bo.getKsrq())
                .eq(StringUtils.isNotBlank(bo.getKcbh()),W2Queuing::getKcbh,bo.getKcbh())
                .eq(StringUtils.isNotBlank(bo.getKcxh()),W2Queuing::getKcxh,bo.getKcxh())
                .eq(StringUtils.isNotBlank(bo.getZjhm()),W2Queuing::getZjhm,bo.getZjhm())
                .eq(StringUtils.isNotBlank(bo.getZkzh()),W2Queuing::getZkzh,bo.getZkzh())
                .eq(StringUtils.isNotBlank(bo.getJxdm()),W2Queuing::getJxdm,bo.getJxdm())
                .in(StringUtils.isNotBlank(bo.getKszt()),W2Queuing::getKszt,new String[]{"1","0"})
                .isNotNull(W2Queuing::getBdxh)
                .orderByAsc(W2Queuing::getBdxh)
        ;
        Page<W2QueuingVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 申请考试
     */
    @Override
    public Boolean applyExam(String ids) {
        W2QueuingVo queuingVo = baseMapper.selectVoOne(
                Wrappers.lambdaQuery(W2Queuing.class)
                        .eq(W2Queuing::getZjhm, ids)
                        .eq(W2Queuing::getZt, "1")
        );
        if(queuingVo == null){
            throw new FailException("考生不存在或状态不可申请考试");
        }
        MessageHandler bean = BeanHelper.getBean(MessageHandler.class);
        // 指令标志;车辆编号;时间;身份证;姓名;
        String[] message = new String[]{"2001", queuingVo.getKcbh(), DateUtil.now(), queuingVo.getZjhm(), queuingVo.getXm(), ""};
        bean.cz2001(TcpUtils.getCarChannel(queuingVo.getKcbh()),message);
        return true;
    }

    /**
     * 取消考试
     */
    @Override
    public Boolean cancelExam(String[] ids) {
        LambdaUpdateWrapper<W2Queuing> uw = new LambdaUpdateWrapper<>();
        uw.set(W2Queuing::getKcbh,"")
                .set(W2Queuing::getZt,"0")
                .set(W2Queuing::getKchp,"")
                .in(W2Queuing::getZjhm,ids);
        return baseMapper.update(null,uw) > 0;
    }

    /**
     * 换车
     */
    @Override
    public Boolean changeByBo(W2QueuingBo bo) {
        QueryWrapper<W2Queuing> qw = new QueryWrapper<>();
        qw.select("nvl(max(bdxh),0) + 1").eq("kcbh",bo.getKcbh());
        LambdaUpdateWrapper<W2Queuing> uw = new LambdaUpdateWrapper<>();
        uw.set(W2Queuing::getKcbh,bo.getKcbh())
                .set(W2Queuing::getKchp,bo.getKchp())
                .setSql("bdxh = (select "+qw.getSqlSelect()+" from w2_queuing "+qw.getCustomSqlSegment()+")")
                .eq(W2Queuing::getId,bo.getId());
        return baseMapper.update(null,uw) > 0;
    }

    /**
     * 随机换车
     */
    @Override
    public Boolean randomChangeByBo(W2QueuingBo bo) {
        //获取最少人次的车
        List<W2KcxxVo> w2KcxxVos = queryCarList(new W2KcxxBo());
        W2KcxxVo w2KcxxVoTmp = null;
        for (W2KcxxVo w2KcxxVo : w2KcxxVos) {
            if((w2KcxxVoTmp == null || w2KcxxVoTmp.getPersonNum() > w2KcxxVo.getPersonNum()) && !w2KcxxVo.getKch().equals(bo.getKcbh())){
                w2KcxxVoTmp = w2KcxxVo;
            }
        }
        //确认最少人次车辆并赋值
        if(w2KcxxVoTmp == null) throw new ServiceException("未获取到待分配车辆，请核实");
        bo.setKcbh(w2KcxxVoTmp.getKch());
        bo.setKchp(w2KcxxVoTmp.getCph());
        //换车
        return this.changeByBo(bo);
    }

    /**
     * 上下移动
     */
    @Override
    public Boolean upDownBdxh(Long newIndex,Long oldIndex,Long id) {
        W2QueuingBo bo = new W2QueuingBo();
        LambdaUpdateWrapper<W2Queuing> lqw = new LambdaUpdateWrapper<>();
        //下移动
        if(newIndex > oldIndex){
            lqw.gt(W2Queuing::getBdxh,oldIndex)
               .le(W2Queuing::getBdxh,newIndex)
                    .setSql("bdxh = bdxh - 1");
            ;
        }else{
            //上移动
            lqw.ge(W2Queuing::getBdxh,newIndex)
                    .lt(W2Queuing::getBdxh,oldIndex)
                    .setSql("bdxh = bdxh + 1");
        }

        W2Queuing w2Queuing = new W2Queuing();
        w2Queuing.setId(id);
        w2Queuing.setBdxh(newIndex);
        return baseMapper.update(null,lqw) > 0 && baseMapper.updateById(w2Queuing) > 0;
    }

    /**
     * 考车列表
    **/
    @Override
    public List<W2KcxxVo> queryCarList(W2KcxxBo bo) {
        //获取考车列表
        LambdaQueryWrapper<W2Kcxx> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getKch()), W2Kcxx::getKch, bo.getKch());
        lqw.like(StringUtils.isNotBlank(bo.getCph()), W2Kcxx::getCph, bo.getCph());
        lqw.like(StringUtils.isNotBlank(bo.getKcmc()), W2Kcxx::getKcmc, bo.getKcmc());
        lqw.eq(StringUtils.isNotBlank(bo.getKscx()), W2Kcxx::getKscx, bo.getKscx());
        lqw.eq(StringUtils.isNotBlank(bo.getZt()), W2Kcxx::getZt, "1");
        lqw.select(W2Kcxx::getId,W2Kcxx::getKch,W2Kcxx::getCph,W2Kcxx::getKscx,W2Kcxx::getKcmc);
        List<W2KcxxVo> w2KcxxVos = kcxxMapper.selectVoList(lqw);
        //获取队列中的排队人数
        W2QueuingBo w2QueuingBo = new W2QueuingBo();
        Map<String, Long> carPersonNumMap = getCarPersonNumMap(w2QueuingBo);
        //匹配，循环赋值人数
        for (W2KcxxVo w2KcxxVo : w2KcxxVos) {
            w2KcxxVo.setPersonNum(carPersonNumMap.containsKey(w2KcxxVo.getKch()) ? carPersonNumMap.get(w2KcxxVo.getKch()): 0L);
        }

        return w2KcxxVos;
    }

    /**
     * 获取最大报到序号
     *
     * @return {@link Integer}
     */
    @Override
    public Long getMaxBdxh(){
        return baseMapper.getMaxBdxh();
    }

    /**
     * 分车
     *
     * @return {@link List}<{@link String}>
     */
    @Override
    public Map<String,SplitCarDto> splitCar(){
        // 获取可用考车信息
        LambdaQueryWrapper<W2Kcxx> kcLqw = Wrappers.lambdaQuery(W2Kcxx.class);
        kcLqw.eq(W2Kcxx::getZt,"1");
        List<W2KcxxVo> w2KcxxVos = kcxxMapper.selectVoList(kcLqw);
        List<String> cphList = w2KcxxVos.stream().map(W2KcxxVo::getCph).collect(Collectors.toList());
        if(cphList.size() == 0) throw new FailException("没有可分配的车辆");
        Map<String, W2KcxxVo> kcxxMap = w2KcxxVos.stream().collect(Collectors.toMap(W2KcxxVo::getCph, vo->vo));

        // 获取排队表信息
        LambdaQueryWrapper<W2Queuing> lqw = Wrappers.lambdaQuery();
        lqw.select(W2Queuing::getKchp,W2Queuing::getTotalNum);
        lqw.eq(W2Queuing::getSign,1l);
        lqw.eq(W2Queuing::getZt,"0");
        lqw.eq(W2Queuing::getSqks,1l);
        lqw.groupBy(W2Queuing::getKchp);
        List<W2QueuingVo> w2QueuingVos = baseMapper.selectVoList(lqw);
        if(w2QueuingVos.size() == 0) return null;
        // 获取线路信息
        Map<String, Long> lineConfigMap = lineconfigService.getLineConfigMap(configService.selectConfigByKey(CacheNames.COURSE_KEY));

        Map<String, SplitCarDto> map = new HashMap<>();
        // 获取最大分车人数
        Long splitNum=Long.valueOf(configService.selectConfigByKey(CacheNames.SPLIT_NUM));
        //处理已分车的车辆继续分车
        w2QueuingVos.stream().forEach(vo->{
            if(cphList.contains(vo.getKchp()) && splitNum - vo.getTotalNum() > 0){
                cphList.remove(vo.getKchp());

                // 发送分车指令
                A0221000008Vo a0221000008Vo = splitCar(vo.getKchp(),String.valueOf(splitNum - vo.getTotalNum()));
                handleSplitResult(map, vo.getKchp(),kcxxMap.get(vo.getKchp()), a0221000008Vo, lineConfigMap);


            }
        });
        // 未分车的车辆首次分车
        if(cphList.size() > 0){
            cphList.stream().forEach(cph->{
                // 发送分车指令
                A0221000008Vo a0221000008Vo = splitCar(cph,String.valueOf(splitNum));
                handleSplitResult(map, cph,kcxxMap.get(cph), a0221000008Vo, lineConfigMap);
            });
        }

        return map;
    }

    /**
     * 分车
     *
     * @param kchp     考车号牌
     * @param splitNum 分配人数
     * @return {@link A0221000008Vo}
     */
    public A0221000008Vo splitCar(String kchp,String splitNum){
        A0221000008Bo a0221000008Bo = new A0221000008Bo();
        a0221000008Bo.setKchp(kchp);
        a0221000008Bo.setSjrs(splitNum);
        return IJgService.a0221000008(a0221000008Bo);
    }

    /**
     * 处理分车结果
     *
     * @param splitResultMap 分割结果图
     * @param kchp           kchp
     * @param kcxxVo           kcxxVo
     * @param a0221000008Vo        分车结果
     */
    private void handleSplitResult(Map<String, SplitCarDto> splitResultMap,String kchp,W2KcxxVo kcxxVo,A0221000008Vo a0221000008Vo,Map<String, Long> lineConfigMap){
        String course = configService.selectConfigByKey(CacheNames.COURSE_KEY);
        Map<String, Long> lineConfig = lineConfigMap;
        Arrays.stream(a0221000008Vo.getMessage().split(",")).forEach(zjhm->{
            SplitCarDto splitCarDto = new SplitCarDto();
            splitCarDto.setKcbh(kcxxVo.getKch());
            splitCarDto.setKchp(kchp);
            if(course.equals("3")){
                splitCarDto.setRLine(lineConfig.get(a0221000008Vo.getRetval()));
            }
            splitCarDto.setZt("1");
            splitCarDto.setBdxh(RedisUtil.incrBdxh());
            // 修改排队分车状态
            W2Queuing w2Queuing = BeanUtil.toBean(splitCarDto, W2Queuing.class);
            w2Queuing.setKsxm(configService.selectConfigByKey(CacheNames.PROJECT_IDS_EXPECT_KEY) + ","+kcxxVo.getXmxh());
            w2Queuing.setKcxh(kcxxVo.getClxh());
            baseMapper.update(w2Queuing, Wrappers.lambdaUpdate(W2Queuing.class).eq(W2Queuing::getZjhm,zjhm));
            // 修改成绩表
            W2Records w2Records = new W2Records();
            w2Records.setKcbh(kcxxVo.getKch());
            w2Records.setKchp(kchp);
            w2Records.setKsxm(w2Queuing.getKsxm());
            w2Records.setLine(splitCarDto.getRLine());
            recordsMapper.update(w2Records, Wrappers.lambdaUpdate(W2Records.class).eq(W2Records::getZjhm,zjhm).eq(W2Records::getYkrq, DateUtil.today()));

            splitResultMap.put(zjhm,splitCarDto);
        });
    }
}
