package com.jarvis.platform.type.modular.archive.dto;

import com.jarvis.framework.search.ComposedCondition;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年10月13日
 */
@ApiModel("档案门类回车站还原参数")
@Getter
@Setter
@ToString
public class UpdateUndeletedDTO {

    private String toLocation;

    private List<Long> ids;

    private ComposedCondition<String> filter;

    private String keyword;

}
