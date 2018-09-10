package com.code_hq.core.domain.score.value;

import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@DiscriminatorValue("1")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
@ToString
public class FractionalValue extends Value
{
    @NonNull
    @NotNull
    private Double numerator;

    @NonNull
    @NotNull
    private Double denominator;

    public FractionalValue(UUID scoreId, Double numerator, Double denominator)
    {
        super(scoreId);

        this.numerator = numerator;
        this.denominator = denominator;
    }
}
