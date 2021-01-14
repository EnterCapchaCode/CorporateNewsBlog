/*
 * Developed by Turbal Denis
 * https://github.com/EnterCapchaCode
 */

package com.turbal.cnb.service.impl;

import com.turbal.cnb.dto.PostDto;
import com.turbal.cnb.mapper.PostMapper;
import com.turbal.cnb.repository.PostRepo;
import com.turbal.cnb.service.PostService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepo postRepo;
    private final PostMapper postMapper;

    @Override
    public PostDto savePost(PostDto postDto) {
        var post = postMapper.toEntity(postDto);
        postRepo.save(post);

        log.info("Post with title = {} saved", postDto.getTitle());
        return postDto;
    }

    @Override
    public void deletePost(Integer id) {
        postRepo.deleteById(id);
        log.info("Post with ID = {} deleted", id);
    }

    @Override
    public PostDto findPostByTitle(String title) {
        var post = postRepo.findPostByTitle(title);
        if(post != null){
            log.info("Post with title = {} found", post.getTitle());
            return postMapper.toDto(post);
        }
        log.info("Post not found");
        return null;
    }

    @Override
    public List<PostDto> findAll() {
        log.info("Got a list of all posts");
        return postRepo.findAll()
            .stream()
            .map(postMapper::toDto)
            .collect(Collectors.toList());
    }
}
