package com.example.springmentorlesson21.entity;

import com.example.springmentorlesson21.model.TalkStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "talks")
public class TalkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String room;
    private Integer durationMinutes;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "speaker_id", nullable = false)
    private SpeakerEntity speaker;

    private LocalDateTime scheduledAt;

    @Enumerated(EnumType.STRING)
    private TalkStatus status;
}
