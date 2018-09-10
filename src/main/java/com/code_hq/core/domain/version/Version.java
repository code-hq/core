package com.code_hq.core.domain.version;

import lombok.*;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(staticName = "define")
@EqualsAndHashCode
@ToString
@Getter
public class Version
{
    @EmbeddedId
    private VersionIdentity id;

    @NonNull
    private LocalDateTime definedAt;

    public static Version define(VersionIdentity id)
    {
        return define(id, LocalDateTime.now());
    }
}
