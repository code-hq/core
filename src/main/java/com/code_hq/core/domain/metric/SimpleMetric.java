package com.code_hq.core.domain.metric;

import com.code_hq.core.domain.normalisation.NormalisationConfig;
import com.code_hq.core.domain.score.value.SimpleValue;
import com.code_hq.core.domain.score.value.Value;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.Assert;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "metric_simple")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
@ToString
public class SimpleMetric extends Metric
{
//    private NormalisationConfig normalisationConfig;

    private SimpleMetric(
        MetricIdentity id,
        String name,
        String description,
        Direction direction,
        ContributionPolicy contributionPolicy,
        NormalisationConfig normalisationConfig
    ) {
        super(id, name, description, direction, contributionPolicy);

        if (contributionPolicy == ContributionPolicy.OVERALL) {
            Assert.notNull(
                normalisationConfig,
                "A normalisation config is required for simple metrics which contribute to overall scores."
            );
        }

//        this.normalisationConfig = normalisationConfig;
    }

    private SimpleMetric(MetricIdentity id, String name)
    {
        this(id, name, null, Direction.HIGHER_IS_BEST, ContributionPolicy.NONE, null);
    }

    public static SimpleMetric define(MetricIdentity id, String name)
    {
        return new SimpleMetric(id, name);
    }

    @Override
    public boolean accepts(Value value)
    {
        return value instanceof SimpleValue;
    }
}
