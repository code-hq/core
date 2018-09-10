package com.code_hq.core.ui.api;

import com.code_hq.core.application.command.ApplicationCommandService;
import com.code_hq.core.application.dto.application.ApplicationDto;
import com.code_hq.core.application.query.ApplicationQueryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("applicationApiController")
@RequestMapping("/api/applications")
@AllArgsConstructor
public class ApplicationController
{
    private ApplicationQueryService queryService;
    private ApplicationCommandService commandService;

    @GetMapping
    public ResponseEntity<?> list()
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
    public ResponseEntity<?> register(@PathVariable("id") String id, @RequestBody ApplicationDto application)
    {
        // This method serves as both create and update.
        // We don't use a POST to the list() endpoint for create because IDs are provided by the client.

        HttpStatus status = HttpStatus.OK;

        if (queryService.existsById(id)) {
            // This application already exists. Execute the corresponding update commands.
            commandService.rename(id, application.getName());
        } else {
            // Register a new application with this ID.
            commandService.register(id, application.getName());
            status = HttpStatus.CREATED;
        }

        return new ResponseEntity(status);
    }
}
