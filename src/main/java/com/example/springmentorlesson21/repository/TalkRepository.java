package com.example.springmentorlesson21.repository;

import com.example.springmentorlesson21.entity.TalkEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TalkRepository extends JpaRepository<TalkEntity, Integer> {

    int countBySpeakerId(Integer speakerId);

    List<TalkEntity> findBySpeakerId(Integer speakerId);
}
