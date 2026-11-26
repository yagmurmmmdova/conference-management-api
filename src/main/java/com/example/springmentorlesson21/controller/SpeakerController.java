package com.example.springmentorlesson21.controller;

import com.example.springmentorlesson21.model.ApiError;
import com.example.springmentorlesson21.model.SpeakerRequestDto;
import com.example.springmentorlesson21.model.SpeakerResponseDto;
import com.example.springmentorlesson21.model.TalkResponseDto;
import com.example.springmentorlesson21.service.SpeakerService;
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
@RequestMapping("/speakers")
@RequiredArgsConstructor
@Validated
@Tag(name = "Speakers", description = "Endpoints for managing conference speakers")
public class SpeakerController {
    private final SpeakerService speakerService;

    @Operation(summary = "Register a new speaker", description = "Creates a new speaker with a unique email address")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Speaker registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "409", description = "Email already exists",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SpeakerResponseDto saveSpeaker(@Valid @RequestBody SpeakerRequestDto speakerRequestDto) {
        return speakerService.saveSpeaker(speakerRequestDto);
    }

    @Operation(summary = "Get all speakers", description = "Retrieves a list of all registered speakers")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Speakers retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping
    public List<SpeakerResponseDto> getSpeakers() {
        return speakerService.getSpeakers();
    }

    @Operation(summary = "Get speaker by ID", description = "Retrieves a single speaker by their unique ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Speaker found"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Speaker not found",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping("/{id}")
    public SpeakerResponseDto getSpeakerById(
            @PathVariable @Positive(message = "Id must be a positive number") int id) {
        return speakerService.getSpeakerById(id);
    }

    @Operation(summary = "Get talks by speaker ID", description = "Retrieves all conference talks assigned to a specific speaker")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Talks retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Speaker not found",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping("/{id}/talks")
    public List<TalkResponseDto> getTalksBySpeakerId(
            @PathVariable("id") @Positive(message = "Id must be a positive number") int speakerId) {
        return speakerService.getTalksBySpeakerId(speakerId);
    }
}
