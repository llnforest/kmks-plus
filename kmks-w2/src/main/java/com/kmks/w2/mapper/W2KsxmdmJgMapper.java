package com.kmks.w2.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kmks.w2.domain.KfCodeReport;
import com.kmks.w2.domain.vo.W2KsxmdmJgVo;
import com.kmks.w2.domain.W2KsxmdmJg;
import com.ruoyi.common.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 项目代码Mapper接口
 *
 * @author ruoyi
 * @date 2023-03-28
 */
public interface W2KsxmdmJgMapper extends BaseMapperPlus<W2KsxmdmJgMapper, W2KsxmdmJg, W2KsxmdmJgVo> {

     List<W2KsxmdmJgVo> getAllKsxmByStatistics(String kskm);

     Page<KfCodeReport> getTotalKfCodeReport(Page page, @Param("kskm") String kskm);

     String getNameByW2KsxmdmJg(@Param("kskm")String kskm, @Param("custxh")String custxh);


     Page<W2KsxmdmJgVo>  listKsxmdmJgByLogNew(Page page);
}
