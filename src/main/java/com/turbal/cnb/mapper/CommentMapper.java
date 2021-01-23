/*
 * Developed by Turbal Denis
 * https://github.com/EnterCapchaCode
 */

package com.turbal.cnb.mapper;

import com.turbal.cnb.dto.CommentDto;
import com.turbal.cnb.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR, uses = {EmployeeMapper.class, PostMapper.class})
public interface CommentMapper {

    @Mapping(target = "id", source = "comment.id")
    @Mapping(target = "commentText", source = "comment.commentText")
    @Mapping(target = "employeeDto", source = "comment.employee")
    @Mapping(target = "postDto", source = "comment.post")
    CommentDto toDto(Comment comment);

    @Mapping(target = "commentText", source = "commentDto.commentText")
    @Mapping(target = "employee", source = "commentDto.employeeDto")
    @Mapping(target = "post", source = "commentDto.postDto")
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    Comment toEntity(CommentDto commentDto);
}
