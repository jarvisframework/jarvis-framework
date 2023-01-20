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
 * @version 1.0.0 2022年5月12日
 */
@Table(name = "app_layer_column")
@ApiModel("层级字段表")
@Getter
@Setter
@ToString
public class LayerColumn extends AbstractSortLongIdEntity {

    /**
     *
     */
    private static final long serialVersionUID = 6635544774735689153L;

    @ApiModelProperty(value = "层级编码", required = true)
    private String layerCode;

    @ApiModelProperty(value = "字段类型：1-系统字段 2-业务字段 3-元数据字段", required = true)
    private Integer type;

    @ApiModelProperty(value = "字段名称",required = true)
    private String columnName;

    @ApiModelProperty(value = "字段显示名称",required = true)
    private String showName;

    @ApiModelProperty(value = "数据类型：varchar-字符型 int-整型 bigint-长整型 date-日期型 datetime-日期时间型 text-文本型 decimal-小数型",required = true)
    private String dataType;

    @ApiModelProperty(value = "字段长度",required = true)
    private Integer length;

    @ApiModelProperty("小数位数")
    private Integer precision;

    @ApiModelProperty("字段默认值")
    private String defaultValue;

    @ApiModelProperty(value = "编码分类编码",required = true)
    private String codeCategoryCode;

    @ApiModelProperty("备注")
    private String remark;

    public void setLayerCode(String layerCode) {
        if (null != layerCode) {
            layerCode = layerCode.trim().toLowerCase();
        }
        this.layerCode = layerCode;
    }

    public String getLayerCode() {
        if (null != this.layerCode) {
            this.layerCode = this.layerCode.trim().toLowerCase();
        }
        return this.layerCode;
    }

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

}
