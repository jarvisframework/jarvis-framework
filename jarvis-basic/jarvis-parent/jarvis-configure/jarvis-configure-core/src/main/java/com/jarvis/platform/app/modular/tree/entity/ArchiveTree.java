package com.jarvis.platform.app.modular.tree.entity;

import com.jarvis.framework.core.entity.AbstractSortLongIdEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Table;

/**
 * @author ronghui
 * @version 1.0.0 2022年9月16日
 */
@Table(name = "app_archive_tree")
@ApiModel("档案树表")
@Getter
@Setter
@ToString
public class ArchiveTree extends AbstractSortLongIdEntity {

    @ApiModelProperty("系统ID")
    private Long systemId;

    @ApiModelProperty("库类型")
    private String libraryCode;

    @ApiModelProperty("根节点")
    private Long rootId;

    @ApiModelProperty("父节点")
    private Long parentId;

    @ApiModelProperty("节点名称")
    private String name;

    @ApiModelProperty("节点类型(0-根节点，1-档案馆节点，2-全宗节点，3-空节点，4-门类节点，5-层级节点，6-静态字段节点，7-动态字段节点，8-归档范围节点)")
    private Integer type;

    @ApiModelProperty("表ID")
    private Long tableId;

    @ApiModelProperty("字段ID")
    private Long columnId;

    @ApiModelProperty("节点值")
    private String value;

    @ApiModelProperty("分类类型")
    private String classType;

    @ApiModelProperty("分类值")
    private String classValue;

    @ApiModelProperty("是否显示数据数量(0-否,1-是)")
    private Integer counted;

    @ApiModelProperty("是否显示根节点(0-否,1-是)")
    private Integer rooted;

    @ApiModelProperty("节点值设置(1-固定值，2-动态值)")
    private Integer valueType;

    @ApiModelProperty("节点显示设置(1-编码名称，2-编码值，3-编码值(名称))")
    private Integer nameType;

    @ApiModelProperty("整理方式")
    private String filingCode;

    @ApiModelProperty("层级编码")
    private String layerCode;

    @ApiModelProperty("排序方式(ASC-升序,DESC-降序)")
    private String orderBy;

    @ApiModelProperty("单位ID")
    private Long unitId;

    @ApiModelProperty("总部ID")
    private Long hqId;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("是否有子节点 0-无 1-有")
    private Integer child;
}
