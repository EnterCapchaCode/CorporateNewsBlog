/*
 * Developed by Turbal Denis
 * https://github.com/EnterCapchaCode
 */

package com.turbal.cnb.service;

import com.turbal.cnb.dto.PostDto;
import com.turbal.cnb.entity.Post;

import java.util.List;

public interface PostService {

    PostDto savePost(PostDto postDto);

    void deletePost(Integer id);

    List<PostDto> findPostByTitleIsContaining(String title);

    List<PostDto> findAll();

    List<PostDto> findPostsByTag(String tag);
}
