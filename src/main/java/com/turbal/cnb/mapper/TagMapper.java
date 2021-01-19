/*
 * Developed by Turbal Denis
 * https://github.com/EnterCapchaCode
 */

package com.turbal.cnb.mapper;

import com.turbal.cnb.dto.TagDto;
import com.turbal.cnb.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface TagMapper {

    @Mapping(target = "id", source = "tag.id")
    @Mapping(target = "tagName", source = "tag.tagName")
    TagDto toDto(Tag tag);

    @Mapping(target = "id", source = "tagDto.id")
    @Mapping(target = "tagName", source = "tagDto.tagName")
    Tag toEntity(TagDto tagDto);
}
