package com.kmks.w2.mapper;

import com.kmks.w2.domain.bo.W2GpscontentBo;

import com.kmks.w2.domain.W2Gpscontent;
import com.kmks.w2.domain.vo.W2GpscontentVo;
import com.ruoyi.common.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 轨迹数据Mapper接口
 *
 * @author ruoyi
 * @date 2023-04-19
 */
public interface W2GpscontentMapper extends BaseMapperPlus<W2GpscontentMapper, W2Gpscontent, W2GpscontentVo> {

    List<W2Gpscontent> selectListByTable(W2GpscontentBo bo);

    int insertRawSQL(@Param("sql") String sql);

    @Update("CREATE TABLE W2_GPSCONTENT_HIS_${tableName} (TIMEID NUMBER(18,0) NOT NULL, " +
            "GPSCON1 VARCHAR2(2000) NOT NULL, " +
            "GPSCON2 VARCHAR2(2000) NOT NULL, " +
            "SIGCON VARCHAR2(2000) NOT NULL, " +
            "KSCON VARCHAR2(2000) NOT NULL, " +
            "SENDRQ NUMBER(18,0) NOT NULL, " +
            "CARNO VARCHAR2(100), " +
            "SFZHM VARCHAR2(100), " +
            "DJC VARCHAR2(100), " +
            "KCBH VARCHAR2(100), " +
            "LSH VARCHAR2(100), " +
            "KCHP VARCHAR2(100))")
    void createTable(@Param("tableName") String tableName);

    @Select("SELECT COUNT(*) FROM USER_TABLES WHERE TABLE_NAME = UPPER('W2_GPSCONTENT_HIS_${tableName}')")
    int checkTableExists(@Param("tableName") String tableName);
}
