package com.jarvis.platform.app.modular.type.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.jarvis.framework.util.CodeEnumUtil;
import com.jarvis.platform.app.enums.DataTypeEnum;
import com.jarvis.platform.app.modular.type.constanst.ColumnConstanst;
import lombok.Data;

/**
 * @Desctription: TODO
 * @Author: liqinhua
 * @Date: Created in 2022/9/14 15:30
 * @Version: 1.0
 */
@Data
public class ArchiveColumnExcel {

    @ExcelProperty("表名称")
    private String tableName;

    @ExcelProperty("排序号")
    private Integer sortOrder;

    @ExcelProperty(value = "字段类型", converter = columnTypeConvert.class)
    private Integer type;

    @ExcelProperty("字段名称")
    private String columnName;

    @ExcelProperty("字段显示名称")
    private String showName;

    @ExcelProperty(value = "数据类型", converter = dataTypeConvert.class)
    private String dataType;

    @ExcelProperty("字段长度")
    private Integer length;

    @ExcelProperty("小数位数")
    private Integer precision;

    @ExcelProperty("字段默认值")
    private String defaultValue;

    @ExcelProperty("编码分类编码")
    private String codeCategoryCode;

    @ExcelProperty("字段标签")
    private String columnLabel;

    @ExcelProperty("元数据标准")
    private Long standardId;

    @ExcelProperty("元数据编码")
    private String metadataCode;

    @ExcelProperty(value = "是否录入项", converter = ArchiveTypeExcel.isOrNoConvert.class)
    private Integer inputable;

    @ExcelProperty(value = "是否检索项", converter = ArchiveTypeExcel.isOrNoConvert.class)
    private Integer searchable;

    @ExcelProperty(value = "是否列表项", converter = ArchiveTypeExcel.isOrNoConvert.class)
    private Integer listable;

    @ExcelProperty(value = "是否排序项", converter = ArchiveTypeExcel.isOrNoConvert.class)
    private Integer sortable;

    @ExcelProperty(value = "允许为空", converter = ArchiveTypeExcel.isOrNoConvert.class)
    private Integer nullable;

    @ExcelProperty(value = "是否快捷检索项", converter = ArchiveTypeExcel.isOrNoConvert.class)
    private Integer rapidable;

    @ExcelProperty("备注")
    private String remark;

    @ExcelProperty("元素类型")
    private String parentMetadataType;


    public static class columnTypeConvert implements Converter<Integer> {
        @Override
        public Class<?> supportJavaTypeKey() {
            return Integer.class;
        }

        @Override
        public CellDataTypeEnum supportExcelTypeKey() {
            return CellDataTypeEnum.STRING;
        }

        @Override
        public Integer convertToJavaData(CellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
            return null;
        }

        @Override
        public CellData<?> convertToExcelData(Integer value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
            if (value == ColumnConstanst.TYPE_SYSTEM) {
                return new CellData("系统字段");
            }else if(value == ColumnConstanst.TYPE_OPERATION){
                return new CellData("业务字段");
            }else if(value == ColumnConstanst.TYPE_META){
                return new CellData("元数据字段");
            }
            return new CellData(value.toString());
        }
    }

    public static class dataTypeConvert implements Converter<String> {
        @Override
        public Class<?> supportJavaTypeKey() {
            return String.class;
        }

        @Override
        public CellDataTypeEnum supportExcelTypeKey() {
            return CellDataTypeEnum.STRING;
        }

        @Override
        public String convertToJavaData(CellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
            return null;
        }

        @Override
        public CellData<?> convertToExcelData(String value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
            String text = CodeEnumUtil.codeEnumMap(DataTypeEnum.class).get(value);
            if (text != null ) {
                return new CellData(text);
            }
            return new CellData(value);
        }
    }
}
