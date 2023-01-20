package com.jarvis.platform.workflow.modular.workflow.entity;

import com.jarvis.framework.core.entity.AbstractLongIdEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Table;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年10月27日
 */
@Table(name = "wf_ref_version_organization")
@ApiModel("版本与组织关系")
@Getter
@Setter
@ToString
public class RefVersionOrganization extends AbstractLongIdEntity {

    /**
     *
     */
    private static final long serialVersionUID = 8825564796515937471L;

    @ApiModelProperty("版本ID")
    private Long versionId;

    @ApiModelProperty("组织ID")
    private Long organizationId;

}
