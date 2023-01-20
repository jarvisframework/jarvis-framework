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
@Table(name = "app_file_format")
@Getter
@Setter
@ToString
@ApiModel("文件格式表")
public class FileFormat extends AbstractSortLongIdEntity {

    @ApiModelProperty("文件格式分类id")
    private Long categoryId;

    @ApiModelProperty("文件格式")
    private String fileFormat;

    @ApiModelProperty("全文类型：多选值，多个编码用英文逗号分隔  文书/照片/音频/视频/数据库/网页")
    private String fileType;

    @ApiModelProperty("备注")
    private String remark;
}
