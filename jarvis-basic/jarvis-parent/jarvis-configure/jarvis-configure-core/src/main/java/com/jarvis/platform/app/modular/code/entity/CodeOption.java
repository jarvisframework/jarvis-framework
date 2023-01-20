package com.jarvis.platform.app.modular.code.entity;

import com.jarvis.framework.core.entity.AbstractSortLongIdEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Table;

/**
 * @author qiucs
 * @version 1.0.0 2022年4月29日
 */
@Table(name = "app_code_option")
@ApiModel("数据编码选项")
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class CodeOption extends AbstractSortLongIdEntity {

    /**
     *
     */
    private static final long serialVersionUID = 3086987902908530980L;

    @ApiModelProperty("分类编码")
    private String categoryCode;

    @ApiModelProperty("是否隐藏：0-否 1-是")
    private Integer hidden = 0;

    @ApiModelProperty("编码")
    private String code;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("备注")
    private String remark;

}
