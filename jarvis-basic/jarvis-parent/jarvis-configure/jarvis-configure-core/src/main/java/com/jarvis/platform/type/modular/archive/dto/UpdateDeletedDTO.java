package com.jarvis.platform.type.modular.archive.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年10月13日
 */
@ApiModel("档案门类逻辑还原参数")
@Getter
@Setter
@ToString
public class UpdateDeletedDTO {

    private String toLocation;

    private List<Long> ids;

    private List<String> setNullColumns;

}
