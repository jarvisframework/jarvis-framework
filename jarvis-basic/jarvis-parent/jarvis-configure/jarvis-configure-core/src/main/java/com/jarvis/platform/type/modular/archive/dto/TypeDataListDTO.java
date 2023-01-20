package com.jarvis.platform.type.modular.archive.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年10月21日
 */
@Getter
@Setter
@ToString
public class TypeDataListDTO {

    private Long tableId;

    private List<TypeData> typeDatas;

    public static TypeDataListDTO newInstance(Long tableId) {
        final TypeDataListDTO entity = new TypeDataListDTO();
        entity.setTableId(tableId);
        return entity;
    }

    public TypeDataListDTO typeDatas(List<TypeData> typeDatas) {
        setTypeDatas(typeDatas);
        return this;
    }

    public TypeDataListDTO addTypeData(TypeData typeData) {
        if (null == typeDatas) {
            typeDatas = new ArrayList<>();
        }
        typeDatas.add(typeData);
        return this;
    }

}
