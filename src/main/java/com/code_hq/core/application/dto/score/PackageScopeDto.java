package com.code_hq.core.application.dto.score;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@lombok.Value
@EqualsAndHashCode(callSuper = true)
class PackageScopeDto extends ScopeDto
{
    @JsonProperty("package")
    private @NonNull String packageName;
}
