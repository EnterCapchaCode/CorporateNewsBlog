package com.turbal.cnb.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO for {@link org.springframework.data.domain.Pageable}
 * <p/>
 * Created on 23/01/2021.
 * <p/>
 *
 * @author generatorr
 */

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PageDto
{

    private int totalPages;
    private int totalDocs;
    private int currentPage;
    private int pageSize;
}
