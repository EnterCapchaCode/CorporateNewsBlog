/*
 * Developed by Turbal Denis
 * https://github.com/EnterCapchaCode
 */

package com.turbal.cnb.service.impl;

import com.turbal.cnb.dto.TagDto;
import com.turbal.cnb.mapper.TagMapper;
import com.turbal.cnb.repository.TagRepo;
import com.turbal.cnb.service.TagService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepo tagRepo;
    private final TagMapper tagMapper;

    @Override
    public List<TagDto> findTagByRequest(String request) {
        log.info("Got a list of tags by request = {}", request);
        return tagRepo.findTagByTagNameIsContaining(request)
            .stream()
            .map(tagMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public TagDto addTag(TagDto tagDto) {
        log.info("Tag with name = {} saved", tagDto.getTagName());
        var tag = tagMapper.toEntity(tagDto);
        tagRepo.save(tag);
        return tagDto;
    }

    @Override
    public void deleteTag(Integer id) {
        log.info("Tag with ID = {} deleted", id);
        tagRepo.deleteById(id);
    }
}
