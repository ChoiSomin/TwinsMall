package com.mall.twins.twinsmall.service;

import com.mall.twins.twinsmall.dto.NoticeFormDto;
import com.mall.twins.twinsmall.dto.NoticeImgDto;
import com.mall.twins.twinsmall.entity.Notice;
import com.mall.twins.twinsmall.entity.NoticeImage;
import com.mall.twins.twinsmall.repository.NoticeImgRepository;
import com.mall.twins.twinsmall.repository.NoticeRepository;
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
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final NoticeImgService noticeImgService;
    private final NoticeImgRepository noticeImgRepository;

    public Long saveNotice(NoticeFormDto noticeFormDto, List<MultipartFile> noticeImgFileList) throws Exception{

        // 등록
        Notice notice = noticeFormDto.cerateNotice();
        System.out.println("Notice Detail: " + notice.getNcontent()); // 로그 출력
        System.out.println("Notice Detail: " + notice.getNtitle());
        noticeRepository.save(notice);

        // 이미지 등록
        for (int i = 0; i < noticeImgFileList.size(); i++) {
            NoticeImage noticeImg = new NoticeImage();
            noticeImg.setNotice(notice);
            if(i == 0)
                noticeImg.setNimgrep("Y");
            else
                noticeImg.setNimgrep("N");

            noticeImgService.saveNoticeImg(noticeImg, noticeImgFileList.get(i));
        }

        return notice.getNid();

    }


    @Transactional(readOnly = true)
    public NoticeFormDto getNoticeDtl(Long NoticeNid){
        List<NoticeImage> noticeImgList = noticeImgRepository.findByNoticeNidOrderByNidAsc(NoticeNid); // 해당 상품의 이미지 조회

        List<NoticeImgDto> noticeImgDtoList = new ArrayList<>();

        for(NoticeImage noticeImg : noticeImgList){ // 조회한 ItemImg 엔티티를 ItemImgDto 객체로 만들어서 리스트에 추가
            NoticeImgDto noticeImgDto = NoticeImgDto.of(noticeImg);
            noticeImgDtoList.add(noticeImgDto);
        }

        Notice notice = noticeRepository.findById(NoticeNid).orElseThrow(EntityNotFoundException::new); // 상품의 아이디를 통해 상품 엔티티를 조회

        NoticeFormDto noticeFormDto = NoticeFormDto.of(notice);
        noticeFormDto.setNoticeImgDtoList(noticeImgDtoList);

        return noticeFormDto;
    }


    public Long updateNotice(NoticeFormDto noticeFormDto, List<MultipartFile> noticeImgFileList) throws Exception{
        //상품 수정
        Notice notice = noticeRepository.findById(noticeFormDto.getNno())
                .orElseThrow(EntityNotFoundException::new);
        notice.updateNotice(noticeFormDto);
        List<Long> noticeImgIds = noticeFormDto.getNoticeImgIds();

        //이미지 등록
        for(int i=0;i<noticeImgFileList.size();i++){
            noticeImgService.updateItemImg(noticeImgIds.get(i),
                    noticeImgFileList.get(i));
        }

        return notice.getNid();
    }
/*
    @Transactional(readOnly = true)
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable){
        return itemRepository.getAdminItemPage(itemSearchDto, pageable);
    }

    @Transactional(readOnly = true)
    public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable){
        return itemRepository.getMainItemPage(itemSearchDto, pageable);
    }*/

}
