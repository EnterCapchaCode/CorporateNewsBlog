/*
 * Developed by Turbal Denis
 * https://github.com/EnterCapchaCode
 */

package com.turbal.cnb.service;

import com.turbal.cnb.dto.CommentDto;
import com.turbal.cnb.dto.PostDto;

import java.util.List;

public interface CommentService {
    void deleteComment(Integer id);

    CommentDto saveComment(CommentDto commentDto, Integer postId);

    List<CommentDto> findAllCommentsByPost(PostDto postDto);
}
