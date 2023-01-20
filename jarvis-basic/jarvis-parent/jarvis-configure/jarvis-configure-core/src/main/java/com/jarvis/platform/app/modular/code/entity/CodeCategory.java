package com.jarvis.platform.app.modular.code.entity;

import com.jarvis.framework.core.entity.AbstractSortLongIdEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Table;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年4月29日
 */
@Table(name = "app_code_category")
@ApiModel("数据编码分类")
@Getter
@Setter
@ToString
public class CodeCategory extends AbstractSortLongIdEntity {

    /**
     *
     */
    private static final long serialVersionUID = 1347559107929942751L;

    @ApiModelProperty("类型： 1-系统编码 2-业务编码")
    private Integer type = 1;

    @ApiModelProperty("编码分类编码")
    private String code;

    @ApiModelProperty("编码分类名称")
    private String name;

    @ApiModelProperty("应用场景")
    private String applicationScenario;

    @ApiModelProperty("备注")
    private String remark;
}
