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
@Table(name = "app_file_column")
@Getter
@Setter
@ToString
@ApiModel("文件属性配置表")
public class FileColumn extends AbstractSortLongIdEntity {

    @ApiModelProperty("文件格式id")
    private Long formatId;

    @ApiModelProperty("文件格式后缀名")
    private String fileFormat;

    @ApiModelProperty("文件属性")
    private String fileAttribute;

    @ApiModelProperty("文件属性名称")
    private String fileAttributeName;

    @ApiModelProperty("字段名称")
    private String columnName;

    @ApiModelProperty("数据类型")
    private String dataType;

    @ApiModelProperty("长度")
    private Integer length;

    @ApiModelProperty("小数位")
    private Integer precision;
}
