package com.mall.twins.twinsmall.dto;

import com.mall.twins.twinsmall.entity.NoticeImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoticeImgDto {

    private Long nimgno;

    private String nimgnew;

    private String nimgori;

    private String nimgurl;

    private String nimgrep;

    private static ModelMapper modelMapper = new ModelMapper();

    public static NoticeImgDto of(NoticeImage noticeImage){
        return modelMapper.map(noticeImage, NoticeImgDto.class);
    }


}
