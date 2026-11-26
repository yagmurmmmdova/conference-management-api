package com.example.springmentorlesson21.scheduler;

import com.example.springmentorlesson21.entity.TalkEntity;
import com.example.springmentorlesson21.model.TalkStatus;
import com.example.springmentorlesson21.repository.TalkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class TalkStatusJob {
    private final TalkRepository talkRepository;

    @Scheduled(cron = "0 * * * * *", zone = "Asia/Baku")
    @Transactional
    public void updateFinishedTalks() {
        try {
            List<TalkEntity> scheduledTalks = talkRepository.findByStatus(TalkStatus.SCHEDULED);

            int finishedCount = 0;
            for (TalkEntity talk : scheduledTalks) {
                LocalDateTime endTime = talk.getScheduledAt().plusMinutes(talk.getDurationMinutes());
                if (endTime.isBefore(LocalDateTime.now())) {
                    talk.setStatus(TalkStatus.FINISHED);
                    finishedCount++;
                }
            }

            if (finishedCount > 0) {
                log.info("{} talks marked as FINISHED", finishedCount);
            } else {
                log.debug("No scheduled talks have finished yet");
            }
        } catch (Exception e) {
            log.error("Status update job failed", e);
        }
    }

}
