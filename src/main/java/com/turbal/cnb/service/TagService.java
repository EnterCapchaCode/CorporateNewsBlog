package com.turbal.cnb.service;

import com.turbal.cnb.dto.TagDto;

import java.util.List;

public interface TagService {

    List<TagDto> findTagByRequest(String request);

    TagDto addTag(TagDto tagDto);

    void deleteTag(Integer id);
}
