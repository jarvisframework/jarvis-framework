package com.jarvis.platform.workflow.modular.workflow.entity;

import com.jarvis.framework.core.entity.AbstractSortLongIdEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Table;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年7月20日
 */
@Table(name = "wf_workflow_category")
@ApiModel("流程分类")
@Getter
@Setter
@ToString
public class WorkflowCategory extends AbstractSortLongIdEntity {

    /**
     *
     */
    private static final long serialVersionUID = 228335941629871511L;

    @ApiModelProperty("系统ID")
    private Long systemId;

    @ApiModelProperty("父ID")
    private Long parentId;

    @ApiModelProperty("分类名称")
    private String name;

    @ApiModelProperty("备注")
    private String remark;

}
