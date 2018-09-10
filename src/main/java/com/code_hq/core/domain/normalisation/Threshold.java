package com.code_hq.core.domain.normalisation;

import lombok.Value;

@Value
public class Threshold
{
    private Operator operator;
    private Double value;

    enum Operator {
        LESS_THAN, LESS_THAN_EQUAL_TO, GREATER_THAN, GREATE_THAN_EQUAL_TO, EQUAL_TO, NOT_EQUAL_TO;
    }
}
