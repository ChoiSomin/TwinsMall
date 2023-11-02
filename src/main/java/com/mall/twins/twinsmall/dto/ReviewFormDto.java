package com.mall.twins.twinsmall.dto;

import com.mall.twins.twinsmall.entity.Review;
import lombok.Data;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
public class ReviewFormDto {

    private Long rno;

    @NotBlank(message = "내용은 필수 입력입니다.")
    private String rcontent; // 리뷰내용

    @NotNull(message ="평점은 필수 입력입니다.")
    private int rscore;

    private List<ReviewImgDto> reviewImgDtoList = new ArrayList<>(); // 상품 저장 후 수정할 때 상품 이비지 정보를 저장하는 리스트

    private List<Long> reviewImgIds = new ArrayList<>(); // 상품의 이미지 아이디를 저장하는 리스트

    private static ModelMapper modelMapper = new ModelMapper();

    public Review createReview() { // 엔티티 객체의 데이터를 복사하여 복사한 객체를 반환해줌
        return modelMapper.map(this, Review.class);
    }

    public static ReviewFormDto of(Review review){ // dto 객체의 데이터를 복사하여 복사한 객체를 반환해줌
        return modelMapper.map(review, ReviewFormDto.class);
    }

}
