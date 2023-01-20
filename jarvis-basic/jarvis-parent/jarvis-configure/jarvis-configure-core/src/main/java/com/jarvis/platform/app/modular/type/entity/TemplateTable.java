package com.jarvis.platform.app.modular.type.entity;

import com.jarvis.framework.core.entity.AbstractSortLongIdEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Table;

/**
 * @author qiucs
 * @version 1.0.0 2022年4月28日
 */
@Table(name = "app_template_table")
@ApiModel("档案门类模板")
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class TemplateTable extends AbstractSortLongIdEntity {

    /**
     *
     */
    private static final long serialVersionUID = 1478831644682350258L;

    @ApiModelProperty("档案类型模板id")
    private Long templateTypeId;

    @ApiModelProperty("父级表模板ID")
    private Long parentId;

    @ApiModelProperty("表模板名称")
    private String name;

    @ApiModelProperty("层级编码")
    private String layerCode;

    @ApiModelProperty("表模板备注")
    private String remark;

}
