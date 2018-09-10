package com.code_hq.core.ui.api;

import com.code_hq.core.application.command.MetricCommandService;
import com.code_hq.core.application.dto.metric.MetricDto;
import com.code_hq.core.application.query.MetricQueryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/metrics")
@AllArgsConstructor
public class MetricController
{
    private MetricCommandService commandService;
    private MetricQueryService queryService;

    @GetMapping
    public ResponseEntity<List<MetricDto>> list()
    {
        return ResponseEntity.ok(queryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> one(@PathVariable("id") String id)
    {
        return queryService
            .findById(id)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> register(@PathVariable("id") String id, @RequestBody MetricDto metric)
    {
        // This method serves as both create and update.
        // We don't use a POST to the list() endpoint for create because IDs are provided by the client.

        HttpStatus status = HttpStatus.OK;

        if (queryService.existsById(id)) {
            // This metric already exists. Execute the corresponding update commands.
            commandService.rename(id, metric.getName());
        } else {
            // Define a new metric with this ID.
            commandService.define(metric);
            status = HttpStatus.CREATED;
        }

        return ResponseEntity.status(status).body(metric);
    }
}
