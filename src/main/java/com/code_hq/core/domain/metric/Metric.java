package com.code_hq.core.domain.metric;

import com.code_hq.core.domain.score.value.Value;
import lombok.*;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.Size;

/**
 * A single metric against which applications can be scored.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
@Getter
public abstract class Metric
{
    @EmbeddedId
    @NonNull
    private MetricIdentity id;

    @NonNull
    @Size(min = 1, max = 100)
    private String name;

    private String description;

    @NonNull
    private Direction direction = Direction.HIGHER_IS_BEST;

    @NonNull
    private ContributionPolicy contributionPolicy = ContributionPolicy.OVERALL;

    public abstract boolean accepts(Value value);

    public void rename(String name)
    {
        this.name = name;
    }

    public enum Direction {
        HIGHER_IS_BEST, LOWER_IS_BEST
    }

    enum ContributionPolicy {
        NONE, OVERALL
    }
}
