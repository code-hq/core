package com.code_hq.core.domain.metric;

import lombok.*;
import lombok.experimental.NonFinal;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Embeddable
@Value
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class MetricIdentity implements Serializable
{
    @NotNull
    @NonNull
    @NonFinal
    @Size(min = 1, max = 100)
    private String id;
}
