package com.code_hq.core.domain.score.scope;

import lombok.NonNull;
import lombok.Value;

/**
 * Scopes scores to a particular package or namespace within the application.
 */
@Value
public class PackageScope implements Scope
{
    private @NonNull String packageName;
}
