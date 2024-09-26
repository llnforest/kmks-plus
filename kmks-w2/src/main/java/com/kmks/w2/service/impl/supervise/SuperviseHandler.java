package com.kmks.w2.service.impl.supervise;

import com.kmks.w2.service.ISuperviseService;
import com.ruoyi.common.constant.CacheNames;
import com.ruoyi.system.service.ISysConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 监管选择处理器
 *
 * @author Star
 * @description: TODO
 * @date 2024/9/3 13:56
 */
@Service
public class SuperviseHandler {

    @Resource
    private NewSuperviseServiceImpl newSuperviseService;

    @Resource
    private OldSuperviseServiceImpl oldSuperviseService;

    @Resource
    private StandAloneServiceImpl standAloneService;

    @Resource
    private ISysConfigService configService;

    /**
     * 获得监督服务
     *
     * @return {@link ISuperviseService}
     */
    public ISuperviseService service() {
        String jgType = configService.selectConfigByKey(CacheNames.SYS_JK_JG_TYPE);

        if (jgType.equals("1")) {
            return newSuperviseService;
        } else if (jgType.equals("2")) {
            return oldSuperviseService;
        } else if (jgType.equals("3")) {
            return standAloneService;
        }
        return newSuperviseService;
    }
}
