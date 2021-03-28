package com.turbal.cnb.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Initial data needed to fill FE application;
 * <p/>
 * Created on 23/01/2021.
 * <p/>
 *
 * @author generatorr
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class InitialFrontendDataDto implements Serializable
{
    private EmployeeDto profile;
    private List<PostDto> messages;
    private PageDto page;
}
