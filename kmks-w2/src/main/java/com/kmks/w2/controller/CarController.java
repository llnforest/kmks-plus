package com.kmks.w2.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.net.NetUtil;
import com.kmks.w2.domain.bo.*;
import com.kmks.w2.domain.carDto.*;
import com.kmks.w2.domain.vo.*;
import com.kmks.w2.service.*;
import com.ruoyi.common.constant.CacheNames;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.exception.api.FailException;
import com.ruoyi.system.domain.SysOperLogParam;
import com.ruoyi.system.service.ISysComputerService;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.ISysOperLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 车载接口
 *
 * @author lynn
 * @date 2023-05-04
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/car")
@Slf4j
@SaIgnore
public class CarController extends BaseController {
    private final ISysConfigService configService;

    private final IW2KfconfigService kfconfigService;
    private final IW2CarsignalService carsignalService;
    private final IW2CarModelService carModelService;
    private final IW2FieldmapService fieldmapService;
    private final IW2KcxxService kcxxService;
    private final IW2KsxmkfdmJgService ksxmkfdmJgService;

    private final IW2KcxxCheckService kcxxCheckService;

    private final ISysComputerService computerService;

    private final ISysOperLogService operLogService;

    /**
     * 1.车辆信号
     *
     * @param kch 考车号
     * @return {@link List}<{@link CarSignalDto}>
     */
    @RequestMapping("/carSignal")
    public R<List<CarSignalDto>> carSignal(@RequestParam(name = "kch") String kch){
        // 获取车辆信号并转数据结构
        W2CarsignalBo w2CarsignalBo = new W2CarsignalBo();
        w2CarsignalBo.setSignalNote("1");
        List<W2CarsignalVo> vos = carsignalService.queryList(w2CarsignalBo);
        List<CarSignalDto> carSignalDtos = vos.stream().map(vo->new CarSignalDto(vo.getSignalKey(), vo.getSignalValue())).collect(Collectors.toList());
        return R.ok(carSignalDtos);
    }

    /**
     * 2.车辆自检
     *
     * @param carSelfCheckDto 考车校验信息
     * @return {@link R}<{@link Void}>
     */
    @RequestMapping("/carSelfCheck")
    public R<CarSelfCheckDto> carSelfCheck(@Validated CarSelfCheckDto carSelfCheckDto){
        // 获取考车信息数据并转数据结构
        W2KcxxBo w2KcxxBo = new W2KcxxBo();
        w2KcxxBo.setKch(carSelfCheckDto.getKch());
        W2KcxxVo vo = kcxxService.queryOne(w2KcxxBo);
        CarInfoDto carInfoDto = BeanUtil.toBean(vo,CarInfoDto.class);
        W2KcxxCheckBo w2KcxxCheckBo = new W2KcxxCheckBo();
        w2KcxxCheckBo.setCheckContent(carSelfCheckDto.getCheckContent());
        w2KcxxCheckBo.setKcbh(carSelfCheckDto.getKch());
        w2KcxxCheckBo.setCheckTime(DateUtil.date());
        w2KcxxCheckBo.setCheckResult(carSelfCheckDto.getCheckResult());
        if(carInfoDto == null || !carInfoDto.getZt().equals("1")) {
            kcxxCheckService.insertByBo(w2KcxxCheckBo);
            throw new FailException("车辆自检失败，考车不可用或编号不存在");
        }
        w2KcxxBo.setId(vo.getId());
        w2KcxxBo.setCheckResult1(carSelfCheckDto.getCheckResult() == 1?1l:2l);
        w2KcxxBo.setCheckTime1(DateUtil.date());
        kcxxCheckService.insertByBo(w2KcxxCheckBo);
        if(kcxxService.updateByBo(w2KcxxBo)){
            return R.ok(carSelfCheckDto);
        }
        throw new FailException("车辆自检保存失败");
    }

