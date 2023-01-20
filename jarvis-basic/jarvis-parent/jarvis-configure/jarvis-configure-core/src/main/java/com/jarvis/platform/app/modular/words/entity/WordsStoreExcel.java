package com.jarvis.platform.app.modular.words.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

@Data
@ColumnWidth(26)
public class WordsStoreExcel extends ExcelData {

    @ExcelVerify(msg = "文件属性名称", length = 500, illegalityChars = "@$&￥")
    @ExcelProperty("词组")
    private String words;

    @ExcelProperty("登记人")
    private String operator;

    @ExcelProperty("登记时间")
    private String createdTime;
}
