/*
 * Developed by Turbal Denis
 * https://github.com/EnterCapchaCode
 */

package com.turbal.cnb.controller;

import com.turbal.cnb.dto.PostDto;
import com.turbal.cnb.entity.Employee;
import com.turbal.cnb.mapper.EmployeeMapper;
import com.turbal.cnb.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    private final EmployeeMapper employeeMapper;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PostDto savePost(@AuthenticationPrincipal Employee currentUser, @RequestBody PostDto postDto) {
        return postService.savePost(postDto, currentUser);
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

    @GetMapping()
    public List<PostDto> findAllPosts() {
        var posts = postService.findAll();
        return posts;
    }

    @GetMapping("search/{tag}")
    public List<PostDto> findPostByTag(@PathVariable String tag) throws Exception {
        return postService.findPostsByTag(tag);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Integer id) {
        postService.deletePost(id);
        return ResponseEntity.ok().build();
    }
}
