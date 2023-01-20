package com.jarvis.platform.type.modular.archive.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年10月20日
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class DataTypeColumnDTO {

    private String columnName;

    private String dataType;

    public static DataTypeColumnDTO newInstance(String columnName, String dataType) {
        return new DataTypeColumnDTO(columnName, dataType);
    }
}
