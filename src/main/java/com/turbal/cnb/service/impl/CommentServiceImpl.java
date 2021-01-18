/*
 * Developed by Turbal Denis
 * https://github.com/EnterCapchaCode
 */

package com.turbal.cnb.service.impl;

import com.turbal.cnb.dto.CommentDto;
import com.turbal.cnb.dto.PostDto;
import com.turbal.cnb.mapper.CommentMapper;
import com.turbal.cnb.mapper.PostMapper;
import com.turbal.cnb.repository.CommentRepo;
import com.turbal.cnb.repository.PostRepo;
import com.turbal.cnb.service.CommentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class CommentServiceImpl extends BaseService implements CommentService {

    private final CommentRepo commentRepo;
    private final PostRepo postRepo;
    private final CommentMapper commentMapper;
    private final PostMapper postMapper;

    @Override
    public void deleteComment(Integer id) {
        commentRepo.deleteById(id);
        log.info("Comment with ID = {} deleted", id);
    }

    @Override
    public CommentDto saveComment(CommentDto commentDto, Integer postId) {
        var post = postRepo.findPostById(postId);
        var employee = getCurrentEmployee();
        var comment = commentMapper.toEntity(commentDto);

        comment.setEmployee(employee);
        comment.setPost(post);

        commentRepo.save(comment);
        log.info("Comment employee with login = {} saved", employee.getLogin());
        return commentDto;
    }

    @Override
    public List<CommentDto> findAllCommentsByPost(PostDto postDto) {
        var post = postMapper.toEntity(postDto);
        return commentRepo.findCommentByPost(post)
            .stream()
            .map(commentMapper::toDto)
            .collect(Collectors.toList());
    }
}
