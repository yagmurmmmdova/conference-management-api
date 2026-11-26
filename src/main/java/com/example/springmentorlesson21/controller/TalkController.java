package com.example.springmentorlesson21.controller;

import com.example.springmentorlesson21.model.TalkRequestDto;
import com.example.springmentorlesson21.model.TalkResponseDto;
import com.example.springmentorlesson21.service.TalkService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/talks")
@RequiredArgsConstructor
public class TalkController {
    private final TalkService talkService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TalkResponseDto saveTalk(@Valid @RequestBody TalkRequestDto talkRequestDto){
        return talkService.saveTalk(talkRequestDto);
    }

    @GetMapping
    public List<TalkResponseDto> getTalks() {
        return talkService.getTalks();
    }

    @GetMapping("/{id}")
    public TalkResponseDto getTalkById(@PathVariable int id) {
        return talkService.getTalkById(id);
    }

    @PutMapping("/{id}")
    public TalkResponseDto updateTalk(@PathVariable int id, @Valid @RequestBody TalkRequestDto talkRequestDto) {
        return talkService.updateTalk(id, talkRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) //204
    public void deleteTalk(@PathVariable int id) {
        talkService.deleteTalk(id);
    }

}
