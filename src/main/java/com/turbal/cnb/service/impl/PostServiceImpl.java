/*
 * Developed by Turbal Denis
 * https://github.com/EnterCapchaCode
 */

package com.turbal.cnb.service.impl;

import com.turbal.cnb.dto.PostDto;
import com.turbal.cnb.entity.*;
import com.turbal.cnb.mapper.PostMapper;
import com.turbal.cnb.repository.DislikeRepo;
import com.turbal.cnb.repository.LikeRepo;
import com.turbal.cnb.repository.PostRepo;
import com.turbal.cnb.repository.TagRepo;
import com.turbal.cnb.service.PostService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class PostServiceImpl extends BaseService implements PostService {

    private final PostRepo postRepo;
    private final PostMapper postMapper;
    private final LikeRepo likeRepo;
    private final DislikeRepo dislikeRepo;
    private final TagRepo tagRepo;

    @Override
    public PostDto savePost(PostDto postDto, Employee currentUser) {
        var post = postMapper.toEntity(postDto);

        post.setEmployee(currentUser);
        post.setCreationDate(LocalDate.now());
        post.setNegativeRating(0);
        post.setPositiveRating(0);

        setTagToNewPost(postDto, post);

        Post newPost = postRepo.save(post);
        log.info("Post with title = {} saved", postDto.getTitle());
        return postMapper.toDto(newPost);
    }

    private void setTagToNewPost(PostDto postDto, Post post) {
        var tagName = postDto.getTag().getTagName().toLowerCase(Locale.ROOT);
        Tag persistedTag = tagRepo.findByTagName(tagName);

        if (persistedTag != null) {
            post.setTag(persistedTag);
        } else {
            Tag newTag = new Tag();
            newTag.setTagName(tagName);
            post.setTag(tagRepo.save(newTag));
        }
    }

    @Override
    public void deletePost(Integer id) {
        postRepo.deleteById(id);
        log.info("Post with ID = {} deleted", id);
    }

    @Override
    public List<PostDto> findPostByTitleIsContaining(String title) {
        var post = postRepo.findPostByTitleIsContaining(title);
        if (post != null) {
            log.info("Got a list of posts with title = {}", title);
            return post.stream().map(postMapper::toDto).collect(Collectors.toList());
        }
        log.info("Posts not found");
        return null;
    }

    @Override
    public List<PostDto> findPostsByTag(String tag) throws Exception {
        Tag entityTag = tagRepo.findByTagName(tag.toLowerCase(Locale.ROOT));
        if (entityTag == null) {
            throw new Exception("Tag not found");
        }
        log.info("Got a list of posts with tag = {}", tag);
        return postRepo.findByTag(entityTag)
                .stream()
                .map(postMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void likePost(Integer id) {
        var post = postRepo.findPostById(id);
        var employee = getCurrentEmployee();
        var currentPositiveRating = post.getPositiveRating();
        var currentLike = likeRepo.findLikeByPostAndAndEmployee(post, employee);

        if (currentLike.isPresent()) {
            likeRepo.delete(currentLike.get());
            post.setPositiveRating(currentPositiveRating - 1);
            log.info("Like deleted from post with id = {}", post.getId());
        } else {
            likeRepo.save(createLike(post, employee));
            post.setPositiveRating(currentPositiveRating + 1);
            log.info("Like added to post with id = {}", post.getId());
        }
    }

    @Override
    public void dislikePost(Integer id) {
        var post = postRepo.findPostById(id);
        var employee = getCurrentEmployee();
        var currentNegativeRating = post.getNegativeRating();
        var currentDislike = dislikeRepo.findDislikeByPostAndAndEmployee(post, employee);

        if (currentDislike.isPresent()) {
            dislikeRepo.delete(currentDislike.get());
            post.setNegativeRating(currentNegativeRating - 1);
            log.info("Dislike deleted from post with id = {}", post.getId());
        } else {
            dislikeRepo.save(createDislike(post, employee));
            post.setNegativeRating(currentNegativeRating + 1);
            log.info("Dislike added to post with id = {}", post.getId());
        }
    }

    @Override
    public List<PostDto> findAll() {
        log.info("Got a list of all posts");
        return postRepo.findAll()
                .stream()
                .map(postMapper::toDto)
                .collect(Collectors.toList());
    }

    private Like createLike(Post post, Employee employee) {
        return Like.builder()
                .post(post)
                .employee(employee)
                .build();
    }

    private Dislike createDislike(Post post, Employee employee) {
        return Dislike.builder()
                .post(post)
                .employee(employee)
                .build();
    }
}
