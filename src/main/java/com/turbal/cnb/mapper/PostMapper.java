/*
 * Developed by Turbal Denis
 * https://github.com/EnterCapchaCode
 */

package com.turbal.cnb.mapper;

import com.turbal.cnb.dto.PostDto;
import com.turbal.cnb.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR, uses = EmployeeMapper.class)
public interface PostMapper {

    @Mapping(target = "id", source = "post.id")
    @Mapping(target = "title", source = "post.title")
    @Mapping(target = "description", source = "post.description")
    @Mapping(target = "text", source = "post.text")
    @Mapping(target = "creationDate", source = "post.creationDate")
    @Mapping(target = "modificationDate", source = "post.modificationDate")
    @Mapping(target = "negativeRating", source = "post.negativeRating")
    @Mapping(target = "positiveRating", source = "post.positiveRating")
    @Mapping(target = "author", source = "post.employee")
    PostDto toDto(Post post);

    List<PostDto> convertToList(Page<Post> posts);

    @Mapping(target = "title", source = "postDto.title")
    @Mapping(target = "description", source = "postDto.description")
    @Mapping(target = "text", source = "postDto.text")
    @Mapping(target = "creationDate", source = "postDto.creationDate")
    @Mapping(target = "modificationDate", source = "postDto.modificationDate")
    @Mapping(target = "negativeRating", source = "postDto.negativeRating")
    @Mapping(target = "positiveRating", source = "postDto.positiveRating")
    @Mapping(target = "employee", source = "postDto.author")
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    Post toEntity(PostDto postDto);
}
