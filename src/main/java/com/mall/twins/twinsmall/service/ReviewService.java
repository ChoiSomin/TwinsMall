package com.mall.twins.twinsmall.service;

import com.mall.twins.twinsmall.dto.ReviewFormDto;
import com.mall.twins.twinsmall.dto.ReviewImgDto;
import com.mall.twins.twinsmall.entity.Review;
import com.mall.twins.twinsmall.entity.ReviewImage;
import com.mall.twins.twinsmall.repository.ReviewImgRepository;
import com.mall.twins.twinsmall.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final ReviewImgService reviewImgService;

    private final ReviewImgRepository reviewImgRepository;

    public Long saveReview(ReviewFormDto reviewFormDto, List<MultipartFile> reviewImgFileList) throws Exception {

        Review review = reviewFormDto.createReview();
        System.out.println("리뷰내용 : " + review.getRcontent());
        System.out.println("리뷰평점 : " + review.getRscore());
        reviewRepository.save(review);

        for (int i = 0; i < reviewImgFileList.size(); i++) {
            ReviewImage reviewImage = new ReviewImage();
            reviewImage.setReview(review);
            if (i == 0)
                reviewImage.setRimgrep("Y");
            else
                reviewImage.setRimgrep("N");

            reviewImgService.saveReviewImg(reviewImage, reviewImgFileList.get(i));
        }
        return review.getRid();
    }

    @Transactional(readOnly = true)
    public ReviewFormDto getReviewDtl(Long ReviewRid){
        List<ReviewImage> reviewImgList = reviewImgRepository.findByReviewRidOrderByRidAsc(ReviewRid); // 해당 상품의 이미지 조회

        List<ReviewImgDto> reviewImgDtoList = new ArrayList<>();

        for(ReviewImage reviewImage : reviewImgList){ // 조회한 ItemImg 엔티티를 ItemImgDto 객체로 만들어서 리스트에 추가
            ReviewImgDto reviewImgDto = ReviewImgDto.of(reviewImage);
            reviewImgDtoList.add(reviewImgDto);
        }

        Review review = reviewRepository.findById(ReviewRid).orElseThrow(EntityNotFoundException::new); // 상품의 아이디를 통해 상품 엔티티를 조회

        ReviewFormDto reviewFormDto = ReviewFormDto.of(review);
        reviewFormDto.setReviewImgDtoList(reviewImgDtoList);

        return reviewFormDto;
    }

    //리뷰수정은 없음

}
