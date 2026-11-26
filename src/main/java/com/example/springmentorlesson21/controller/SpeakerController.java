package com.example.springmentorlesson21.controller;

import com.example.springmentorlesson21.model.SpeakerRequestDto;
import com.example.springmentorlesson21.model.SpeakerResponseDto;
import com.example.springmentorlesson21.model.TalkResponseDto;
import com.example.springmentorlesson21.service.SpeakerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/speakers")
@RequiredArgsConstructor
public class SpeakerController {
    private final SpeakerService speakerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SpeakerResponseDto saveSpeaker(@Valid @RequestBody SpeakerRequestDto speakerRequestDto) {
        return speakerService.saveSpeaker(speakerRequestDto);
    }

    @GetMapping
    public List<SpeakerResponseDto> getSpeakers() {
        return speakerService.getSpeakers();
    }

    @GetMapping("/{id}")
    public SpeakerResponseDto getSpeakerById(@PathVariable int id) {
        return speakerService.getSpeakerById(id);
    }

    @GetMapping("/{id}/talks")
    public List<TalkResponseDto> getTalksBySpeakerId(@PathVariable("id") int speakerId) {
        return speakerService.getTalksBySpeakerId(speakerId);
    }
}