    /**
     * 3.考车信息校验
     *
     * @param carCheckDto 考车校验信息
     * @return {@link R}<{@link Void}>
     */
    @RequestMapping("/carCheck")
    public R<Void> carCheck(@Validated CarCheckDto carCheckDto){
        // 获取考车信息数据并转数据结构
        W2KcxxBo w2KcxxBo = new W2KcxxBo();
        w2KcxxBo.setKch(carCheckDto.getKch());
        w2KcxxBo.setCph(carCheckDto.getCph());
        w2KcxxBo.setCarIp(carCheckDto.getCarIp());
        w2KcxxBo.setCarMac(carCheckDto.getCarMac());
        w2KcxxBo.setCarVersion(carCheckDto.getCarVersion());
        W2KcxxVo vo = kcxxService.queryOne(w2KcxxBo);
        CarInfoDto carInfoDto = BeanUtil.toBean(vo,CarInfoDto.class);
        if(carInfoDto == null  || !carInfoDto.getZt().equals("1")) {
            insertOperateLog(carCheckDto.getKch(),1,"采集评判软件IP【"+carCheckDto.getCarIp()+ "】、MAC【"+carCheckDto.getCarMac()+"】、考车号【"+carCheckDto.getKch()+"】、车牌号【"+carCheckDto.getCph()+"】、版本号【"+carCheckDto.getCarVersion()+"】校验失败");
            throw new FailException("车辆校验失败，考车不可用或校验信息错误");
        }
//        if(!vo.getCheckResult1().equals(1l)){
//            throw new FailException("车辆校验失败，考车未自检成功");
//        }
        w2KcxxBo.setId(vo.getId());
        w2KcxxBo.setCheckResult2(1l);
        return toAjax(kcxxService.updateByBo(w2KcxxBo));
    }

    /**
     * 4.中心配置
     *
     * @param kch 考车号
     * @return {@link List}<{@link CenterConfigDto}>
     */
    @RequestMapping("/centerConfig")
    public R<List<CenterConfigDto>> centerConfig(@RequestParam(name = "kch") String kch){
        // 获取中心配置并转数据结构
        W2KfconfigBo w2KfconfigBo = new W2KfconfigBo();
        HashMap<String, Object> map = new HashMap<>();
        map.put("judgeType","1");
        w2KfconfigBo.setParams(map);

        List<W2KfconfigVo> vos = kfconfigService.queryList(w2KfconfigBo);
        List<CenterConfigDto> centerConfigDtos = vos.stream().map(vo->new CenterConfigDto(vo.getGakey(), vo.getValue())).collect(Collectors.toList());
        return R.ok(centerConfigDtos);
    }

    /**
     * 4.扣分代码（新）
     *
     * @param kch 考车号
     * @return {@link List}<{@link CenterConfigDto}>
     */
    @RequestMapping("/centerKf")
    public R<List<CenterKfDto>> centerKf(@RequestParam(name = "kch") String kch){
        // 获取中心配置并转数据结构
        W2KfconfigBo w2KfconfigBo = new W2KfconfigBo();
        HashMap<String, Object> map = new HashMap<>();
        map.put("judgeType","2");
        w2KfconfigBo.setParams(map);
        w2KfconfigBo.setAutoflag("1");
        List<W2KfconfigVo> vos = kfconfigService.queryList(w2KfconfigBo);
        List<CenterKfDto> CenterKfDtos = vos.stream().map(vo->new CenterKfDto(vo.getGakfdm(), vo.getGakfmc(),vo.getKf(),vo.getValue())).collect(Collectors.toList());
        return R.ok(CenterKfDtos);
    }



    /**
     * 4.扣分信息
     *
     * @param kch 考车号
     * @return {@link List}<{@link DeductPointsDto}>
     */
    @RequestMapping("/deductPoints")
    public R<List<DeductPointsDto>> deductPoints(@RequestParam(name = "kch") String kch){
        //获取系统考试科目
        W2KsxmkfdmJgBo w2KsxmkfdmJgBo = new W2KsxmkfdmJgBo();

        // 获取扣分数据并转数据结构
        List<W2KsxmkfdmJgVo> vos = ksxmkfdmJgService.queryList(w2KsxmkfdmJgBo);
        List<DeductPointsDto> deductPointsDtos = vos.stream().map(vo->new DeductPointsDto(String.valueOf(vo.getId()), vo.getGakfdm(),vo.getKfmc(),String.valueOf(vo.getKf()),String.valueOf(vo.getFlag()))).collect(Collectors.toList());
        return R.ok(deductPointsDtos);
    }

    /**
     * 4.车辆模型
     *
     * @param kch 考车号
     * @return {@link List}<{@link CarModelDto}>
     */
    @RequestMapping("/carModel")
    public R<List<CarModelDto>> carModel(@RequestParam(name = "kch",required = true) String kch){
        // 获取考车信息
        W2KcxxBo w2KcxxBo = new W2KcxxBo();
        w2KcxxBo.setKch(kch);
        W2KcxxVo w2KcxxVo = kcxxService.queryOne(w2KcxxBo);
        if(w2KcxxVo == null || !w2KcxxVo.getZt().equals("1")) throw new FailException("考车不可用或编号不存在");
        // 获取车模数据并转数据结构
        W2CarModelBo w2CarModelBo = new W2CarModelBo();
        w2CarModelBo.setRelationId(w2KcxxVo.getCarModelRelation());
        List<W2CarModelVo> vos = carModelService.queryList(w2CarModelBo);

        List<CarModelDto> collect = vos.stream().map(vo -> new CarModelDto(vo.getModelname(), vo.getModeltype(), vo.getPointdata(), vo.getState(), vo.getScode())).collect(Collectors.toList());
        return R.ok(collect);
    }

