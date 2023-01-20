package com.jarvis.platform.app.modular.tree.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年11月1日
 */
@ApiModel("档案树节点")
@Getter
@Setter
@ToString
public class ArchiveTreeNodeDTO {

    @ApiModelProperty("父节点")
    private String parentId;

    @ApiModelProperty("节点类型(0-根节点，1-档案馆节点，2-全宗节点，3-空节点，4-门类节点，5-层级节点，6-静态字段节点，7-动态字段节点，8-归档范围节点)")
    private Integer type;

    @ApiModelProperty("表ID")
    private Long tableId;

    @ApiModelProperty("字段过滤：Q_EQ_xxx=yyy")
    private String filter;

    @ApiModelProperty("节点值")
    private String value;

    @ApiModelProperty("是否显示数据数量(0-否,1-是)")
    private Integer counted;

    @ApiModelProperty("整理方式")
    private String filingCode;

    @ApiModelProperty("层级编码")
    private String layerCode;

}
