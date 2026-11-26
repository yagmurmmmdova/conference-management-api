package com.example.springmentorlesson21.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class TalkResponseDto {
    private Integer id;
    private String title;
    private String room;
    private Integer durationMinutes;
    private Integer speakerId;
    private String speakerName;

    private LocalDateTime scheduledAt;
    private TalkStatus status;
}
