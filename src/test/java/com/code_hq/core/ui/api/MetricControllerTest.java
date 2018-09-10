package com.code_hq.core.ui.api;

import com.code_hq.core.domain.metric.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.code_hq.core.application.dto.metric.FractionalMetricDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MetricControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MetricRepository metricRepository;

    @Before
    public void setUp()
    {
        metricRepository.deleteAll();
    }

    @Test
    public void givenMetrics_whenListMetrics_thenExpectedResource()
    throws Exception
    {
        Metric coverage = define("coverage", "Test Coverage", FractionalMetric.class);
        Metric loc = define("loc", "Lines of Code", SimpleMetric.class);

        mockMvc
            .perform(get("/api/metrics")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].type", is("fraction")))
            .andExpect(jsonPath("$[0].id", is(coverage.getId().getId())))
            .andExpect(jsonPath("$[0].name", is(coverage.getName())))
            .andExpect(jsonPath("$[1].type", is("simple")))
            .andExpect(jsonPath("$[1].id", is(loc.getId().getId())))
            .andExpect(jsonPath("$[1].name", is(loc.getName())));
    }

    @Test
    public void givenMetrics_whenGetOneMetric_thenExpectedResource()
    throws Exception {

        Metric coverage = define("coverage", "Test Coverage", FractionalMetric.class);

        mockMvc
            .perform(get("/api/metrics/coverage")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.type", is("fraction")))
            .andExpect(jsonPath("$.id", is(coverage.getId().getId())))
            .andExpect(jsonPath("$.name", is(coverage.getName())));
    }

    @Test
    public void givenMetricWhichDoesNotExist_whenPutMetric_thenExpectedResource()
    throws Exception
    {
        FractionalMetricDto dto = new FractionalMetricDto("lloc", "Logical Lines of Code");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(dto);

        mockMvc
            .perform(put("/api/metrics/coverage")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isCreated())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id", is(dto.getId())))
            .andExpect(jsonPath("$.type", is("fraction")))
            .andExpect(jsonPath("$.name", is(dto.getName())));
    }

    private <T extends Metric> T define(String id, String name, Class<T> type)
    {
        MetricIdentity metricIdentity = new MetricIdentity(id);

        if (type.equals(SimpleMetric.class)) {
            return (T) metricRepository.save(SimpleMetric.define(metricIdentity, name));
        }

        if (type.equals(FractionalMetric.class)) {
            return (T) metricRepository.save(FractionalMetric.define(metricIdentity, name));
        }

        throw new IllegalArgumentException("Invalid metric type supplied.");
    }
}
