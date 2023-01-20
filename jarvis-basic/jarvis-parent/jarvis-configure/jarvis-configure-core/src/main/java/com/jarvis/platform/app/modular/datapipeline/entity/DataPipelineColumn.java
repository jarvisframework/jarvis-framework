package com.jarvis.platform.app.modular.datapipeline.entity;

import com.jarvis.framework.core.entity.AbstractLongIdEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Table;

/**
 * Description
 * @author hexiaochuang
 * @company
 * @version 1.0  2022年8月25日 下午7:09:26
 */

@Table(name = "app_data_pipeline_column")
@ApiModel("数据管道字段关联配置表")
@Getter
@Setter
@ToString
public class DataPipelineColumn extends AbstractLongIdEntity {

    private static final long serialVersionUID = -8859108584543949909L;

    @ApiModelProperty("管道表id")
    private Long dataPipelineId;

    @ApiModelProperty("层级")
    private String layerCode;

    @ApiModelProperty("源门类表id")
    private Long sourceTableId;

    @ApiModelProperty("源字段id")
    private Long sourceColumnId;

    @ApiModelProperty("目标门类表id")
    private Long targetTableId;

    @ApiModelProperty("目标字段id")
    private Long targetColumnId;


}
