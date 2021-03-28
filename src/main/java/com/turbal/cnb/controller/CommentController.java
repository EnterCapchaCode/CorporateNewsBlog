/*
 * Developed by Turbal Denis
 * https://github.com/EnterCapchaCode
 */

package com.turbal.cnb.controller;

import com.turbal.cnb.dto.CommentDto;
import com.turbal.cnb.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "{post_id}")
    public CommentDto saveComment(@PathVariable Integer post_id, @RequestBody CommentDto commentDto) {
        return commentService.saveComment(commentDto, post_id);
    }

    @GetMapping("{id}")
    public List<CommentDto> findAllCommentsByPost(@PathVariable Integer id) {
        return commentService.findAllCommentsInPost(id);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok().build();
    }
}
