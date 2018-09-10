package com.code_hq.core.application.command;

import com.code_hq.core.domain.metric.Metric;
import com.code_hq.core.domain.metric.MetricIdentity;
import com.code_hq.core.domain.metric.MetricRepository;
import com.code_hq.core.domain.score.Score;
import com.code_hq.core.domain.score.ScoreRepository;
import com.code_hq.core.domain.score.value.FractionalValue;
import com.code_hq.core.domain.score.value.SimpleValue;
import com.code_hq.core.domain.score.value.Value;
import com.code_hq.core.domain.version.Version;
import com.code_hq.core.domain.version.VersionIdentity;
import com.code_hq.core.domain.version.VersionRepository;
import com.code_hq.core.application.dto.score.FractionalValueDto;
import com.code_hq.core.application.dto.score.ScoreDto;
import com.code_hq.core.application.dto.score.SimpleValueDto;
import com.code_hq.core.application.dto.score.ValueDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ScoreCommandService
{
    private ScoreRepository scoreRepository;
    private MetricRepository metricRepository;
    private VersionRepository versionRepository;

    public void record(final String applicationId, final ScoreDto dto) throws IllegalArgumentException
    {
        UUID scoreId = UUID.randomUUID();
        Metric metric = resolveReferencedMetric(dto.getMetric());
        Version version = resolveReferencedVersion(applicationId, dto.getVersion());
        Value value = marshallValueDtoToDomainObject(scoreId, dto.getValue());
        Score score = Score.record(scoreId, metric, version, value);

        scoreRepository.save(score);
    }

    private Metric resolveReferencedMetric(String id)
    {
        MetricIdentity metricId = new MetricIdentity(id);

        return metricRepository
            .findById(metricId)
            // FIXME: Better exception.
            .orElseThrow(() -> new IllegalArgumentException("Cannot find metric."));
    }

    private Version resolveReferencedVersion(String applicationId, String version)
    {
        VersionIdentity id = new VersionIdentity(applicationId, version);

        // Does the referenced version exist? If not, create it now.
        return versionRepository
            .findById(id)
            .orElseGet(() -> versionRepository.save(Version.define(id)));
    }

    private Value marshallValueDtoToDomainObject(UUID scoreId, ValueDto dto)
    {
        if (dto instanceof SimpleValueDto) {
            return new SimpleValue(scoreId, ((SimpleValueDto) dto).getValue());
        }

        if (dto instanceof FractionalValueDto) {
            FractionalValueDto fractionalDto = (FractionalValueDto) dto;
            return new FractionalValue(scoreId, fractionalDto.getNumerator(), fractionalDto.getDenominator());
        }

        throw new IllegalArgumentException("Unsupported value type.");
    }
}
