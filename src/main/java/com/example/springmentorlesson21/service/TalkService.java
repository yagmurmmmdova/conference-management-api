package com.example.springmentorlesson21.service;

import com.example.springmentorlesson21.entity.SpeakerEntity;
import com.example.springmentorlesson21.entity.TalkEntity;
import com.example.springmentorlesson21.exception.BusinessRuleException;
import com.example.springmentorlesson21.exception.ResourceNotFoundException;
import com.example.springmentorlesson21.mapper.TalkMapper;
import com.example.springmentorlesson21.model.TalkRequestDto;
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
public class TalkService {
    private final TalkRepository talkRepository;
    private final TalkMapper talkMapper;
    private final SpeakerRepository speakerRepository;

    public TalkResponseDto saveTalk(TalkRequestDto talkRequestDto) {
        log.debug("Attempting to save a new talk");

        SpeakerEntity speaker = speakerRepository.findById(talkRequestDto.getSpeakerId())
                .orElseThrow(() -> new ResourceNotFoundException("Speaker not found with id: " +
                        talkRequestDto.getSpeakerId()));

        int existingTalkCounts = talkRepository.countBySpeakerId(talkRequestDto.getSpeakerId());

        if (existingTalkCounts >= 3) {
            throw new BusinessRuleException("A speaker can have at most 3 talks!");
        }

        if (talkRequestDto.getDurationMinutes() < 15 || talkRequestDto.getDurationMinutes() > 90) {
            throw new BusinessRuleException("Talk duration must be between 15 and 90 minutes.");
        }
        TalkEntity entity = talkMapper.toEntity(talkRequestDto);
        entity.setSpeaker(speaker);
        TalkEntity talkEntity = talkRepository.save(entity);

        log.info("Talk created with id: {}", talkEntity.getId());
        return talkMapper.toTalk(talkEntity);
    }

    public List<TalkResponseDto> getTalks() {
        log.debug("Fetching all talks...");

        List<TalkEntity> talkEntities = talkRepository.findAll();

        return talkEntities.stream().map(talkMapper::toTalk).toList();
    }

    public TalkResponseDto getTalkById(int id) {
        log.debug("Fetching talk with id: {}", id);

        TalkEntity talkEntity = talkRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Talk not found with id: " + id));

        return talkMapper.toTalk(talkEntity);
    }

    public TalkResponseDto updateTalk(int id, TalkRequestDto talkRequestDto) {
        log.debug("Updating talk {}", id);

        TalkEntity existingTalk = talkRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Talk not found with id: " + id));

        SpeakerEntity speaker = speakerRepository.findById(talkRequestDto.getSpeakerId())
                .orElseThrow(() -> new ResourceNotFoundException("Speaker not found with id: " + talkRequestDto.getSpeakerId()));

        if (talkRequestDto.getDurationMinutes() < 15 || talkRequestDto.getDurationMinutes() > 90) {
            throw new BusinessRuleException("Talk duration must be between 15 and 90 minutes.");
        }

        if (!existingTalk.getSpeaker().getId().equals(speaker.getId())) {
            int existingTalkCounts = talkRepository.countBySpeakerId(speaker.getId());
            if (existingTalkCounts >= 3) {
                throw new BusinessRuleException("A speaker can have at most 3 talks!");
            }
        }

        talkMapper.updateEntityFromModel(talkRequestDto, existingTalk);
        existingTalk.setSpeaker(speaker);
        TalkEntity updatedTalk = talkRepository.save(existingTalk);

        log.info("Talk updated with id {}", updatedTalk.getId());
        return talkMapper.toTalk(updatedTalk);
    }

    public void deleteTalk(int id) {
        TalkEntity talkEntity = talkRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Talk not found with id: " + id));

        talkRepository.delete(talkEntity);

        log.info("Talk deleted with id: {}", id);
    }

}
