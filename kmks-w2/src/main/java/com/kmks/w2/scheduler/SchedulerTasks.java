package com.kmks.w2.scheduler;

import com.kmks.w2.domain.bo.W2FlowBo;
import com.kmks.w2.domain.bo.W2QueuingBo;
import com.kmks.w2.domain.dto.DispatchCarDto;
import com.kmks.w2.domain.dto.DispatchCenterDto;
import com.kmks.w2.domain.vo.W2FlowVo;
import com.kmks.w2.domain.vo.W2QueuingVo;
import com.kmks.w2.service.IDispatchService;
import com.kmks.w2.service.IW2FlowlogService;
import com.kmks.w2.service.IW2KcxxService;
import com.kmks.w2.service.IW2QueuingService;
import com.kmks.w2.service.impl.supervise.SuperviseHandler;
import com.kmks.w2.utils.RedisUtil;
import com.kmks.w2.websocket.map.DispatchDataMap;
import com.kmks.w2.websocket.map.QueueDataMap;
import com.ruoyi.common.constant.CacheNames;
import com.ruoyi.common.core.domain.PageQuery;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.JsonUtils;
import com.ruoyi.system.service.ISysConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @author Star
 * @description: TODO
 * @date 2023/10/9 17:29
 */
@Slf4j
@Service
public class SchedulerTasks {
    @Resource
    private IW2QueuingService queuingService;

    @Resource
    private IDispatchService dispatchService;

    @Resource
    private IW2KcxxService kcxxService;

    @Resource
    private IW2FlowlogService flowlogService;

    @Resource
    private ISysConfigService configService;

    @Resource
    private SuperviseHandler superviseHandler;

    /**
     * 调度中心列表定时更新
     *
     * @return {@link Boolean}
     */
    @Scheduled(fixedDelay = 5000)//5秒执行一次
    public void handleDisPatchData() {
                // code 1 调度中心数据  2过程明细数据  3待考列表数据  4、呼叫中心
        DispatchDataMap.getDispatchSocketsBeanMap().forEach((key, value)->{
            try {
                // 调度中心数据
                List<DispatchCenterDto> dispatchCenterList = dispatchService.getDispatchCenterList();
                value.session.getBasicRemote().sendText(JsonUtils.toJsonString(R.ok(1,dispatchCenterList)));

                // 待考列表数据
                List<DispatchCarDto> dispatchCarDtos = dispatchService.carQueueList();
                value.session.getBasicRemote().sendText(JsonUtils.toJsonString(R.ok(3,dispatchCarDtos)));

                // 过程明细数据
                // 获取查询条件
                String message = DispatchDataMap.getQueryStringMap(key);
                if(message == null) return;
                W2FlowBo w2FlowBo = JsonUtils.parseObject(message, W2FlowBo.class);


                // 查询结果转成json字符串发送给客户端
                List<W2FlowVo> flowList = dispatchService.getFlowList(w2FlowBo);
                value.session.getBasicRemote().sendText(JsonUtils.toJsonString(R.ok(2,flowList)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * 排队列表定时更新
     *
     * @return {@link Boolean}
     */
    @Scheduled(fixedDelay = 10000)//10秒执行一次
    public void handleQueueData() {
        QueueDataMap.getQueueSocketsBeanMap().forEach((key,value)->{
            try {
                // 获取查询条件
                String message = QueueDataMap.getQueryStringMap(key);
                if(message == null) return;
                W2QueuingBo w2QueuingBo = JsonUtils.parseObject(message, W2QueuingBo.class);
                PageQuery pageQuery = JsonUtils.parseObject(message, PageQuery.class);
                // 查询结果转成json字符串发送给客户端
                TableDataInfo<W2QueuingVo> w2QueuingVoTableDataInfo = queuingService.queryPageList(w2QueuingBo, pageQuery);
                value.session.getBasicRemote().sendText(JsonUtils.toJsonString(w2QueuingVoTableDataInfo));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * 分车定时任务
     *
     * @return {@link Boolean}
     */
    @Scheduled(fixedDelay = 8000)//8秒执行一次
    public void handleSplitCarData() {
        // 是否启动分车
        if(configService.selectConfigByKey(CacheNames.SPLIT_STATUS).equals("1")){
            superviseHandler.service().splitCar();
        }
    }

    /**
     * 每日重置任务
     *
     * @return {@link Boolean}
     */
    @Scheduled(cron = "0 1 0 * * *")//每天零时00:01执行
    public void resetData() {
        // 排队信息同步到备份表中
        queuingService.syncToHistory();

        // 重置每日报到序号
        RedisUtil.setMaxBdxh(0l);

        // 重置考车自检信息
        kcxxService.resetKcxxCheck();

        // 过程明细同步到备份表中
        flowlogService.syncToHistory();


    }

}
