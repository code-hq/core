package com.code_hq.core.domain.normalisation;

import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.Collection;

@Value
@EqualsAndHashCode(callSuper = true)
public class ThresholdNormalisationConfig extends NormalisationConfig
{
    private Collection<Threshold> thresholds;
}

