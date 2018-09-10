package com.code_hq.core.application.dto.application;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationDto
{
    private String id;

    @Size(min = 2, max = 100)
    private String name;
}
