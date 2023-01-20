package com.jarvis.platform.app.modular.type.entity;

import com.jarvis.framework.core.entity.AbstractLongIdEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Table;

/**
 * @Desctription: TODO
 * @Author: liqinhua
 * @Date: Created in 2022/8/29 18:22
 * @Version: 1.0
 */
@Table(name = "app_type_retention_period")
@ApiModel("档案门类保管期限关联")
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class TypeRetentionPeriod extends AbstractLongIdEntity {

    private static final long serialVersionUID = 768153573815408725L;

    @ApiModelProperty("门类ID")
    private Long typeId;

    @ApiModelProperty("保管期限编码")
    private String retentionPeriodCode;
}
