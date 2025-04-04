package com.blog.back.service.impl;

import java.util.Set;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.back.repository.BoardRepository;
import com.blog.back.service.LikeSyncService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikeSyncServiceImpl implements LikeSyncService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final BoardRepository boardRepository;

    private static final String LIKE_COUNT_KEY = "board:likeCount:";

    @Override
    @Transactional
    public void syncLikesToDatabase() {
        Set<String> keys = redisTemplate.keys(LIKE_COUNT_KEY + "*");
        if (keys == null || keys.isEmpty()) {
            log.info("Nothing Sync Data");
            return;
        }

        for (String key : keys) {
            try {
                Long boardId = Long.valueOf(key.replace(LIKE_COUNT_KEY, ""));
                Set<Object> members = redisTemplate.opsForSet().members(key);
                int likeCount = (members == null) ? 0 : members.size();

                boardRepository.updateLikeCount(boardId, likeCount);
                log.info("Complete Sync Like - boardId: {}, likeCount: {}", boardId, likeCount);

            } catch (Exception e) {
                log.error("Failed Sync Like - key: {}, error: {}", key, e.getMessage());
            }
        }
    }
}
