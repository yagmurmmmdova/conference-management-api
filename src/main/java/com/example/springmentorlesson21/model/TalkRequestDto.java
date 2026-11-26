package com.example.springmentorlesson21.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    private String title;

    @NotBlank(message = "Room is required")
    private String room;

    @NotNull(message = "Duration is required")
    private Integer durationMinutes;

    @NotNull(message = "Speaker ID is required")
    private Integer speakerId;

    @NotNull(message = "Scheduled time is required")
    private LocalDateTime scheduledAt;
}
