package com.jarvis.platform.app.modular.type.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author hewm
 * @date 2022/8/25
 */
@Data
public class TemplateFilingUpdateDto {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty("模板编码（小写）")
    private String code;

    @ApiModelProperty("模板名称")
    private String name;

    @ApiModelProperty("类型：1-系统模板 2-业务模板")
    private Integer type = 2;

    @ApiModelProperty("是否启用：0-禁用 1-启用")
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

    @ApiModelProperty("层级列表")
    private List<TemplateLayer> layerList;
}
