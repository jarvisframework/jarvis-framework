package com.jarvis.platform.workflow.modular.workflow.entity;

import com.jarvis.framework.constant.CommonConstant;
import com.jarvis.framework.core.entity.AbstractSortLongIdEntity;
import com.jarvis.platform.workflow.modular.workflow.constant.WorkflowConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.StringUtils;

import javax.persistence.Table;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年7月20日
 */
@Table(name = "wf_workflow_version")
@ApiModel("流程版本")
@Getter
@Setter
@ToString
public class WorkflowVersion extends AbstractSortLongIdEntity {

    /**
     *
     */
    private static final long serialVersionUID = -2035924649930489246L;

    @ApiModelProperty("系统ID")
    private Long systemId;

    @ApiModelProperty("流程ID")
    private Long workflowId;

    @ApiModelProperty("模型ID")
    private String modelId;

    @ApiModelProperty("版本号")
    private String version;

    @ApiModelProperty("是否为主版本：0-否 1-是")
    private Integer major = CommonConstant.NO;

    @ApiModelProperty("状态：0-未定义 1-未激活 2-已挂起 9-已激活")
    private Integer status = WorkflowConstant.STATUS_UNDEFINED;

    @ApiModelProperty("流程定义id")
    private String processDefinitionId;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("单位ID")
    private Long unitId;

    @ApiModelProperty("总部ID")
    private Long hqId;

    public Integer getStatus() {
        if (!StringUtils.hasText(modelId)) {
            return WorkflowConstant.STATUS_UNDEFINED;
        }
        if (!StringUtils.hasText(processDefinitionId)) {
            return WorkflowConstant.STATUS_UNACTIVED;
        }
        return status;
    }

}
