package com.code_hq.core.ui.api;

import com.code_hq.core.application.command.ScoreCommandService;
import com.code_hq.core.application.dto.score.ScoreDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications/{application}/scores")
@AllArgsConstructor
public class ScoreController
{
    private ScoreCommandService commandService;

    @PostMapping("/bulk")
    public ResponseEntity<?> bulk(
        @PathVariable("application") String application,
        @RequestBody List<ScoreDto> scores
    ) {
        // TODO: Use the normal collection POST endpoint with a different content type.
        // It's not especially nice to have a separate bulk endpoint as it breaks REST semantics - there is no "bulk"
        // resource whose state we are trying to modify. Instead, we want to perform bulk operations on existing
        // resources ("scores" in this case). We will need a "scores" collection resource endpoint which creates a
        // single "score" resource, so it makes sense to have this also accept a collection of "score" resources and
        // create them all (probably asynchronously).
        // For simplicity though a bulk endpoint is used for now.

        scores.forEach(score -> commandService.record(application, score));

        return ResponseEntity.accepted().build();
    }
}
