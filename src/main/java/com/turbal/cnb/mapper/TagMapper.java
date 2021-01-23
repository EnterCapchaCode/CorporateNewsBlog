/*
 * Developed by Turbal Denis
 * https://github.com/EnterCapchaCode
 */

package com.turbal.cnb.mapper;

import com.turbal.cnb.dto.TagDto;
import com.turbal.cnb.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface TagMapper {

    @Mapping(target = "id", source = "tag.id")
    @Mapping(target = "tagName", source = "tag.tagName")
    TagDto toDto(Tag tag);

    @Mapping(target = "id", source = "tagDto.id")
    @Mapping(target = "tagName", source = "tagDto.tagName")
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    Tag toEntity(TagDto tagDto);
}
