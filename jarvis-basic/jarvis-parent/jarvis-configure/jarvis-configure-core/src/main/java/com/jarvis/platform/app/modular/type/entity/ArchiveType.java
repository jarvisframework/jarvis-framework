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
@Table(name = "app_archive_type")
@ApiModel("档案门类")
@Getter
@Setter
@ToString
public class ArchiveType extends AbstractSortLongIdEntity {

    /**
     *
     */
    private static final long serialVersionUID = 2571566900692393562L;

    @ApiModelProperty("源门类id(多库关联时使用）")
    private Long sourceTypeId;

    @ApiModelProperty("是否为逻辑库(库关联时使用）")
    private Integer logical;

    @ApiModelProperty("所属系统ID")
    private Long systemId;

    @ApiModelProperty("单位ID")
    private Long unitId;

    @ApiModelProperty("总部ID")
    private Long hqId;

    @ApiModelProperty("档案组编码")
    private String groupCode;

    @ApiModelProperty("档案门类编码")
    private String code;

    @ApiModelProperty("档案门类名称")
    private String name;

    @ApiModelProperty("整理方式编码")
    private String filingCode;

    @ApiModelProperty("全文类型")
    private String fileType;

    @ApiModelProperty("档案门类模板ID")
    private Long templateTypeId;

    @ApiModelProperty("源库类型")
    private String sourceLibraryCode;

    @ApiModelProperty("库类型")
    private String libraryCode;

    @ApiModelProperty("逻辑库类型")
    private String logicLibraryCode;

    @ApiModelProperty("物理库类型")
    private String physicalLibraryCode;

    @ApiModelProperty("所属门类")
    private String ownerType;

    @ApiModelProperty("档案标准ID")
    private Long standardId;

    @ApiModelProperty("创建索引：0-否 1-是")
    private Integer createIndex;

    @ApiModelProperty("常用门类：0-否 1-是")
    private Integer popular;

    @ApiModelProperty("是否启用：0-否 1-是")
    private Integer enabled;

    @ApiModelProperty("排序号")
    private Integer sortOrder;

    @ApiModelProperty("备注")
    private String remark;

    public void setCode(String code) {
        if (null != code) {
            code = code.trim().toLowerCase();
        }
        this.code = code;
    }

    public String getCode() {
        if (null != this.code) {
            this.code = this.code.trim().toLowerCase();
        }
        return this.code;
    }

}
