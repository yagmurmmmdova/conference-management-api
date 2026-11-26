package com.example.springmentorlesson21.mapper;

import com.example.springmentorlesson21.entity.SpeakerEntity;
import com.example.springmentorlesson21.model.SpeakerRequestDto;
import com.example.springmentorlesson21.model.SpeakerResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SpeakerMapper {

    SpeakerResponseDto toSpeaker(SpeakerEntity speakerEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "talkEntities", ignore = true)
    SpeakerEntity toEntity(SpeakerRequestDto speakerRequestDto);

}
