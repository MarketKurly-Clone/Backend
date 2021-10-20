package com.sparta.kerly_clone.service;

import com.sparta.kerly_clone.dto.requestDto.LikeRequestDto;
import com.sparta.kerly_clone.exception.NoItemException;
import com.sparta.kerly_clone.model.Liked;
import com.sparta.kerly_clone.model.Review;
import com.sparta.kerly_clone.model.User;
import com.sparta.kerly_clone.repository.LikedRepository;
import com.sparta.kerly_clone.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikedService {

    private final ReviewRepository reviewRepository;
    private final LikedRepository likedRepository;

    @Autowired
    public LikedService(ReviewRepository reviewRepository, LikedRepository likedRepository) {
        this.reviewRepository = reviewRepository;
        this.likedRepository = likedRepository;
    }

    @Transactional
    public boolean likedReview(LikeRequestDto requestDto, User user) {
        Review review = reviewRepository.findById(requestDto.getReviewId()).orElseThrow(
                () -> new NoItemException("해당 리뷰를 찾을 수 없습니다.")
        );
        Liked liked = likedRepository.findByUserIdAndReviewId(user.getId(), review.getId()).orElse(null);
        if (liked == null) {
            liked = new Liked(review, user);
            likedRepository.save(liked);
            return true;
        }
        liked.delete();
        likedRepository.delete(liked);
        return false;
    }
}
