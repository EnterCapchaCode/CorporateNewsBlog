/*
 * Developed by Turbal Denis
 * https://github.com/EnterCapchaCode
 */

package com.turbal.cnb.mapper;

import com.turbal.cnb.dto.CommentDto;
import com.turbal.cnb.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {EmployeeMapper.class, PostMapper.class})
public interface CommentMapper {

    @Mapping(target = "id", source = "comment.id")
    @Mapping(target = "commentText", source = "comment.commentText")
    @Mapping(target = "employeeDto", source = "comment.employee")
    @Mapping(target = "postDto", source = "comment.post")
    CommentDto toDto(Comment comment);

    @Mapping(target = "commentText", source = "commentDto.commentText")
    @Mapping(target = "employee", source = "commentDto.employeeDto")
    @Mapping(target = "post", source = "commentDto.employeeDto")
    Comment toEntity(CommentDto commentDto);
}
