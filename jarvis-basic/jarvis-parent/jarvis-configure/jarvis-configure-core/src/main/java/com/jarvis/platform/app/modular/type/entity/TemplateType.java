package com.jarvis.platform.app.modular.type.entity;

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
 * @version 1.0.0 2022年4月28日
 */
@Table(name = "app_template_type")
@ApiModel("档案门类模板")
@Getter
@Setter
@ToString
public class TemplateType extends AbstractSortLongIdEntity {

    /**
     *
     */
    private static final long serialVersionUID = 2571566900692393562L;

    @ApiModelProperty("档案门类模板名称")
    private String name;

    @ApiModelProperty("类型：1-系统 系统初始化都为1 ,2-业务 出库后新增都是2")
    private Integer type = 2;

    @ApiModelProperty("整理方式编码")
    private String filingCode;

    @ApiModelProperty("标准类型编码")
    private String standardTypeCode;

    @ApiModelProperty("档案标准ID")
    private Long standardId;

    @ApiModelProperty("全文类型编码")
    private String fileType;

    @ApiModelProperty("备注")
    private String remark;

}
