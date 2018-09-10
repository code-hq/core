package com.code_hq.core.application.query.model.score.value;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class FractionalValue implements Value
{
    @NonNull
    private Double numerator;

    @NonNull
    private Double denominator;

    @Override
    public String format()
    {
        return String.format("%.2f/%.2f", numerator, denominator);
    }
}
