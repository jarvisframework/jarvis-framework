package com.jarvis.platform.app.modular.words.entity;

import com.jarvis.framework.core.entity.AbstractSortLongIdEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Table;


@Table(name = "app_words_store")
@Getter
@Setter
@ToString
@ApiModel("词库表")
public class WordsStore extends AbstractSortLongIdEntity {

    @ApiModelProperty("库类型：0-同义词，1-近义词，2-同音词，3-形近词")
    private Integer storeType;

    @ApiModelProperty("词组")
    private String words;

    @ApiModelProperty("登记人")
    private String operator;
}
