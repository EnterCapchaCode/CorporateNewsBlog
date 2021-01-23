/*
 * Developed by Turbal Denis
 * https://github.com/EnterCapchaCode
 */

package com.turbal.cnb.controller;

import com.turbal.cnb.dto.PostDto;
import com.turbal.cnb.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    @PostMapping("/like/{id}")
    public ResponseEntity<Void> likePost(@PathVariable Integer id) {
        postService.likePost(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/dislike/{id}")
    private ResponseEntity<Void> dislikePost(@PathVariable Integer id) {
        postService.dislikePost(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{pageable}")
    public Page<PostDto> findAllPosts(@PathVariable
                                      @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        var posts = postService.findAll(pageable);
        return new PageImpl<>(posts);
    }

    @GetMapping("{tag}")
    public List<PostDto> findPostByTag(@PathVariable String tag) {
        return postService.findPostsByTag(tag);
    }

    @GetMapping("search/{title}")
    public List<PostDto> findPostByTitle(@PathVariable String title) {
        return postService.findPostByTitleIsContaining(title);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Integer id) {
        postService.deletePost(id);
        return ResponseEntity.ok().build();
    }
}
