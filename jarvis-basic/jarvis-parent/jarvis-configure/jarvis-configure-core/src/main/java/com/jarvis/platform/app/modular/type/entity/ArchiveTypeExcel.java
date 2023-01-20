package com.jarvis.platform.app.modular.type.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import lombok.Data;

/**
 * @Desctription: TODO
 * @Author: liqinhua
 * @Date: Created in 2022/9/14 15:09
 * @Version: 1.0
 */
@Data
public class ArchiveTypeExcel {

    @ExcelIgnore
    private Long systemId;
    @ExcelProperty("所属系统")
    private String systemName;

    @ExcelProperty("档案组编码")
    private String groupCode;

    @ExcelProperty("档案门类编码")
    private String code;

    @ExcelProperty("档案门类名称")
    private String name;

    @ExcelProperty("整理方式")
    private String filingCode;

    @ExcelProperty("全文类型")
    private String fileType;

    @ExcelIgnore
    private Long templateTypeId;
    @ExcelProperty("档案门类模板")
    private String templateTypeName;

    @ExcelProperty("源库类型")
    private String sourceLibraryCode;

    @ExcelProperty("库类型")
    private String libraryCode;

    @ExcelProperty("逻辑库类型")
    private String logicLibraryCode;

    @ExcelProperty("物理库类型")
    private String physicalLibraryCode;

    @ExcelProperty("所属门类")
    private String ownerType;

    @ExcelIgnore
    private Long standardId;
    @ExcelProperty("档案标准")
    private String standardName;

    @ExcelProperty(value = "创建索引", converter = isOrNoConvert.class)
    private Integer createIndex;

    @ExcelProperty(value = "常用门类", converter = isOrNoConvert.class)
    private Integer popular;

    @ExcelProperty(value = "是否启用", converter = isOrNoConvert.class)
    private Integer enabled;


    public static class isOrNoConvert implements Converter<Integer> {
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
            if (value == 1) {
                return new CellData("是");
            }else if(value == 0){
                return new CellData("否");
            }
            return new CellData(value.toString());
        }
    }
}
