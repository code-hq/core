package com.code_hq.core.application.dto.score;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true)
@JsonSubTypes({
    @JsonSubTypes.Type(value = ApplicationScopeDto.class, name = "application"),
    @JsonSubTypes.Type(value = PackageScopeDto.class, name = "package"),
})
abstract class ScopeDto
{
}
