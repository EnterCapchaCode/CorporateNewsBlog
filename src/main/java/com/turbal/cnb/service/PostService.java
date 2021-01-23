/*
 * Developed by Turbal Denis
 * https://github.com/EnterCapchaCode
 */

package com.turbal.cnb.service;

import com.turbal.cnb.dto.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {

    PostDto savePost(PostDto postDto);

    void deletePost(Integer id);

    List<PostDto> findPostByTitleIsContaining(String title);

    List<PostDto> findAll(Pageable pageable);

    List<PostDto> findPostsByTag(String tag);

    void likePost(Integer id);

    void dislikePost(Integer id);
}
