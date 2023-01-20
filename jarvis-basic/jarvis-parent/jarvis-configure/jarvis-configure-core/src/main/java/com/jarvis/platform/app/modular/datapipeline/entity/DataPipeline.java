package com.jarvis.platform.app.modular.datapipeline.entity;

import com.jarvis.framework.core.entity.AbstractLongIdEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Table;

@Table(name = "app_data_pipeline")
@ApiModel("数据管道主表")
@Getter
@Setter
@ToString
public class DataPipeline extends AbstractLongIdEntity {

    private static final long serialVersionUID = -8859108584543949909L;

    @ApiModelProperty("源所属系统")
    private Long sourceSystemId;

    @ApiModelProperty("源库id")
    private Long sourceLibraryId;

    @ApiModelProperty("源库编码")
    private String sourceLibraryCode;

    @ApiModelProperty("源门类id")
    private Long sourceTypeId;

    @ApiModelProperty("源门类编码")
    private String sourceTypeCode;

    @ApiModelProperty("源门类名称")
    private String sourceTypeName;

    @ApiModelProperty("目标所属系统")
    private Long targetSystemId;

    @ApiModelProperty("目标库id")
    private Long targetLibraryId;

    @ApiModelProperty("目标库编码")
    private String targetLibraryCode;

    @ApiModelProperty("目标门类表id")
    private Long targetTypeId;

    @ApiModelProperty("目标门类编码")
    private String targetTypeCode;

    @ApiModelProperty("目标门类名称")
    private String targetTypeName;

    @ApiModelProperty("数据交换方式复制/剪切")
    private String dataExchangeType;

}
