package com.code_hq.core.domain.metric;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MetricRepository extends JpaRepository<Metric, MetricIdentity>
{
}
