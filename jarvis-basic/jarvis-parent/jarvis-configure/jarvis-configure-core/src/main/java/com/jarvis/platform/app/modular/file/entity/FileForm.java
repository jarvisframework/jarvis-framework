package com.jarvis.platform.app.modular.file.entity;

import com.jarvis.framework.core.entity.AbstractSortLongIdEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Table;

/**
 *
 * @author ronghui
 * @version 1.0.0 2022年8月15日
 */
@Table(name = "app_file_form")
@Getter
@Setter
@ToString
@ApiModel("文件格式表单配置表")
public class FileForm extends AbstractSortLongIdEntity {

    @ApiModelProperty("文件格式id")
    private Long formatId;

    @ApiModelProperty("文件格式后缀名")
    private String fileFormat;

    @ApiModelProperty("表单json")
    private String formJson;
}
