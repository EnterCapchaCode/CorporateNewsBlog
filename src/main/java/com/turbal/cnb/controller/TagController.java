/*
 * Developed by Turbal Denis
 * https://github.com/EnterCapchaCode
 */

package com.turbal.cnb.controller;

import com.turbal.cnb.dto.TagDto;
import com.turbal.cnb.service.TagService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tags")
@AllArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping("{request}")
    public List<TagDto> findAllTags(@PathVariable String request) {
        return tagService.findTagByRequest(request);
    }

    @PostMapping
    public TagDto addTag(@RequestBody TagDto tagDto) {
        return tagService.addTag(tagDto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Integer id) {
        tagService.deleteTag(id);
        return ResponseEntity.ok().build();
    }
}
