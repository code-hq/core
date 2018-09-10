package com.code_hq.core.domain.application;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * An application against which scores can be recorded for metrics.
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor(staticName = "register")
@EqualsAndHashCode
@ToString
@Getter
public class Application
{
    @Id
    @NonNull
    @Size(min = 2, max = 100)
    private String id;

    @NonNull
    @NotNull
    @Size(min = 2, max = 100)
    private String name;

    public void rename(String name)
    {
        this.name = name;
    }
}
