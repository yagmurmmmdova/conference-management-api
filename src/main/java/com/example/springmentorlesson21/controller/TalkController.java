package com.example.springmentorlesson21.controller;

import com.example.springmentorlesson21.model.ApiError;
import com.example.springmentorlesson21.model.TalkRequestDto;
import com.example.springmentorlesson21.model.TalkResponseDto;
import com.example.springmentorlesson21.service.TalkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/talks")
@RequiredArgsConstructor
@Validated
@Tag(name = "Talks", description = "Endpoints for managing conference talks")
public class TalkController {
    private final TalkService talkService;

    @Operation(summary = "Schedule a new talk", description = "Creates a new talk for an existing speaker (max 3 talks per speaker)")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Talk created successfully"),
            @ApiResponse(responseCode = "400", description = "Validation error or speaker talk limit exceeded",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Speaker not found",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TalkResponseDto saveTalk(@Valid @RequestBody TalkRequestDto talkRequestDto){
        return talkService.saveTalk(talkRequestDto);
    }

    @Operation(summary = "Get all talks", description = "Retrieves a list of all scheduled and finished talks")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Talks retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping
    public List<TalkResponseDto> getTalks() {
        return talkService.getTalks();
    }

    @Operation(summary = "Get talk by ID", description = "Retrieves details of a single talk by its unique ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Talk found"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Talk not found",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping("/{id}")
    public TalkResponseDto getTalkById(
            @PathVariable @Positive(message = "Id must be a positive number") int id) {
        return talkService.getTalkById(id);
    }

    @Operation(summary = "Update an existing talk", description = "Updates talk details and validates speaker limits and duration")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Talk updated successfully"),
            @ApiResponse(responseCode = "400", description = "Validation or business rule error",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Talk or speaker not found",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @PutMapping("/{id}")
    public TalkResponseDto updateTalk(
            @PathVariable @Positive(message = "Id must be a positive number") int id,
            @Valid @RequestBody TalkRequestDto talkRequestDto) {
        return talkService.updateTalk(id, talkRequestDto);
    }

    @Operation(summary = "Delete a talk", description = "Deletes an existing talk by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Talk deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Talk not found",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) //204
    public void deleteTalk(
            @PathVariable @Positive(message = "Id must be a positive number") int id) {
        talkService.deleteTalk(id);
    }

}
