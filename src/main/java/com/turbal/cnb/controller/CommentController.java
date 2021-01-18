/*
 * Developed by Turbal Denis
 * https://github.com/EnterCapchaCode
 */

package com.turbal.cnb.controller;

import com.turbal.cnb.dto.CommentDto;
import com.turbal.cnb.dto.PostDto;
import com.turbal.cnb.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/comments")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping(value = "/{employee_id}")
    public CommentDto saveComment(@PathVariable Integer employee_id, @RequestBody CommentDto commentDto) {
        return commentService.saveComment(commentDto, employee_id);
    }

    @GetMapping()
    public List<CommentDto> findAllCommentsByPost(@RequestBody PostDto postDto) {
        return commentService.findAllCommentsByPost(postDto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer id){
        commentService.deleteComment(id);
        return ResponseEntity.ok().build();
    }
}
