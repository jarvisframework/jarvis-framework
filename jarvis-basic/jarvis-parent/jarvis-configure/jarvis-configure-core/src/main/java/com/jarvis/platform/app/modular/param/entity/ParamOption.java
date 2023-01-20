package com.jarvis.platform.app.modular.param.entity;

import com.jarvis.framework.core.entity.AbstractLongIdEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Table;

/**
 * @author hewm
 * @date 2022/9/8
 */
@ApiModel("参数项")
@Table(name = "app_param_option")
@Getter
@Setter
@Accessors(chain = true)
@ToString
public class ParamOption extends AbstractLongIdEntity {

    @ApiModelProperty(value = "参数编码", required = true)
    private String code;

    @ApiModelProperty(value = "参数名称", required = true)
    private String name;

    @ApiModelProperty(value = "参数值")
    private String value;

    @ApiModelProperty(value = "设值方式: 0-下拉框 1-输入框", required = true)
    private Integer valueMode;

    @ApiModelProperty(value = "参数类型：1-默认参数 2-用户参数 （默认参数不能删除）")
    private Integer type;

    @ApiModelProperty(value = "下拉框JSON")
    private String json;

    @ApiModelProperty(value = "分类ID", required = true)
    private Long categoryId;

    @ApiModelProperty(value = "说明")
    private String remark;

    @ApiModelProperty(value = "系统ID", required = true)
    private Long systemId;
}
