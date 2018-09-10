package com.code_hq.core.init;

import com.code_hq.core.domain.metric.MetricRepository;
import com.code_hq.core.application.command.MetricCommandService;
import com.code_hq.core.application.dto.metric.FractionalMetricDto;
import com.code_hq.core.application.dto.metric.SimpleMetricDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MetricInitializer
{
    private final @NonNull
    MetricRepository metricRepository;
    private final @NonNull MetricCommandService metricCommandService;

    @EventListener
    public void init(ApplicationReadyEvent event) {

        if (metricRepository.count() != 0) {
            log.debug("Skipping metric initialisation as metrics already exist");
            return;
        }

        log.info("Initialising metrics");

        metricCommandService.define(new FractionalMetricDto("test-coverage", "Test Coverage"));
        metricCommandService.define(new FractionalMetricDto("comment-weight", "Comment Weight"));
        metricCommandService.define(new SimpleMetricDto("lines-of-code", "Lines of Code"));
        metricCommandService.define(new SimpleMetricDto("comment-lines-of-code", "Comment Lines of Code"));
        metricCommandService.define(new SimpleMetricDto("logical-lines-of-code", "Logical Lines of Code"));
        metricCommandService.define(new SimpleMetricDto("package-count", "Package Count"));
        metricCommandService.define(new SimpleMetricDto("class-count", "Class Count"));
        metricCommandService.define(new SimpleMetricDto("interface-count", "Interface Count"));
        metricCommandService.define(new SimpleMetricDto("method-count", "Method Count"));
    }
}
