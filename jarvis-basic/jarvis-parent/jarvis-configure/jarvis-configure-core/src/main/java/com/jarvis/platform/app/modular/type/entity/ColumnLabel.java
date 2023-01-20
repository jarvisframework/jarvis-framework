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
@Table(name = "app_column_label")
@ApiModel("字段标签")
@Getter
@Setter
@ToString
public class ColumnLabel extends AbstractSortLongIdEntity {

    /**
     *
     */
    private static final long serialVersionUID = 296075952304188368L;

    @ApiModelProperty("标签编码")
    private String code;

    @ApiModelProperty("标签名称")
    private String name;

    @ApiModelProperty("标签绑定编码")
    private String codeCategoryCode;

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
