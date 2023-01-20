package com.jarvis.platform.app.modular.type.entity;

import com.jarvis.framework.core.entity.AbstractSortLongIdEntity;
import com.jarvis.platform.app.enums.DataTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Table;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年5月12日
 */
@Table(name = "app_template_column")
@ApiModel("模板字段表")
@Getter
@Setter
@ToString
public class TemplateColumn extends AbstractSortLongIdEntity {

    /**
     *
     */
    private static final long serialVersionUID = 6635544774735689153L;

    @ApiModelProperty("模板表Id")
    private Long templateTableId;

    @ApiModelProperty("字段类型：1-系统字段 2-业务字段 3-元数据字段")
    private Integer type;

    @ApiModelProperty("字段名称")
    private String columnName;

    @ApiModelProperty("字段显示名称")
    private String showName;

    @ApiModelProperty("数据类型：varchar-字符型 int-整型 bigint-长整型 date-日期型 datetime-日期时间型 text-文本型 decimal-小数型")
    private String dataType;

    @ApiModelProperty("字段长度")
    private Integer length;

    @ApiModelProperty("小数位数")
    private Integer precision;

    @ApiModelProperty("允许为空：0-否 1-是")
    private Integer nullable = 1;

    @ApiModelProperty("字段默认值")
    private String defaultValue;

    @ApiModelProperty("编码分类编码")
    private String codeCategoryCode;

    @ApiModelProperty("字段标签")
    private String columnLabel;

    @ApiModelProperty("元数据标准id")
    private Long standardId;

    @ApiModelProperty("元数据编码")
    private String metadataCode;

    @ApiModelProperty("父元素类型：complex-复合型 container-容器型")
    private String parentMetadataType;

    /** 是否录入项：0-否 1-是 * */
    @ApiModelProperty("是否录入项：0-否 1-是")
    private Integer inputable = 1;

    /** 是否检索项：0-否 1-是 * */
    @ApiModelProperty("是否检索项：0-否 1-是")
    private Integer searchable = 1;

    /** 是否列表项：0-否 1-是 * */
    @ApiModelProperty("是否列表项：0-否 1-是")
    private Integer listable = 1;

    /** 是否排序项：0-否 1-是 * */
    @ApiModelProperty("是否排序项：0-否 1-是")
    private Integer sortable = 1;

    /** 是否快捷检索项：0-否 1-是 * */
    @ApiModelProperty("是否快捷检索项：0-否 1-是")
    private Integer rapidable = 0;

    @ApiModelProperty("备注")
    private String remark;

    public void setColumnName(String columnName) {
        if (null != columnName) {
            columnName = columnName.trim().toLowerCase();
        }
        this.columnName = columnName;
    }

    public String getColumnName() {
        if (null != this.columnName) {
            this.columnName = this.columnName.trim().toLowerCase();
        }
        return this.columnName;
    }

    public void setShowName(String showName) {
        if (null != showName) {
            showName = showName.trim().toLowerCase();
        }
        this.showName = showName;
    }

    public String getShowName() {
        if (null != this.showName) {
            this.showName = this.showName.trim().toLowerCase();
        }
        return this.showName;
    }

    public Integer getLength() {
        if (dataType.equals(DataTypeEnum.DATE.value())
                ||dataType.equals(DataTypeEnum.DATETIME.value())
                ||dataType.equals(DataTypeEnum.TEXT.value())) {
            return null;
        }
        return this.length;
    }

    public Integer getPrecision() {
        if (dataType.equals(DataTypeEnum.DECIMAL.value())) {
            return this.precision;
        }
        return null;
    }

    public Integer getNullable() {
        if (0 != this.nullable) {
            return 1;
        }
        return this.nullable;
    }

}
