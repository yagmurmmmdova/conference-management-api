package com.example.springmentorlesson21.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Getter
@Setter
@ToString
@Entity
@Table(name = "speaker", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class SpeakerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String fullName;
    private String email;
    private String company;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "speaker")
    private List<TalkEntity> talkEntities;

}
