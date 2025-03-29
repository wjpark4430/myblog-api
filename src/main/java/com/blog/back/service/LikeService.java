package com.blog.back.service;

public interface LikeService {
    void addLike(Long boardId, Long memberId);
    
    void removeLike(Long boardId, Long memberId);

    long getLikeCount(Long boardId);

    boolean hasUserLiked(Long boardId, Long memberId);
}
