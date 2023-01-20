package com.jarvis.platform.app.modular.library.entity;

import com.jarvis.framework.core.entity.AbstractLongIdEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 库关系
 *
 * @author wangdh
 * @date 2022-08-22 13:40
 */
@Data
@ToString
@Table(name = "app_archive_library_relation")
@ApiModel("库关系")
@EqualsAndHashCode(callSuper = true)
public class ArchiveLibraryRelation extends AbstractLongIdEntity {

    private static final long serialVersionUID = -1425877960734460821L;

    @ApiModelProperty(value = "当前库id", required = true)
    private Long libraryId;

    @Transient
    @ApiModelProperty("当前库编码")
    private String libraryCode;

    @Transient
    @ApiModelProperty("当前库名称")
    private String libraryName;

    @ApiModelProperty(value = "关联库id", required = true)
    private Long relationLibraryId;

    @Transient
    @ApiModelProperty("关联库编码")
    private String relationLibraryCode;

    @Transient
    @ApiModelProperty("关联库名称")
    private String relationLibraryName;

    @ApiModelProperty(value = "库关系：physical-物理;logic-逻辑", required = true)
    private String relationType;

    @ApiModelProperty(value = "数据交换方式：cut-剪切;copy-复制", required = true)
    private String dataExchangeType;

    @ApiModelProperty("备注")
    private String remark;
}
