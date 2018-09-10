package com.code_hq.core.application.dto.metric;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
@JsonTypeName("simple")
public class SimpleMetricDto extends MetricDto
{
    public SimpleMetricDto(String id, String name)
    {
        super(id, name);
    }
}
