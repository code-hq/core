package com.code_hq.core.application.query.model.score;


import com.code_hq.core.application.query.model.score.value.Value;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.Date;

@AllArgsConstructor
@Getter
public class ScoreDetails
{
    private @NonNull String metric;
    private @NonNull String scope;
    private @NonNull String version;
    private @NonNull
    Value value;
    private @NonNull Date collectedAt;
}
