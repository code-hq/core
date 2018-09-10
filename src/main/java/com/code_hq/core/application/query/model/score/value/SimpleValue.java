package com.code_hq.core.application.query.model.score.value;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class SimpleValue implements Value
{
    @NonNull
    private Double value;

    @Override
    public String format()
    {
        return value.toString();
    }
}
