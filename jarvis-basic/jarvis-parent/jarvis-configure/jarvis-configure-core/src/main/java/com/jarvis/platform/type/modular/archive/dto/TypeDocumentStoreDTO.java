package com.jarvis.platform.type.modular.archive.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年9月30日
 */
@ApiModel("档案门类全文存储")
@Getter
@Setter
@ToString
public class TypeDocumentStoreDTO {

    private String location;

    private String filePath;

}
