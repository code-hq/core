package com.code_hq.core.domain.metric;

import com.code_hq.core.domain.score.value.FractionalValue;
import com.code_hq.core.domain.score.value.Value;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "metric_fraction")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
@ToString
public class FractionalMetric extends Metric
{
    private FractionalMetric(MetricIdentity id, String name)
    {
        super(id, name);
    }

    public static FractionalMetric define(MetricIdentity id, String name)
    {
        return new FractionalMetric(id, name);
    }

    @Override
    public boolean accepts(Value value)
    {
        return value instanceof FractionalValue;
    }
}
