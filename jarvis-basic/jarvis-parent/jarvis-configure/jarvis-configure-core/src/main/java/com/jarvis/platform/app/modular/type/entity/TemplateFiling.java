package com.jarvis.platform.app.modular.type.entity;

import com.jarvis.framework.core.entity.AbstractSortLongIdEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年4月28日
 */
@Table(name = "app_template_filing")
@ApiModel("整理方式模板")
@Getter
@Setter
@ToString
public class TemplateFiling extends AbstractSortLongIdEntity {

    /**
     *
     */
    private static final long serialVersionUID = -3024829259409103043L;

    @ApiModelProperty(value = "模板编码（小写）", required = true)
    private String code;

    @ApiModelProperty(value = "模板名称",required = true)
    private String name;

    @ApiModelProperty(value = "类型：1-系统模板 2-业务模板,默认：2")
    private Integer type = 2;

    @ApiModelProperty(value = "是否启用：0-禁用 1-启用,默认: 1")
    private Integer enabled = 1;

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

    @Transient
    @ApiModelProperty("层级列表")
    private List<TemplateLayer> layerList;

}
