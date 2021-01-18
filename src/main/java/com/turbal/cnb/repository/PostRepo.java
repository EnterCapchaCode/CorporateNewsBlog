/*
 * Developed by Turbal Denis
 * https://github.com/EnterCapchaCode
 */

package com.turbal.cnb.repository;

import com.turbal.cnb.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Integer> {

    Post findPostByTitle(String title);

    @Query("select p"
        + " from Post p"
        + " join PostTag pt on p.id = pt.post"
        + " join pt.tag t"
        + " where t.tag_name = LOWER(:tag)")
    List<Post> findPostsByTag(String tag);

    List<Post> findPostByTitleIsContaining(String title);
}
