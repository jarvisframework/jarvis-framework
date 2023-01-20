package com.jarvis.platform.app.modular.group.entity;

import com.jarvis.framework.core.entity.AbstractSortLongIdEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Table;

/**
 * 档案组
 *
 * @author qiucs
 * @version 1.0.0 2022年5月20日
 */
@Table(name = "app_archive_group")
@ApiModel("档案组")
@Getter
@Setter
@ToString
public class ArchiveGroup extends AbstractSortLongIdEntity {

    /**
     *
     */
    private static final long serialVersionUID = 8493414058820600096L;

    @ApiModelProperty("组编码")
    private String code;

    @ApiModelProperty("组名称")
    private String name;

    @ApiModelProperty("单位ID")
    private Long unitId;

    @ApiModelProperty("总部ID")
    private Long hqId;

    @ApiModelProperty("说明")
    private String remark;

}
