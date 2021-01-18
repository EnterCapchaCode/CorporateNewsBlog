/*
 * Developed by Turbal Denis
 * https://github.com/EnterCapchaCode
 */

package com.turbal.cnb.controller;

import com.turbal.cnb.dto.PostDto;
import com.turbal.cnb.service.PostService;
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
@RequestMapping("/posts")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public PostDto savePost(@RequestBody PostDto postDto) {
        return postService.savePost(postDto);
    }

    @GetMapping
    public List<PostDto> findAllPosts() {
        return postService.findAll();
    }

    @GetMapping("{tag}")
    private List<PostDto> findPostByTag(@PathVariable String tag) {
        return postService.findPostsByTag(tag);
    }

    @DeleteMapping("{id}")
    private ResponseEntity<Void> deletePost(@PathVariable Integer id) {
        postService.deletePost(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("search/{title}")
    private List<PostDto> findPostByTitle(@PathVariable String title) {
        return postService.findPostByTitleIsContaining(title);
    }
}
