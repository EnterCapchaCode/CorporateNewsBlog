/*
 * Developed by Turbal Denis
 * https://github.com/EnterCapchaCode
 */

package com.turbal.cnb.service;

import com.turbal.cnb.dto.PostDto;
import com.turbal.cnb.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {

    PostDto savePost(PostDto postDto, Employee currentUser);

    void deletePost(Integer id);

    List<PostDto> findPostByTitleIsContaining(String title);

    List<PostDto> findAll();

    List<PostDto> findPostsByTag(String tag) throws Exception;

    void likePost(Integer id);

    void dislikePost(Integer id);
}
