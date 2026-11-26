package com.example.springmentorlesson21.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
}
