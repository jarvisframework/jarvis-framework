package com.jarvis.platform.app.modular.metadata.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author ronghui
 * @Version 1.0.0 2022年09月05日
 */
@ApiModel("元数据标准实体DTO")
@Getter
@Setter
@ToString
public class MetaDataShowDTO {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("编号")
    private String code;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("应用层级")
    private String use;

}
