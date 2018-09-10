package com.code_hq.core.application.query.model.leaderboard;

import com.code_hq.core.domain.metric.Metric;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Score {
    private @NonNull String applicationId;
    private @NonNull String applicationName;
    private String metricId;
    private Double value;
    private Metric.Direction direction;
}