package com.jarvis.platform.workflow.modular.process.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年7月21日
 */
@ApiModel("启动流程参数")
@Getter
@Setter
@ToString
public class StartProcessDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 6832993719799694497L;

    @ApiModelProperty("流程编码")
    private String processDefinitionId;

    @ApiModelProperty("设置代流程启动用户ID，如果为未设置时，则默认为当前登录用户")
    private Long userId;

    @ApiModelProperty("启动并自动提交")
    private boolean autoComplete;

    @ApiModelProperty("数据ID")
    private String dataId;

    @ApiModelProperty("流程变量")
    private Map<String, Object> variables;

    public Map<String, Object> getVariables() {
        if (null == variables) {
            return new HashMap<>();
        }
        return variables;
    }
}
