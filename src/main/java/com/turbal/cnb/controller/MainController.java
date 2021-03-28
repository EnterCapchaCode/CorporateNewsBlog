package com.turbal.cnb.controller;

import com.turbal.cnb.dto.InitialFrontendDataDto;
import com.turbal.cnb.dto.PageDto;
import com.turbal.cnb.entity.Employee;
import com.turbal.cnb.entity.Post;
import com.turbal.cnb.mapper.EmployeeMapper;
import com.turbal.cnb.mapper.PostMapper;
import com.turbal.cnb.repository.PostRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller class for load front end application
 * <p/>
 * Created on 23/01/2021.
 * <p/>
 *
 * @author generatorr
 */

@Controller
public class MainController {

    @Value("${spring.profiles.active}")
    private String profile;

    private final PostRepo postRepo;
    private final PostMapper postMapper;
    private final EmployeeMapper employeeMapper;

    public MainController(PostRepo postRepo, PostMapper postMapper, EmployeeMapper employeeMapper) {
        this.postRepo = postRepo;
        this.postMapper = postMapper;
        this.employeeMapper = employeeMapper;
    }

    @GetMapping
    @RequestMapping("/")
    public String init(
        Model model,
        @AuthenticationPrincipal Employee user,
        @PageableDefault Pageable pageable
    ) {

        Page<Post> messages = postRepo.findAll(pageable);
        Pageable pageInfo = messages.getPageable();
        InitialFrontendDataDto initialFrontendDataDTO = new InitialFrontendDataDto();
        initialFrontendDataDTO.setMessages(postMapper.convertToList(messages));
        initialFrontendDataDTO.setProfile(employeeMapper.toDto(user));
        initialFrontendDataDTO.setPage(
            new PageDto(messages.getTotalPages(), (int) messages.getTotalElements(), pageInfo.getPageNumber(), pageInfo.getPageSize())
        );
        model.addAttribute("frontendData", initialFrontendDataDTO);
        model.addAttribute("isDevMode", "dev".equals(profile));

        return "index";
    }
}
