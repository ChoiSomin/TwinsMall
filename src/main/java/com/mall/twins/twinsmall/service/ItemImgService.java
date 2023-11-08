package com.mall.twins.twinsmall.service;

import com.mall.twins.twinsmall.entity.ItemImg;
import com.mall.twins.twinsmall.repository.ItemImgRepository;
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
public class ItemImgService {

    @Value("${itemImgLocation}")
    private String itemImgLocation;

    private final ItemImgRepository itemImgRepository;

    private final FileService fileService;

    public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws Exception{
        String iimgori = itemImgFile.getOriginalFilename();
        String iimgnew = "";
        String iimgurl = "";

        //파일 업로드
        if(!StringUtils.isEmpty(iimgori)){
            iimgnew = fileService.uploadFile(itemImgLocation, iimgori,
                    itemImgFile.getBytes());
            iimgurl = "/images/productImages/" + iimgnew;
        }

        //상품 이미지 정보 저장
        itemImg.updateItemImg(iimgori, iimgnew, iimgurl);
        itemImgRepository.save(itemImg);
    }

    public void updateItemImg(Long itemImgId, MultipartFile itemImgFile) throws Exception{
        if(!itemImgFile.isEmpty()) { // 상품 이미지를 수정한 경우 상품 이미지를 업데이트
            ItemImg savedItemImg = itemImgRepository.findById(itemImgId).orElseThrow(EntityNotFoundException::new);

            //기존 이미지 파일 삭제
            if(!StringUtils.isEmpty(savedItemImg.getIimgnew())) {
                fileService.deleteFile(itemImgLocation+"/"+
                        savedItemImg.getIimgnew());
            }

            String iimgori = itemImgFile.getOriginalFilename();
            String iimgnew = fileService.uploadFile(itemImgLocation, iimgori, itemImgFile.getBytes());
            String iimgurl = "/images/productImages/" + iimgnew;
            savedItemImg.updateItemImg(iimgori, iimgnew, iimgurl);
        }
    }
}
