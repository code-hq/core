package com.code_hq.core.domain.score.value;

import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.UUID;

@Entity
@DiscriminatorValue("2")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
@ToString
public class SimpleValue extends Value
{
    private @NonNull Double value;

    public SimpleValue(UUID scoreId, Double value)
    {
        super(scoreId);

        this.value = value;
    }
}
