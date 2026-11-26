package com.example.springmentorlesson21.scheduler;

import com.example.springmentorlesson21.repository.SpeakerRepository;
import com.example.springmentorlesson21.repository.TalkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class TalkStatisticsJob {

    private final SpeakerRepository speakerRepository;
    private final TalkRepository talkRepository;

    @Scheduled(fixedRateString = "${conference.report.rate-ms}", initialDelay = 10000)
    public void reportStatistics() {
        try {
            log.debug("Statistics job started");

            long totalSpeakers = speakerRepository.count();
            long totalTalks = talkRepository.count();
            log.info("Conference statistics — Total speakers: {}, Total talks: {}", totalSpeakers, totalTalks);

            List<Object[]> roomCounts = talkRepository.countTalksByRoom();
            String roomStats = roomCounts.isEmpty()
                    ? "none"
                    : roomCounts.stream()
                    .map(row -> row[0] + ": " + row[1])
                    .collect(Collectors.joining(", "));

            log.info("Talks per room — {}", roomStats);

        }catch (Exception e) {
            log.error("Statistics job failed", e);
        }
    }
}
