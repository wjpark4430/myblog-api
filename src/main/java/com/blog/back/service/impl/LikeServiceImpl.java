package com.blog.back.service.impl;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.back.domain.Board;
import com.blog.back.domain.BoardLike;
import com.blog.back.domain.Member;
import com.blog.back.repository.BoardLikeRepository;
import com.blog.back.repository.BoardRepository;
import com.blog.back.repository.MemberRepository;
import com.blog.back.service.LikeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private static final String LIKE_COUNT_KEY = "board:like_count:";
    private static final String LIKE_USERS_KEY = "board:like_users:";

    private final RedisTemplate<String, Object> redisTemplate;

    private final BoardLikeRepository boardLikeRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void addLike(Long boardId, Long memberId) {
        String userSetKey = LIKE_USERS_KEY + boardId;
        String countKey = LIKE_COUNT_KEY + boardId;

        Boolean isMember = redisTemplate.opsForSet().isMember(userSetKey, memberId);

        if (isMember == null) {
            log.error("Redis search error");
            throw new IllegalStateException();
        }

        if (!isMember) {
            redisTemplate.opsForSet().add(userSetKey, memberId);
            redisTemplate.opsForValue().increment(countKey);

            Board board = boardRepository.findById(boardId).orElseThrow(() -> new RuntimeException());
            Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException());
            BoardLike boardLike = BoardLike.ofCreate(board, member);

            boardLikeRepository.save(boardLike);
        }
    }

    @Override
    @Transactional
    public void removeLike(Long boardId, Long memberId) {
        String userSetKey = LIKE_USERS_KEY + boardId;
        String countKey = LIKE_COUNT_KEY + boardId;

        Boolean isMember = redisTemplate.opsForSet().isMember(userSetKey, memberId);

        if (isMember == null) {
            log.error("Redis search error");
            throw new IllegalStateException();
        }

        if (isMember) {
            redisTemplate.opsForSet().remove(userSetKey, memberId);
            redisTemplate.opsForValue().decrement(countKey);

            boardLikeRepository.deleteByBoardIdAndMemberId(boardId, memberId);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public long getLikeCount(Long boardId) {
        Long count = (Long) redisTemplate.opsForValue().get(LIKE_COUNT_KEY + boardId);
        return count == null ? 0L : count;
    }

    @Override
    public boolean hasUserLiked(Long boardId, Long memberId) {
        Boolean hasLiked = redisTemplate.opsForSet().isMember(LIKE_USERS_KEY + boardId, memberId);

        if (hasLiked == null) {
            log.error("Redis search error");
            throw new IllegalStateException();
        }

        return hasLiked;
    }

}

