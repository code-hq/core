package com.code_hq.core.application.dto.score;

import lombok.NonNull;

@lombok.Value
public class ScoreDto
{
    private @NonNull String metric, version;
    private ScopeDto scope = new ApplicationScopeDto();
    private @NonNull
    ValueDto value;
}
