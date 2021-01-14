/*
 * Developed by Turbal Denis
 * https://github.com/EnterCapchaCode
 */

package com.turbal.cnb.service;

import com.turbal.cnb.dto.PostDto;

import java.util.List;

public interface PostService {

    PostDto savePost(PostDto postDto);

    void deletePost(Integer id);

    PostDto findPostByTitle(String title);

    List<PostDto> findAll();
}
