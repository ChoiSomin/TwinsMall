package com.mall.twins.twinsmall.dto;

import com.mall.twins.twinsmall.entity.ItemImg;
import com.mall.twins.twinsmall.entity.ReviewImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewImgDto {

    private Long rimgno;

    private String rimgnew;

    private String rimgori; //원본 이미지 파일명

    private String rimgurl; //이미지 조회 경로

    private String rimgrep; //대표 이미지 여부

    private static ModelMapper modelMapper = new ModelMapper();

    public static ReviewImgDto of(ReviewImage reviewImg) {
        // ItemImg 객체로 받아 ItemImg 객체의 자료형과 멤버변수의 이름이 같을 때 ItemImgDto로 값을 복사해서 반환
        return modelMapper.map(reviewImg, ReviewImgDto.class);
    }



}