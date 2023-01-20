package com.jarvis.platform.app.modular.test.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>description</p>
 *
 * @author Doug Wang
 * @since 1.8, 2022-09-09 10:51:21
 */
@ApiModel("测试标准实体DTO")
@Getter
@Setter
@ToString
public class TestDTO {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("name")
    private String name;

    @ApiModelProperty("gender")
    private String gender;

}
