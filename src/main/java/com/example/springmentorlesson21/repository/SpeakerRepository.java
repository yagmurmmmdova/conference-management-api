package com.example.springmentorlesson21.repository;

import com.example.springmentorlesson21.entity.SpeakerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpeakerRepository extends JpaRepository<SpeakerEntity, Integer> {

    boolean existsByEmail(String email);
}
