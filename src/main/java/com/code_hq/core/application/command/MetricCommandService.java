package com.code_hq.core.application.command;

import com.code_hq.core.domain.metric.*;
import com.code_hq.core.application.dto.metric.FractionalMetricDto;
import com.code_hq.core.application.dto.metric.MetricDto;
import com.code_hq.core.application.dto.metric.SimpleMetricDto;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MetricCommandService
{
    private MetricRepository metricRepository;

    public void define(MetricDto dto)
    {
        MetricIdentity metricIdentity = new MetricIdentity(dto.getId());
        Metric metric;

        if (dto instanceof FractionalMetricDto) {
            metric = FractionalMetric.define(metricIdentity, dto.getName());
        } else if (dto instanceof SimpleMetricDto) {
            metric = SimpleMetric.define(metricIdentity, dto.getName());
        } else {
            throw new IllegalArgumentException("Invalid metric DTO type.");
        }

        metricRepository.save(metric);
    }

    public void rename(final String id, final String name) throws IllegalArgumentException
    {
        MetricIdentity metricIdentity = new MetricIdentity(id);

        Metric metric = metricRepository
            .findById(metricIdentity)
            .orElseThrow(() -> new IllegalArgumentException("Cannot find metric with ID " + id + "."));

        metric.rename(name);

        metricRepository.save(metric);
    }

    public void delete(final String id) throws IllegalArgumentException
    {
        MetricIdentity metricIdentity = new MetricIdentity(id);

        try {
            metricRepository.deleteById(metricIdentity);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("Cannot find metric with ID " + id + ".", e);
        }
    }
}
