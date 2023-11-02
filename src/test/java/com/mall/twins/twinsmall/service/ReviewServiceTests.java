package com.mall.twins.twinsmall.service;

import com.mall.twins.twinsmall.dto.ReviewFormDto;
import com.mall.twins.twinsmall.entity.Review;
import com.mall.twins.twinsmall.entity.ReviewImage;
import com.mall.twins.twinsmall.repository.ReviewImgRepository;
import com.mall.twins.twinsmall.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ReviewServiceTests {

    @Autowired
    ReviewService reviewService;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    ReviewImgRepository reviewImgRepository;

    List<MultipartFile> createMultipartFiles() throws Exception{

        List<MultipartFile> multipartFileList = new ArrayList<>();

        for(int i=0;i<5;i++){
            String path = "D:/upload/reviewImages/";
            String rimgnew = "image" + i + ".jpg";
            MockMultipartFile multipartFile =
                    new MockMultipartFile(path, rimgnew, "image/jpg", new byte[]{1,2,3,4});
            multipartFileList.add(multipartFile);
        }

        return multipartFileList;
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void saveReview() throws Exception {
        ReviewFormDto reviewFormDto = new ReviewFormDto();
        reviewFormDto.setRcontent("리뷰내용입니당...");
        reviewFormDto.setRscore(3);

        List<MultipartFile> multipartFileList = createMultipartFiles();
        Long ReviewRid = reviewService.saveReview(reviewFormDto, multipartFileList);

        System.out.println(ReviewRid);

        List<ReviewImage> reviewImgList = reviewImgRepository.findByReviewRidOrderByRidAsc(ReviewRid);

        Review review = reviewRepository.findById(ReviewRid)
                .orElseThrow(EntityNotFoundException::new);

        assertEquals(reviewFormDto.getRcontent(), review.getRcontent());
        assertEquals(reviewFormDto.getRscore(), review.getRscore());
        assertEquals(multipartFileList.get(0).getOriginalFilename(), reviewImgList.get(0).getRimgori());
    }


}
