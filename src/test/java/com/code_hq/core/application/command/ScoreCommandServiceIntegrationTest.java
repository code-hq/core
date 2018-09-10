package com.code_hq.core.application.command;

import com.code_hq.core.application.dto.score.ScoreDto;
import com.code_hq.core.application.dto.score.SimpleValueDto;
import com.code_hq.core.domain.application.Application;
import com.code_hq.core.domain.application.ApplicationRepository;
import com.code_hq.core.domain.metric.MetricIdentity;
import com.code_hq.core.domain.metric.MetricRepository;
import com.code_hq.core.domain.metric.SimpleMetric;
import com.code_hq.core.domain.score.Score;
import com.code_hq.core.domain.score.ScoreRepository;
import com.code_hq.core.domain.score.value.SimpleValue;
import com.code_hq.core.domain.version.Version;
import com.code_hq.core.domain.version.VersionIdentity;
import com.code_hq.core.domain.version.VersionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScoreCommandServiceIntegrationTest
{
    @Autowired
    private ScoreCommandService service;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private MetricRepository metricRepository;

    @Autowired
    private VersionRepository versionRepository;

    @Before
    public void setUp()
    {
        applicationRepository.deleteAll();
        scoreRepository.deleteAll();
        metricRepository.deleteAll();
        versionRepository.deleteAll();

        applicationRepository.save(Application.register("jmc", "Jupiter Mining Corp."));
        metricRepository.save(SimpleMetric.define(new MetricIdentity("code-coverage"), "Code Coverage"));
    }

    @Test
    public void whenValidScoreDto_thenScoreShouldBeRecorded()
    {
        // Given
        // A valid score DTO
        ScoreDto scoreDto = new ScoreDto("code-coverage", "master", new SimpleValueDto(42.0));
        // And an empty score repository
        assertThat(scoreRepository.findAll()).isEmpty();
        // And an existing version
        VersionIdentity versionIdentity = new VersionIdentity("jmc", "master");
        Version version = Version.define(versionIdentity);
        versionRepository.save(version);

        // When
        service.record("jmc", scoreDto);
        List<Score> result = scoreRepository.findAll();

        // Then
        assertThat(result).hasSize(1);

        Score score = result.get(0);

        assertThat(score.getValue()).isEqualTo(new SimpleValue(score.getId(), 42.0));
        assertThat(score.getVersionId()).isEqualTo(versionIdentity);
    }
}
