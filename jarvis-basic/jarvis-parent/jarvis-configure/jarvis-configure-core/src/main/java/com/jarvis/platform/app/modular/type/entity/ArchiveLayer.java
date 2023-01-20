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
@Table(name = "app_archive_layer")
@ApiModel("档案层次")
@Getter
@Setter
@ToString
public class ArchiveLayer extends AbstractSortLongIdEntity {

    /**
     *
     */
    private static final long serialVersionUID = -4706854859710148358L;

    @ApiModelProperty("层级编码")
    private String code;

    @ApiModelProperty("层级名称")
    private String name;

    @ApiModelProperty("类型：1-系统 2-业务")
    private Integer type = 2;

    @ApiModelProperty("优先顺序")
    private Integer priority;

    @ApiModelProperty("层级备注")
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
