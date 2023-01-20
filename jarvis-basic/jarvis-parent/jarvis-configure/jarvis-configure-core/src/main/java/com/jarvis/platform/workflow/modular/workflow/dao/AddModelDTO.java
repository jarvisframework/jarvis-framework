package com.jarvis.platform.workflow.modular.workflow.dao;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年7月22日
 */
@ApiModel("添加流程模型")
@Getter
@Setter
@ToString
public class AddModelDTO {

    private String category;

    private String name;

    private String code;

    private Integer version;

    private String remark;

    private String xml;

}
