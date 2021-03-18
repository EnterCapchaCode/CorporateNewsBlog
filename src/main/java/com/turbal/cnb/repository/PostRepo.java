/*
 * Developed by Turbal Denis
 * https://github.com/EnterCapchaCode
 */

package com.turbal.cnb.repository;

import com.turbal.cnb.entity.Post;
import com.turbal.cnb.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Integer> {

    Post findPostByTitle(String title);

    Page<Post> findAll(Pageable pageable);

    Post findPostById(Integer id);

    List<Post> findByTag(Tag tag);

    List<Post> findPostByTitleIsContaining(String title);
}
