package com.example.springmentorlesson21.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class TalkRequestDto {
    @NotBlank(message = "Title is required")
    @Size(min = 5, max = 150, message = "Title must be 5-150 characters")
    private String title;

    @NotBlank(message = "Room is required")
    private String room;

    @NotNull(message = "Duration is required")
    @Min(value = 15, message = "Duration must be between 15 and 90 minutes")
    @Max(value = 90, message = "Duration must be between 15 and 90 minutes")
    private Integer durationMinutes;

    @NotNull(message = "Speaker ID is required")
    @Positive(message = "Speaker id must be a positive number")
    private Integer speakerId;

    @NotNull(message = "Scheduled time is required")
    @Future(message = "Talk must be scheduled in the future")
    private LocalDateTime scheduledAt;
}
