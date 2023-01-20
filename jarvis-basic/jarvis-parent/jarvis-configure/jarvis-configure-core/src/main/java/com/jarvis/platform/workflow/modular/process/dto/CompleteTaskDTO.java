package com.jarvis.platform.workflow.modular.process.dto;

import com.jarvis.platform.workflow.modular.process.constant.ProcessConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年7月21日
 */
@ApiModel("流程审批参数")
@Getter
@Setter
@ToString
public class CompleteTaskDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 8843781884334244587L;

    @ApiModelProperty(value = "任务ID", required = true)
    private String taskId;

    @ApiModelProperty("设置流程审批用户ID，如果为未设置时，则默认为当前登录用户")
    private Long userId;

    @ApiModelProperty("下一个节点审批，key: 节点id, value：审批人集合")
    private Map<String, List<String>> assigneeMap = new HashMap<>();

    @ApiModelProperty("同意与否")
    private boolean agreement = true;

    @ApiModelProperty("审批意见")
    private String message;

    @ApiModelProperty("流程变量")
    private Map<String, Object> variables;

    public String getMessage() {
        final StringBuilder sb = new StringBuilder();
        if (agreement) {
            sb.append("同意");
        } else {
            sb.append("不同意");
        }
        if (null == message || !StringUtils.hasText(message)) {
            return sb.toString();
        }
        return sb.append("，").append(message).toString();
    }

    public Map<String, Object> getVariables() {
        if (null == variables) {
            return new HashMap<>();
        }
        return variables;
    }

    /**
     * 设置下一节点审批人
     *
     * @param assignees 审批人
     */
    public void nextAssignee(List<String> assignees) {
        assigneeMap.put(ProcessConstant.NEXT_NODE_ID, assignees);
    }

    /**
     * 设置指定节点审批人
     *
     * @param assignees 审批人
     */
    public void nextAssignee(String key, List<String> assignees) {
        assigneeMap.put(key, assignees);
    }

}
