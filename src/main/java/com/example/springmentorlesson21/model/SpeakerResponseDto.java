package com.example.springmentorlesson21.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class SpeakerResponseDto {
    private Integer id;
    private String fullName;
    private String email;
    private String company;

}
