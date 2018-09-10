package com.code_hq.core.domain.normalisation;

import com.code_hq.core.domain.score.Score;
import com.code_hq.core.domain.metric.Metric;

public interface NormalisationStrategy
{
    Double normalise(Metric metric, Score score);
}
