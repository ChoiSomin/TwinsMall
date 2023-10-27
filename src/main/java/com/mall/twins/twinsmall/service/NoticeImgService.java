package com.mall.twins.twinsmall.service;

import com.mall.twins.twinsmall.entity.ItemImg;
import com.mall.twins.twinsmall.entity.NoticeImage;
import com.mall.twins.twinsmall.repository.ItemImgRepository;
import com.mall.twins.twinsmall.repository.NoticeImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class NoticeImgService {
    @Value("${noticeImgLocation}")
    private String noticeImgLocation;

    private final NoticeImgRepository noticeImgRepository;

    private final FileService fileService;

    public void saveNoticeImg(NoticeImage noticeImage, MultipartFile noticeImgFile) throws Exception{
        String nimgori = noticeImgFile.getOriginalFilename();
        String nimgnew = "";
        String nimgurl = "";

        //파일 업로드
        if(!StringUtils.isEmpty(nimgori)){
            nimgnew = fileService.uploadFile(noticeImgLocation, nimgori,
                    noticeImgFile.getBytes());
            nimgurl = "/images/noticeImages/" + nimgnew;
        }

        //상품 이미지 정보 저장
        noticeImage.updateNoticeImg(nimgori, nimgnew, nimgurl);
        noticeImgRepository.save(noticeImage);
    }

    public void updateItemImg(Long noticeImgId, MultipartFile noticeImgFile) throws Exception{
        if(!noticeImgFile.isEmpty()) { // 상품 이미지를 수정한 경우 상품 이미지를 업데이트
            NoticeImage savedNoticeImg = noticeImgRepository.findById(noticeImgId).orElseThrow(EntityNotFoundException::new);

            //기존 이미지 파일 삭제
            if(!StringUtils.isEmpty(savedNoticeImg.getNimgnew())) {
                fileService.deleteFile(noticeImgLocation+"/"+
                        savedNoticeImg.getNimgnew());
            }

            String nimgori = noticeImgFile.getOriginalFilename();
            String nimgnew = fileService.uploadFile(noticeImgLocation, nimgori, noticeImgFile.getBytes());
            String nimgurl = "/images/noticeImages/" + nimgnew;
            savedNoticeImg.updateNoticeImg(nimgori, nimgnew, nimgurl);
        }
    }
}
