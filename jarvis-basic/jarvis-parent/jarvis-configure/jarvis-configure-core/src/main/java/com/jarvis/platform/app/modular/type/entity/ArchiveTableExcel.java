package com.jarvis.platform.app.modular.type.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @Desctription: TODO
 * @Author: liqinhua
 * @Date: Created in 2022/9/14 15:27
 * @Version: 1.0
 */
@Data
public class ArchiveTableExcel {

    @ExcelIgnore
    private Long templateTableId;
    @ExcelProperty("表模板")
    private String templateTable;

    @ExcelIgnore
    private Long parentId;
    @ExcelProperty("父表")
    private String parentTable;

    @ExcelProperty("整理方式")
    private String filingCode;

    @ExcelProperty("显示名称")
    private String showName;

    @ExcelProperty("表名称")
    private String tableName;

    @ExcelProperty("层级编码")
    private String layerCode;

    @ExcelProperty("层级编码名称")
    private String layerCodeName;

    @ExcelProperty("父层级编码")
    private String parentCode;

    @ExcelProperty("父层级编码名称")
    private String parentCodeName;

    @ExcelProperty("表备注")
    private String remark;
}
