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
@ApiModel("参数分类")
@Table(name = "app_param_category")
@Getter
@Setter
@Accessors(chain = true)
@ToString
public class ParamCategory extends AbstractLongIdEntity {

    @ApiModelProperty(value = "分类名称", required = true)
    private String name;

    @ApiModelProperty(value = "父级ID", required = true)
    private Long parentId;

    @ApiModelProperty(value = "系统ID", required = true)
    private Long systemId;
}
