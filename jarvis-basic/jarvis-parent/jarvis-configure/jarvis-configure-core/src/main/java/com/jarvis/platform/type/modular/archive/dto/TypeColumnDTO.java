package com.jarvis.platform.type.modular.archive.dto;

import com.jarvis.platform.app.enums.DataTypeEnum;
import com.jarvis.platform.app.modular.type.entity.ArchiveColumn;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年10月20日
 */
@Getter
@Setter
@ToString
public class TypeColumnDTO {

    private Map<String, String> columnLabels = new HashMap<>();

    List<DataTypeColumnDTO> dateColumns = new ArrayList<>();

    public static TypeColumnDTO newInstance(List<ArchiveColumn> columns) {
        final TypeColumnDTO entity = new TypeColumnDTO();
        columns.forEach(e -> {
            if (StringUtils.hasText(e.getColumnLabel())) {
                entity.columnLabels.put(e.getColumnLabel(), e.getColumnName());
            }
            if (DataTypeEnum.DATE.value().equals(e.getDataType()) ||
                DataTypeEnum.DATETIME.value().equals(e.getDataType())) {
                entity.dateColumns.add(DataTypeColumnDTO.newInstance(e.getColumnName(), e.getDataType()));
            }
        });
        return entity;
    }

}
