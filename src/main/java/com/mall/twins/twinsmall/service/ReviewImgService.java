package com.mall.twins.twinsmall.service;

import com.mall.twins.twinsmall.entity.ItemImg;
import com.mall.twins.twinsmall.entity.ReviewImage;
import com.mall.twins.twinsmall.repository.ReviewImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewImgService {

    @Value("${reviewImgLocation}")
    private String reviewImgLocation;

    private final ReviewImgRepository reviewImgRepository;

    private final FileService fileService;

    public void saveReviewImg(ReviewImage reviewImage, MultipartFile reviewImgFile) throws Exception{
        String rimgori = reviewImgFile.getOriginalFilename();
        String rimgnew = "";
        String rimgurl = "";

        //파일 업로드
        if(!StringUtils.isEmpty(rimgori)){
            rimgnew = fileService.uploadFile(reviewImgLocation, rimgori,
                    reviewImgFile.getBytes());
            rimgurl = "/images/reviewImages/" + rimgnew;
        }

        //상품 이미지 정보 저장
        reviewImage.updateReviewImg(rimgori, rimgnew, rimgurl);
        reviewImgRepository.save(reviewImage);
    }


    //리뷰수정불가


}
