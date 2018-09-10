package com.code_hq.core.domain.score;

import com.code_hq.core.domain.metric.Metric;
import com.code_hq.core.domain.metric.MetricIdentity;
import com.code_hq.core.domain.score.scope.Scope;
import com.code_hq.core.domain.score.value.Value;
import com.code_hq.core.domain.version.Version;
import com.code_hq.core.domain.version.VersionIdentity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * An application's score against a particular metric.
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
@Getter
public class Score
{
    @Id
    @NonNull
    private UUID id;

    @NonNull
    @NotNull
    @Embedded
    private VersionIdentity versionId;

    @NonNull
    @NotNull
    @Embedded
    @AttributeOverride(name="id", column=@Column(name="metric_id"))
    private MetricIdentity metricId;

    @NonNull
    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private com.code_hq.core.domain.score.value.Value value;

    @NonNull
    @NotNull
    private LocalDateTime collectedAt;

    @Embedded
    private Scope scope;

    private Score(
        final UUID id,
        final Metric metric,
        final com.code_hq.core.domain.version.Version version,
        final com.code_hq.core.domain.score.value.Value value,
        final LocalDateTime collectedAt,
        final Scope scope
    ) {
        if (!metric.accepts(value)) {
            throw new IllegalArgumentException("Incorrect value type for metric.");
        }

        this.id = id;
        this.metricId = metric.getId();
        this.versionId = version.getId();
        this.value = value;
        this.collectedAt = collectedAt;
        this.scope = scope;
    }

    // FIXME: Only Score should be able to create a Score as Score is the aggregate root!
    public static Score record(final UUID id, final Metric metric, final Version version, final Value value)
    {
        return new Score(id, metric, version, value, LocalDateTime.now(), null);
    }
}
