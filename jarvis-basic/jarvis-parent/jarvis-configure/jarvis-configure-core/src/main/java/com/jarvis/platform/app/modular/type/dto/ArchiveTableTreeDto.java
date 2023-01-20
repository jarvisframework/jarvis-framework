package com.jarvis.platform.app.modular.type.dto;

import lombok.Data;

/**
 * @Desctription: TODO
 * @Author: liqinhua
 * @Date: Created in 2022/9/5 19:59
 * @Version: 1.0
 */

@Data
public class ArchiveTableTreeDto {

    private Long systemId;

    private Long libraryId;

    private String libraryCode;

    private String libraryName;

    private Long typeId;

    private String typeName;

    private Long tableId;

    private String tableName;
}
