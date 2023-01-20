package com.jarvis.platform.app.modular.library.entity;

import com.jarvis.framework.core.entity.AbstractSortLongIdEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 库类型
 *
 * @author wangdh
 * @date 2022-08-22 13:40
 */
@Data
@ToString
@Table(name = "app_archive_library")
@ApiModel("库类型")
@EqualsAndHashCode(callSuper = true)
public class ArchiveLibrary extends AbstractSortLongIdEntity {

    private static final long serialVersionUID = -426248053140409460L;

    @ApiModelProperty(value = "库级别 1-系统 2-业务", required = true)
    private Integer libraryLevel;

    @ApiModelProperty(value = "所属系统id", required = true)
    private Long systemId;

    @Transient
    @ApiModelProperty("所属系统名称")
    private String systemName;

    @ApiModelProperty(value = "库编码", required = true)
    private String libraryCode;

    @ApiModelProperty(value = "库名称", required = true)
    private String name;

    @ApiModelProperty(value = "是否建立档案门类", required = true)
    private Boolean createArchiveAble;

    @ApiModelProperty("备注")
    private String remark;
}
