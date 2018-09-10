package com.code_hq.core.domain.score.value;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Inheritance
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.INTEGER)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
public abstract class Value
{
    @Id
    @NonNull
    private UUID scoreId;
}
