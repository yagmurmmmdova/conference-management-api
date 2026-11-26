package com.example.springmentorlesson21.service;

import com.example.springmentorlesson21.entity.SpeakerEntity;
import com.example.springmentorlesson21.entity.TalkEntity;
import com.example.springmentorlesson21.exception.DuplicateResourceException;
import com.example.springmentorlesson21.exception.ResourceNotFoundException;
import com.example.springmentorlesson21.mapper.SpeakerMapper;
import com.example.springmentorlesson21.mapper.TalkMapper;
import com.example.springmentorlesson21.model.SpeakerRequestDto;
import com.example.springmentorlesson21.model.SpeakerResponseDto;
import com.example.springmentorlesson21.model.TalkResponseDto;
import com.example.springmentorlesson21.repository.SpeakerRepository;
import com.example.springmentorlesson21.repository.TalkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SpeakerService {
    private final SpeakerRepository speakerRepository;
    private final SpeakerMapper speakerMapper;
    private final TalkRepository talkRepository;
    private final TalkMapper talkMapper;

    public SpeakerResponseDto saveSpeaker(SpeakerRequestDto speakerRequestDto) {
        log.debug("Attempting to save a new speaker");

        if (speakerRepository.existsByEmail(speakerRequestDto.getEmail())) {
            throw new DuplicateResourceException("Speaker with this email is already exists!");
        }
        SpeakerEntity entity = speakerMapper.toEntity(speakerRequestDto);
        SpeakerEntity speakerEntity = speakerRepository.save(entity);

        log.info("Speaker created with id: {}", speakerEntity.getId());
        return speakerMapper.toSpeaker(speakerEntity);
    }

    public List<SpeakerResponseDto> getSpeakers() {
        log.debug("Fetching all speakers...");

        List<SpeakerEntity> speakerEntity = speakerRepository.findAll();

        return speakerEntity.stream().map(speakerMapper::toSpeaker).toList();
    }

    public SpeakerResponseDto getSpeakerById(int id) {
        log.debug("Fetching speaker with id: {}", id);

        SpeakerEntity speakerEntity = speakerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Speaker Not Found"));

        return speakerMapper.toSpeaker(speakerEntity);
    }

    public List<TalkResponseDto> getTalksBySpeakerId(int speakerId) {
        log.debug("Fetching talks for speaker with id: {}", speakerId);

        if (!speakerRepository.existsById(speakerId)) {
            throw new ResourceNotFoundException("Speaker not found with id: " + speakerId);
        }

        List<TalkEntity> talks = talkRepository.findBySpeakerId(speakerId);

        return talks.stream().map(talkMapper::toTalk).toList();
    }

}
