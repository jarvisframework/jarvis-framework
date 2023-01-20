package com.jarvis.platform.app.modular.metadata.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author xukaiqian
 * @Version 1.0.0 2022年09月01日
 */
@ApiModel("元数据标准实体DTO")
@Getter
@Setter
@ToString
public class MetaDataDTO {

    @ApiModelProperty("元数据标准")
    private ArchiveStandard archiveStandard;

    @ApiModelProperty("元数据实体")
    private List<StandardEntity> standardEntityList;

    @ApiModelProperty("标准附件")
    private List<Attachment> attachmentList;


}
