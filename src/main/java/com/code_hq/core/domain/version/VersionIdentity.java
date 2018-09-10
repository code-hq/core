package com.code_hq.core.domain.version;

import lombok.*;
import lombok.experimental.NonFinal;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Embeddable
@Value
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class VersionIdentity implements Serializable
{
    @NonNull
    @NotNull
    @NonFinal
    @Size(min = 2, max = 100)
    private String applicationId;

    @NonNull
    @NotNull
    @NonFinal
    @Size(min = 1, max = 50)
    @Column(name = "version_id")
    private String version;

}
