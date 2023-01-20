package com.jarvis.platform.app.modular.type.entity;

import com.jarvis.framework.core.entity.AbstractSortLongIdEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author qiucs
 * @version 1.0.0 2022年4月28日
 */
@Table(name = "app_archive_table")
@ApiModel("档案门类表")
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class ArchiveTable extends AbstractSortLongIdEntity {

    /**
     *
     */
    private static final long serialVersionUID = -5012059040107897878L;

    @ApiModelProperty("档案门类id")
    private Long typeId;

    @ApiModelProperty("源门类表表id（多库关联时使用）")
    private Long sourceTableId;

    @ApiModelProperty("表模板id")
    private Long templateTableId;

    @ApiModelProperty("父表ID")
    private Long parentId;

    @ApiModelProperty("整理方式")
    private String filingCode;

    @ApiModelProperty("显示名称")
    private String showName;

    @ApiModelProperty("表名称")
    private String tableName;

    @ApiModelProperty("层级编码")
    private String layerCode;

    @Transient
    @ApiModelProperty("层级编码名称")
    private String layerCodeName;

    @ApiModelProperty("父层级编码")
    private String parentCode;

    @Transient
    @ApiModelProperty("父层级编码名称")
    private String parentCodeName;

    @ApiModelProperty("表备注")
    private String remark;

}
