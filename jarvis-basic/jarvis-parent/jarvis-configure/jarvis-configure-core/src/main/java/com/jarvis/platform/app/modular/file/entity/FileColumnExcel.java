package com.jarvis.platform.app.modular.file.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.jarvis.framework.util.CodeEnumUtil;
import com.jarvis.platform.app.enums.DataTypeEnum;
import lombok.Data;
import org.springframework.beans.BeanUtils;


/**
 * @author ronghui
 * @date 2022/8/22
 */
@Data
@ColumnWidth(26)
public class FileColumnExcel extends ExcelData {

    @ExcelVerify(msg = "文件属性", length = 40, notEmpty = true, illegalityChars = "@$&￥")
    @ExcelProperty("文件属性")
    private String fileAttribute;

    @ExcelVerify(msg = "文件属性名称", length = 40, notEmpty = true, illegalityChars = "@$&￥")
    @ExcelProperty("文件属性名称")
    private String fileAttributeName;

    @ExcelVerify(msg = "字段名称", length = 30, notEmpty = true, illegalityChars = "@$&￥")
    @ExcelProperty("字段名称")
    private String columnName;

    @ExcelVerify(msg = "数据类型", notEmpty = true, codeEnum = DataTypeEnum.class)
    @ExcelProperty(value = "数据类型", converter = FileColumnConvert.class)
    private String dataType;

    @ExcelProperty("长度")
    @ColumnWidth(9)
    private String length;

    @ExcelProperty("小数位")
    @ColumnWidth(9)
    private String precision;

    public FileColumn toFileColumn(){
        FileColumn entity = new FileColumn();
        BeanUtils.copyProperties(this, entity);
        entity.setLength(Integer.parseInt(this.getLength()));
        entity.setPrecision(Integer.parseInt(this.getPrecision()));
        return entity;
    }


    public static class FileColumnConvert implements Converter<String> {

        @Override
        public Class<?> supportJavaTypeKey() {
            return String.class;
        }

        @Override
        public CellDataTypeEnum supportExcelTypeKey() {
            return CellDataTypeEnum.STRING;
        }

        @Override
        public String convertToJavaData(CellData cellData,
                                         ExcelContentProperty contentProperty,
                                         GlobalConfiguration globalConfiguration) {
            String value = null;
            if ("dataType".equals(contentProperty.getField().getName())) {
                value = CodeEnumUtil.textEnumMap(DataTypeEnum.class).get(cellData.getStringValue());
                if (value == null) {
                    value = cellData.getStringValue();
                }
            }
            return value;
        }

        @Override
        public CellData<?> convertToExcelData(String value,
                                              ExcelContentProperty contentProperty,
                                              GlobalConfiguration globalConfiguration) {
            CellData<?> cellData = new CellData<>();

            if ("dataType".equals(contentProperty.getField().getName())) {
                cellData.setType(CellDataTypeEnum.STRING);
                cellData.setStringValue(CodeEnumUtil.codeEnumMap(DataTypeEnum.class).get(value));
            }
            return cellData;
        }
    }
}
