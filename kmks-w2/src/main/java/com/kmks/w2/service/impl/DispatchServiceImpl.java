package com.kmks.w2.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.kmks.w2.domain.W2Flowrec;
import com.kmks.w2.domain.W2Queuing;
import com.kmks.w2.domain.bo.W2FlowBo;
import com.kmks.w2.domain.dto.DispatchCarDto;
import com.kmks.w2.domain.dto.DispatchCenterDto;
import com.kmks.w2.domain.vo.*;
import com.kmks.w2.mapper.W2FlowrecMapper;
import com.kmks.w2.mapper.W2QueuingMapper;
import com.kmks.w2.service.*;
import com.kmks.w2.utils.TcpUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Star
 * @description: TODO
 * @date 2024/5/31 15:18
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class DispatchServiceImpl implements IDispatchService {

    private final W2QueuingMapper queuingMapper;

    private final W2FlowrecMapper flowrecMapper;


    /**
     * 车辆排队列表
     *
     * @return {@link List}<{@link DispatchCarDto}>
     */
    @Override
    public List<DispatchCarDto> carQueueList(){
        // 查找素有排队人员
        List<W2QueuingVo> w2QueuingVos = queuingMapper.selectVoList(
                Wrappers.lambdaQuery(W2Queuing.class)
                        .select(W2Queuing::getKcbh, W2Queuing::getXm)
                        .eq(W2Queuing::getKszt, "0")
                        .eq(W2Queuing::getZt, "1")
                        .eq(W2Queuing::getKsrq, DateUtil.beginOfDay(new Date()))
                        .orderByAsc(W2Queuing::getBdxh)
        );
        if(w2QueuingVos == null) return null;
        // 按车辆分组
        Map<String, List<W2QueuingVo>> carMap = w2QueuingVos.stream().collect(Collectors.groupingBy(W2QueuingVo::getKcbh));

        // 转成所需list
        List<DispatchCarDto> list = carMap.entrySet().stream().map(entry -> {
            ArrayList<String> xms = new ArrayList<>();
            entry.getValue().forEach(vo -> {
                xms.add(vo.getXm());
            });
            return new DispatchCarDto(entry.getKey(), entry.getValue().size(), StringUtils.join(xms, ","));
        }).collect(Collectors.toList());
       return list;
    }

    /**
     * 获取调度中心列表
     *
     * @return {@link List}<{@link DispatchCenterDto}>
     */
    @Override
    public List<DispatchCenterDto> getDispatchCenterList(){
        List<DispatchCenterDto> list = TcpUtils.getDispatchCenterMap().entrySet().stream()
                .map(entry -> entry.getValue())
                .collect(Collectors.toList());
        return list;
        // 测试数据
//        List<DispatchCenterDto> newList = new ArrayList<DispatchCenterDto>();
//        DispatchCenterDto dispatchCenterDto = new DispatchCenterDto();
//        dispatchCenterDto.setKcbh("05");
//        dispatchCenterDto.setKcxx("皖J65988");
//        dispatchCenterDto.setKszt("考试结束");
//        dispatchCenterDto.setZjhm("210381198202245023");
//        dispatchCenterDto.setKsxm("李巍");
//        dispatchCenterDto.setSczt("上传成功");
//        dispatchCenterDto.setKsfs(70l);
//        dispatchCenterDto.setKscj("4");
//        dispatchCenterDto.setZt(0);
//        dispatchCenterDto.setDqxm("坡道定点起步");
//        dispatchCenterDto.setKscs(2l);
//        newList.add(dispatchCenterDto);
//        DispatchCenterDto dispatchCenterDto1 = BeanUtil.toBean(dispatchCenterDto, DispatchCenterDto.class);
//        dispatchCenterDto1.setZjhm("210381198202245024");
//        dispatchCenterDto1.setKsxm("李巍2");
//        dispatchCenterDto1.setKsfs(75l);
//        newList.add(dispatchCenterDto1);
//        return newList;

    }

    /**
     * 获取考试明细列表
     *
     * @param bo bo
     * @return {@link List}<{@link W2FlowVo}>
     */
    @Override
    public List<W2FlowVo> getFlowList(W2FlowBo bo){
        List<W2FlowVo> w2FlowVos = flowrecMapper.selectVoList(
                Wrappers.lambdaQuery(W2Flowrec.class)
                        .eq(W2Flowrec::getYkrq, DateUtil.today())
                        .eq(StringUtils.isNotBlank(bo.getZjhm()),W2Flowrec::getZjhm, bo.getZjhm())
                        .orderByDesc(W2Flowrec::getKscs)
                        .orderByDesc(W2Flowrec::getKssj)
                        .orderByDesc(W2Flowrec::getId)
        );
        return w2FlowVos;
    }


}
