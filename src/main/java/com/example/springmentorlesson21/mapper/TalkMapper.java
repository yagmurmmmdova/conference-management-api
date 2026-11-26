package com.example.springmentorlesson21.mapper;

import com.example.springmentorlesson21.entity.TalkEntity;
import com.example.springmentorlesson21.model.TalkRequestDto;
import com.example.springmentorlesson21.model.TalkResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TalkMapper {

    @Mapping(target = "speakerName", source = "speaker.fullName")
    @Mapping(target = "speakerId", source = "speaker.id")
    TalkResponseDto toTalk(TalkEntity talkEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "speaker", ignore = true)
    TalkEntity toEntity(TalkRequestDto talkRequestDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "speaker", ignore = true)
    void updateEntityFromModel(TalkRequestDto talkRequestDto, @MappingTarget TalkEntity talkEntity);

}
