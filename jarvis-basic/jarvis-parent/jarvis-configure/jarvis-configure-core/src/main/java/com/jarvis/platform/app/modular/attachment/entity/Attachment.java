package com.jarvis.platform.app.modular.attachment.entity;

import com.jarvis.framework.core.entity.AbstractLongIdEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Table;

/**
 * @Author xukaiqian
 * @Version 1.0.0 2022年09月01日
 */
@Table(name = "app_attachment")
@ApiModel("附件表")
@Getter
@Setter
@ToString
public class Attachment extends AbstractLongIdEntity {

    @ApiModelProperty("类型（元数据/报表等）")
    private String type;

    @ApiModelProperty("主表ID")
    private Long ownerId;

    @ApiModelProperty("存储位置")
    private String location;

    @ApiModelProperty("相对路径")
    private String path;

}
