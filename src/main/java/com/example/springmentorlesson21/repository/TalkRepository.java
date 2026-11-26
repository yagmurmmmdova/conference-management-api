package com.example.springmentorlesson21.repository;

import com.example.springmentorlesson21.entity.TalkEntity;
import com.example.springmentorlesson21.model.TalkStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TalkRepository extends JpaRepository<TalkEntity, Integer> {

    int countBySpeakerId(Integer speakerId);

    List<TalkEntity> findBySpeakerId(Integer speakerId);

    @Query("SELECT t.room, COUNT(t) FROM TalkEntity t GROUP BY t.room")
    List<Object[]> countTalksByRoom();

    List<TalkEntity> findByStatus(TalkStatus status);
}
