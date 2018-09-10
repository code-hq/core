package com.code_hq.core.application.dto.score;

import lombok.EqualsAndHashCode;
import lombok.NonNull;

@lombok.Value
@EqualsAndHashCode(callSuper = true)
public class SimpleValueDto extends ValueDto
{
    private @NonNull Double value;
}
