/*
 * Developed by Turbal Denis
 * https://github.com/EnterCapchaCode
 */

package com.turbal.cnb.mapper;

import com.turbal.cnb.dto.PostDto;
import com.turbal.cnb.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = EmployeeMapper.class)
public interface PostMapper {

    @Mapping(target = "id", source = "post.id")
    @Mapping(target = "title", source = "post.title")
    @Mapping(target = "description", source = "post.description")
    @Mapping(target = "text", source = "post.text")
    @Mapping(target = "creationDate", source = "post.creationDate")
    @Mapping(target = "modificationDate", source = "post.modificationDate")
    @Mapping(target = "negativeRating", source = "post.negativeRating")
    @Mapping(target = "positiveRating", source = "post.positiveRating")
    @Mapping(target = "employeeDto", source = "post.employee")
    PostDto toDto(Post post);

    @Mapping(target = "title", source = "postDto.title")
    @Mapping(target = "description", source = "postDto.description")
    @Mapping(target = "text", source = "postDto.text")
    @Mapping(target = "creationDate", source = "postDto.creationDate")
    @Mapping(target = "modificationDate", source = "postDto.modificationDate")
    @Mapping(target = "negativeRating", source = "postDto.negativeRating")
    @Mapping(target = "positiveRating", source = "postDto.positiveRating")
    @Mapping(target = "employee", source = "postDto.employeeDto")
    Post toEntity(PostDto postDto);
}
