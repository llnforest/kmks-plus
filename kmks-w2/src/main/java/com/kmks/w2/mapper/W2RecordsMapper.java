package com.kmks.w2.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.kmks.w2.domain.W2Records;
import com.kmks.w2.domain.vo.W2RecordsVo;
import com.ruoyi.common.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 成绩管理Mapper接口
 *
 * @author lynn
 * @date 2023-04-06
 */
public interface W2RecordsMapper extends BaseMapperPlus<W2RecordsMapper, W2Records, W2RecordsVo> {

      int getRecordCount(@Param(Constants.WRAPPER) QueryWrapper<W2Records> query);

      int getRecordsCountPersonal(@Param(Constants.WRAPPER) QueryWrapper<W2Records> query);

      int selectCountByGakfdm1( String gakfdm);
      int selectCountByGakfdm2( String gakfdm);

      int selectCountByKfxx1();
      int selectCountByKfxx2();
      List<String> selectSfzByZjhm();

}
