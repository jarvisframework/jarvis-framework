package com.jarvis.platform.app.modular.type.entity;

import com.jarvis.framework.core.entity.AbstractSortLongIdEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Table;

/**
 * @Desctription: TODO
 * @Author: liqinhua
 * @Date: Created in 2022/8/29 18:30
 * @Version: 1.0
 */
@Table(name = "app_type_fonds")
@ApiModel("档案门类全宗关联")
@Getter
@Setter
@ToString
public class TypeFonds extends AbstractSortLongIdEntity {


    private static final long serialVersionUID = 6343584550038420512L;

    @ApiModelProperty("门类ID")
    private Long typeId;

    @ApiModelProperty("全宗号")
    private String fonds_code;

    @ApiModelProperty("全宗名称")
    private String fonds_name;
}
