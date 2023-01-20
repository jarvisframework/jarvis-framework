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
 * @version 1.0.0 2022年8月12日
 */
@Table(name = "app_file_category")
@Getter
@Setter
@ToString
@ApiModel("文件格式分类表")
public class FileCategory extends AbstractSortLongIdEntity {

    @ApiModelProperty("文件格式分类")
    private String name;

    @ApiModelProperty("备注")
    private String remark;
}
