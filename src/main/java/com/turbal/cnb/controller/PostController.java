/*
 * Developed by Turbal Denis
 * https://github.com/EnterCapchaCode
 */

package com.turbal.cnb.controller;

import com.turbal.cnb.dto.PostDto;
import com.turbal.cnb.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public PostDto savePost(@RequestBody PostDto postDto) {
        return postService.savePost(postDto);
    }
}