    /**
     * 4.场地地图
     *
     * @param kch 考车号
     * @return {@link List}<{@link FieldMapDto}>
     */
    @RequestMapping("/fieldMap")
    public R<List<FieldMapDto>> fieldMap(@RequestParam(name = "kch") String kch){
        // 获取考车信息
        W2KcxxBo w2KcxxBo = new W2KcxxBo();
        w2KcxxBo.setKch(kch);
        W2KcxxVo w2KcxxVo = kcxxService.queryOne(w2KcxxBo);
        if(w2KcxxVo == null || !w2KcxxVo.getZt().equals("1")) throw new FailException("考车不可用或编号不存在");

        // 获取场地地图数据并转数据结构
        W2FieldmapBo w2FieldmapBo = new W2FieldmapBo();
        w2FieldmapBo.setRelationId(w2KcxxVo.getCarFieldRelation());
        List<W2FieldmapVo> vos = fieldmapService.queryList(w2FieldmapBo);
        List<FieldMapDto> fieldMapDtos = vos.stream().map(vo->new FieldMapDto(vo.getFieldname(), vo.getFieldid(),vo.getFieldtype(),vo.getPointcount(),vo.getPointdata(),vo.getPointposition(),vo.getLineno(),vo.getState(),vo.getScode())).collect(Collectors.toList());
        return R.ok(fieldMapDtos);
    }

    /**
     * 4.考车信息
     *
     * @param kch 考车号
     * @return {@link List}<{@link CarInfoDto}>
     */
    @RequestMapping("/carInfo")
    public R<CarInfoDto> carInfo(@RequestParam(name = "kch") String kch){
        isCanVisited(kch);
        // 获取考车信息数据并转数据结构
        W2KcxxBo w2KcxxBo = new W2KcxxBo();
        w2KcxxBo.setKch(kch);
        W2KcxxVo vo = kcxxService.queryOne(w2KcxxBo);
        CarInfoDto carInfoDto = BeanUtil.toBean(vo,CarInfoDto.class);
        if(carInfoDto == null || !carInfoDto.getZt().equals("1")) throw new FailException("考车不可用或编号不存在");

        w2KcxxBo.setId(vo.getId());
        w2KcxxBo.setCheckResult3(1l);
        w2KcxxBo.setCheckTime(DateUtil.date());
        kcxxService.updateByBo(w2KcxxBo);
        return R.ok(carInfoDto);
    }

    private void isCanVisited(String kch){
        if(!computerService.visitedByIpAndMac(NetUtil.getLocalhostStr(),NetUtil.getLocalMacAddress())){
            // 发送操作日志
            insertOperateLog(kch,1,"计算机访问IP【"+NetUtil.getLocalhostStr()+ "】、MAC【"+NetUtil.getLocalMacAddress()+"】无法访问参数库");
            throw new FailException("IP:"+NetUtil.getLocalhostStr()+ "或 MAC:"+NetUtil.getLocalMacAddress()+" 无法访问参数库");
        }else{
            insertOperateLog(kch,0,"");
        }
    }

    private void insertOperateLog(String kch,Integer status,String msg){
        SysOperLogParam operLog = new SysOperLogParam();
        operLog.setTitle(configService.selectConfigByKey(CacheNames.SCHOOL_NAME));
        operLog.setOperatorType(5);
        operLog.setOperIp(NetUtil.getLocalhostStr());
        operLog.setMac(NetUtil.getLocalMacAddress());
        operLog.setOperTime(DateUtil.date());
        operLog.setKch(kch+"号车");
        operLog.setBusinessType(11);
        operLog.setStatus(status);
        if(status == 0){
            operLog.setRemark(kch+"号车的车辆参数、模型参数、评判参数下载成功！");
            operLogService.deleteParamOperlog(configService.selectConfigByKey(CacheNames.SCHOOL_NAME)+kch+"号车"+DateUtil.today()+"当日参数未下载");
        }else{
            operLog.setRemark(kch+"号车的车辆参数、模型参数、评判参数下载失败！"+msg);
        }

        operLogService.insertParamOperlog(operLog);
    }


}
