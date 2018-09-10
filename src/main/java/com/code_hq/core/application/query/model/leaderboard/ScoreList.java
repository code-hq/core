package com.code_hq.core.application.query.model.leaderboard;

import lombok.*;
import lombok.experimental.Wither;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ScoreList {
    private @NonNull String applicationId;
    private @NonNull String applicationName;
    private @Wither Double overallScore;
    private Map<String, Score> individualScores;
}
