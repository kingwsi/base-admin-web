package com.kingwsi.bs.entity.dictionary;

/**
 * description: Dictionary <br>
 * date: 2020/8/6 10:29 <br>
 * author: ws <br>
 * version: 1.0 <br>
 */

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface DictionaryConvertMapper {
    Dictionary toDictionary(DictionaryVO vo);

    DictionaryVO toDictionaryVO(DictionaryVO vo);
}
