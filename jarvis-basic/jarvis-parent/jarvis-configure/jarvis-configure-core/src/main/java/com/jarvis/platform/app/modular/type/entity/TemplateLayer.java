package com.jarvis.platform.app.modular.type.entity;

import com.jarvis.framework.core.entity.AbstractSortLongIdEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Table;

/**
 * @author qiucs
 * @version 1.0.0 2022年4月28日
 */
@Table(name = "app_template_layer")
@ApiModel("模板层次")
@Getter
@Setter
@ToString
public class TemplateLayer extends AbstractSortLongIdEntity {

    /**
     *
     */
    private static final long serialVersionUID = -2932209632431639737L;

    @ApiModelProperty(value = "整理方式模板编码", required = true)
    private String filingCode;

    @ApiModelProperty(value = "名称", required = true)
    private String name;

    @ApiModelProperty(value = "层级编码", required = true)
    private String layerCode;

    @ApiModelProperty(value = "上层编码,顶级为0", required = true)
    private String parentCode;

    @ApiModelProperty("层级备注")
    private String remark;
}
