package com.blog.back.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.blog.back.service.LikeSyncService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class LikeSyncScheduler {

    private final LikeSyncService likeSyncService;

    @Scheduled(fixedRate = 60000) 
    public void scheduleLikeSync() {
        log.info("Redis → MySQL Start Like Sync...");
        likeSyncService.syncLikesToDatabase();
    }
}
