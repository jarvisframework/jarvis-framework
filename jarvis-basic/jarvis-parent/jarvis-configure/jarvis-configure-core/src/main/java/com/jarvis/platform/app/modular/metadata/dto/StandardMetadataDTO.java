package com.jarvis.platform.app.modular.metadata.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @Author xukaiqian
 * @Version 1.0.0 2022年09月09日
 */

@Getter
@Setter
@ToString
public class StandardMetadataDTO extends StandardMetadata {

    private List<StandardMetadata> children;

}
