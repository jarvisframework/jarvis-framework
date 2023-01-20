package com.jarvis.platform.workflow.modular.process.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年7月21日
 */
@ApiModel("流程审批结果")
@Getter
@Setter
@ToString
public class CompleteTaskResultDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 6912085154917955282L;

    private boolean finished = false;

    private List<String> nextTaskIds;

    public CompleteTaskResultDTO(boolean finished) {
        this.finished = finished;
        this.nextTaskIds = new ArrayList<>();
    }

    public CompleteTaskResultDTO nextTaskId(String nextTaskId) {
        this.nextTaskIds.add(nextTaskId);
        return this;
    }

    public CompleteTaskResultDTO nextTaskId(List<String> nextTaskIds) {
        this.nextTaskIds.addAll(nextTaskIds);
        return this;
    }

    public static CompleteTaskResultDTO finished() {
        final CompleteTaskResultDTO result = new CompleteTaskResultDTO(true);
        return result;
    }

    public static CompleteTaskResultDTO unfinished() {
        final CompleteTaskResultDTO result = new CompleteTaskResultDTO(false);
        return result;
    }

}
