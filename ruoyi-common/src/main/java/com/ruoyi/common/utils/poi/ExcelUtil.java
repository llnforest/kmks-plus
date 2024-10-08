package com.ruoyi.common.utils.poi;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.alibaba.excel.write.metadata.fill.FillWrapper;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.ruoyi.common.annotation.Column;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelBigNumberConvert;
import com.ruoyi.common.core.service.DictService;
import com.ruoyi.common.excel.CellMergeStrategy;
import com.ruoyi.common.excel.DefaultExcelListener;
import com.ruoyi.common.excel.ExcelListener;
import com.ruoyi.common.excel.ExcelResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Excel相关处理
 *
 * @author Lion Li
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExcelUtil {

    /**
     * 同步导入(适用于小数据量)
     *
     * @param is 输入流
     * @return 转换后集合
     */
    public static <T> List<T> importExcel(InputStream is, Class<T> clazz) {
        return EasyExcel.read(is).head(clazz).autoCloseStream(false).sheet().doReadSync();
    }


    /**
     * 使用校验监听器 异步导入 同步返回
     *
     * @param is         输入流
     * @param clazz      对象类型
     * @param isValidate 是否 Validator 检验 默认为是
     * @return 转换后集合
     */
    public static <T> ExcelResult<T> importExcel(InputStream is, Class<T> clazz, boolean isValidate) {
        DefaultExcelListener<T> listener = new DefaultExcelListener<>(isValidate);
        EasyExcel.read(is, clazz, listener).sheet().doRead();
        return listener.getExcelResult();
    }

    /**
     * 使用自定义监听器 异步导入 自定义返回
     *
     * @param is       输入流
     * @param clazz    对象类型
     * @param listener 自定义监听器
     * @return 转换后集合
     */
    public static <T> ExcelResult<T> importExcel(InputStream is, Class<T> clazz, ExcelListener<T> listener) {
        EasyExcel.read(is, clazz, listener).sheet().doRead();
        return listener.getExcelResult();
    }


    public static <T> void exportExcel(List<T> list, String sheetName, Class<T> clazz, HttpServletResponse response) {
        try {
            resetResponse(sheetName, response);
            ServletOutputStream os = response.getOutputStream();
            exportExcel(list, sheetName, clazz, false, os);
        } catch (IOException e) {
            throw new RuntimeException("导出Excel异常");
        }
    }


    /**
     * 导出excel
     *
     * @param list      导出数据集合
     * @param sheetName 工作表的名称
     * @param clazz     实体类
     * @param response  响应体
     * @param flag    标记参数
     */
    public static <T> void exportExcel(List<T> list, String sheetName, Class<T> clazz, HttpServletResponse response,String flag) {
        try {

//            resetResponse(sheetName, response);
//            ServletOutputStream os = response.getOutputStream();
//            exportExcel(list, sheetName, clazz, false, os);
            // 创建Excel工作簿
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet();

            //取每一行数据，对每一行的数据进行反射处理
//            int rowNum = 0;
//            Row headRow = sheet.createRow(rowNum);
//            rowNum ++;
//            for (T t : list) {
//                // 反射获取字段
//                Row rowData = sheet.createRow(rowNum);
//                Field[] declaredFields = clazz.getDeclaredFields();
//                int columnNum = 0;
//                for (Field declaredField : declaredFields) {
//                    ExcelProperty excelProperty = declaredField.getDeclaredAnnotation(ExcelProperty.class);
//                    if (excelProperty != null) {
//                        if(rowNum == 1){
//                            Cell headCell = headRow.createCell(columnNum);
//                            String[] value = excelProperty.value();
//                            headCell.setCellValue(value[0]);
//                        }
//                        Cell cellData = rowData.createCell(columnNum);
//                        declaredField.setAccessible(true);
//                        if (ObjectUtil.isNotNull(declaredField.get(t))) {
//                            cellData.setCellValue(String.valueOf(declaredField.get(t)));
//                        }
//
//                        columnNum ++;
//                    }
//                }
//                rowNum ++;
//            }

            //每列的数据进行处理
            List<Row> rowList =new ArrayList<>();
            for (int i = 0; i <= list.size(); i++) {
                rowList.add(sheet.createRow(i));
            }
            Field[] declaredFields = clazz.getDeclaredFields();
            int columnNum = 0;
            for (Field declaredField : declaredFields) {
                int rowNum = 0;
                ExcelProperty excelProperty = declaredField.getDeclaredAnnotation(ExcelProperty.class);
                Column column = declaredField.getDeclaredAnnotation(Column.class);
                if (excelProperty != null && column != null) {
                    // 判断是否存在指定的值
                    if (!ArrayUtil.contains(column.exists(),flag)) {
                        continue;
                    }

                    //添加头部
                    String[] value = excelProperty.value();
                    rowList.get(rowNum).createCell(columnNum).setCellValue(value[0]);
                    //是否使用字典
                    ExcelDictFormat excelDictFormat = declaredField.getDeclaredAnnotation(ExcelDictFormat.class);
                    //添加内容
                    declaredField.setAccessible(true);
                    for (T t : list) {
                        rowNum ++;
                        Object o = declaredField.get(t);
                        if (ObjectUtil.isNotNull(o)) {
                            //有字典值
                            if(excelDictFormat != null){
                                rowList.get(rowNum).createCell(columnNum).setCellValue(SpringUtils.getBean(DictService.class).getDictLabel(excelDictFormat.dictType(), String.valueOf(o), excelDictFormat.separator()));
                            }else{
                                rowList.get(rowNum).createCell(columnNum).setCellValue(String.valueOf(o));
                            }
                        }
                    }
                    columnNum ++;
                }
            }

            // 输出Excel文件
            workbook.write(response.getOutputStream());
            response.getOutputStream().flush();
            response.getOutputStream().close();

        } catch (IOException e) {
            throw new RuntimeException("导出Excel异常");
        } catch (IllegalAccessException e) {
            throw new RuntimeException("导出Excel异常");
        }
    }

    /**
     * 导出excel
     *
     * @param list      导出数据集合
     * @param sheetName 工作表的名称
     * @param clazz     实体类
     * @param merge     是否合并单元格
     * @param response  响应体
     */
    public static <T> void exportExcel(List<T> list, String sheetName, Class<T> clazz, boolean merge, HttpServletResponse response) {
        try {
            resetResponse(sheetName, response);
            ServletOutputStream os = response.getOutputStream();
            exportExcel(list, sheetName, clazz, merge, os);
        } catch (IOException e) {
            throw new RuntimeException("导出Excel异常");
        }
    }

    /**
     * 导出excel
     *
     * @param list      导出数据集合
     * @param sheetName 工作表的名称
     * @param clazz     实体类
     * @param os        输出流
     */
    public static <T> void exportExcel(List<T> list, String sheetName, Class<T> clazz, OutputStream os) {
        exportExcel(list, sheetName, clazz, false, os);
    }

    /**
     * 导出excel
     *
     * @param list      导出数据集合
     * @param sheetName 工作表的名称
     * @param clazz     实体类
     * @param merge     是否合并单元格
     * @param os        输出流
     */
    public static <T> void exportExcel(List<T> list, String sheetName, Class<T> clazz, boolean merge, OutputStream os) {
        ExcelWriterSheetBuilder builder = EasyExcel.write(os, clazz)
            .autoCloseStream(false)
            // 自动适配
            .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
            // 大数值自动转换 防止失真
            .registerConverter(new ExcelBigNumberConvert())
            .sheet(sheetName);

        if (merge) {
            // 合并处理器
            builder.registerWriteHandler(new CellMergeStrategy(list, true));
        }
        builder.doWrite(list);
    }

    /**
     * 单表多数据模板导出 模板格式为 {.属性}
     *
     * @param filename     文件名
     * @param templatePath 模板路径 resource 目录下的路径包括模板文件名
     *                     例如: excel/temp.xlsx
     *                     重点: 模板文件必须放置到启动类对应的 resource 目录下
     * @param data         模板需要的数据
     * @param response     响应体
     */
    public static void exportTemplate(List<Object> data, String filename, String templatePath, HttpServletResponse response) {
        try {
            resetResponse(filename, response);
            ServletOutputStream os = response.getOutputStream();
            exportTemplate(data, templatePath, os);
        } catch (IOException e) {
            throw new RuntimeException("导出Excel异常");
        }
    }

    /**
     * 单表多数据模板导出 模板格式为 {.属性}
     *
     * @param templatePath 模板路径 resource 目录下的路径包括模板文件名
     *                     例如: excel/temp.xlsx
     *                     重点: 模板文件必须放置到启动类对应的 resource 目录下
     * @param data         模板需要的数据
     * @param os           输出流
     */
    public static void exportTemplate(List<Object> data, String templatePath, OutputStream os) {
        ClassPathResource templateResource = new ClassPathResource(templatePath);
        ExcelWriter excelWriter = EasyExcel.write(os)
            .withTemplate(templateResource.getStream())
            .autoCloseStream(false)
            // 大数值自动转换 防止失真
            .registerConverter(new ExcelBigNumberConvert())
            .build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        if (CollUtil.isEmpty(data)) {
            throw new IllegalArgumentException("数据为空");
        }
        // 单表多数据导出 模板格式为 {.属性}
        for (Object d : data) {
            excelWriter.fill(d, writeSheet);
        }
        excelWriter.finish();
    }

    /**
     * 多表多数据模板导出 模板格式为 {key.属性}
     *
     * @param filename     文件名
     * @param templatePath 模板路径 resource 目录下的路径包括模板文件名
     *                     例如: excel/temp.xlsx
     *                     重点: 模板文件必须放置到启动类对应的 resource 目录下
     * @param data         模板需要的数据
     * @param response     响应体
     */
    public static void exportTemplateMultiList(Map<String, Object> data, String filename, String templatePath, HttpServletResponse response) {
        try {
            resetResponse(filename, response);
            ServletOutputStream os = response.getOutputStream();
            exportTemplateMultiList(data, templatePath, os);
        } catch (IOException e) {
            throw new RuntimeException("导出Excel异常");
        }
    }

    /**
     * 多表多数据模板导出 模板格式为 {key.属性}
     *
     * @param templatePath 模板路径 resource 目录下的路径包括模板文件名
     *                     例如: excel/temp.xlsx
     *                     重点: 模板文件必须放置到启动类对应的 resource 目录下
     * @param data         模板需要的数据
     * @param os           输出流
     */
    public static void exportTemplateMultiList(Map<String, Object> data, String templatePath, OutputStream os) {
        ClassPathResource templateResource = new ClassPathResource(templatePath);
        ExcelWriter excelWriter = EasyExcel.write(os)
            .withTemplate(templateResource.getStream())
            .autoCloseStream(false)
            // 大数值自动转换 防止失真
            .registerConverter(new ExcelBigNumberConvert())
            .build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        if (CollUtil.isEmpty(data)) {
            throw new IllegalArgumentException("数据为空");
        }
        for (Map.Entry<String, Object> map : data.entrySet()) {
            // 设置列表后续还有数据
            FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
            if (map.getValue() instanceof Collection) {
                // 多表导出必须使用 FillWrapper
                excelWriter.fill(new FillWrapper(map.getKey(), (Collection<?>) map.getValue()), fillConfig, writeSheet);
            } else {
                excelWriter.fill(map.getValue(), writeSheet);
            }
        }
        excelWriter.finish();
    }

    /**
     * 重置响应体
     */
    private static void resetResponse(String sheetName, HttpServletResponse response) throws UnsupportedEncodingException {
        String filename = encodingFilename(sheetName);
        FileUtils.setAttachmentResponseHeader(response, filename);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8");
    }

    /**
     * 解析导出值 0=男,1=女,2=未知
     *
     * @param propertyValue 参数值
     * @param converterExp  翻译注解
     * @param separator     分隔符
     * @return 解析后值
     */
    public static String convertByExp(String propertyValue, String converterExp, String separator) {
        StringBuilder propertyString = new StringBuilder();
        String[] convertSource = converterExp.split(",");
        for (String item : convertSource) {
            String[] itemArray = item.split("=");
            if (StringUtils.containsAny(propertyValue, separator)) {
                for (String value : propertyValue.split(separator)) {
                    if (itemArray[0].equals(value)) {
                        propertyString.append(itemArray[1] + separator);
                        break;
                    }
                }
            } else {
                if (itemArray[0].equals(propertyValue)) {
                    return itemArray[1];
                }
            }
        }
        return StringUtils.stripEnd(propertyString.toString(), separator);
    }

    /**
     * 反向解析值 男=0,女=1,未知=2
     *
     * @param propertyValue 参数值
     * @param converterExp  翻译注解
     * @param separator     分隔符
     * @return 解析后值
     */
    public static String reverseByExp(String propertyValue, String converterExp, String separator) {
        StringBuilder propertyString = new StringBuilder();
        String[] convertSource = converterExp.split(",");
        for (String item : convertSource) {
            String[] itemArray = item.split("=");
            if (StringUtils.containsAny(propertyValue, separator)) {
                for (String value : propertyValue.split(separator)) {
                    if (itemArray[1].equals(value)) {
                        propertyString.append(itemArray[0] + separator);
                        break;
                    }
                }
            } else {
                if (itemArray[1].equals(propertyValue)) {
                    return itemArray[0];
                }
            }
        }
        return StringUtils.stripEnd(propertyString.toString(), separator);
    }

    /**
     * 编码文件名
     */
    public static String encodingFilename(String filename) {
        return IdUtil.fastSimpleUUID() + "_" + filename + ".xlsx";
    }

}
