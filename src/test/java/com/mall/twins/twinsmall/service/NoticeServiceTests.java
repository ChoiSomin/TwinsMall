package com.mall.twins.twinsmall.service;

import com.mall.twins.twinsmall.dto.NoticeFormDto;
import com.mall.twins.twinsmall.dto.PageRequestDTO;
import com.mall.twins.twinsmall.dto.PageResultDTO;
import com.mall.twins.twinsmall.entity.Notice;

import com.mall.twins.twinsmall.repository.NoticeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class NoticeServiceTests {

    @Autowired
    NoticeServiceImpl noticeServiceImpl;

    @Autowired
    NoticeService noticeService;

    @Autowired
    NoticeRepository noticeRepository;

    /*@Autowired
    NoticeImgRepository noticeImgRepository;*/

    List<MultipartFile> createMultipartFiles() throws Exception{

        List<MultipartFile> multipartFileList = new ArrayList<>();

        for(int i=0;i<5;i++){
            String path = "D:/upload/productImages/";
            String nimgnew = "image" + i + ".jpg";
            MockMultipartFile multipartFile =
                    new MockMultipartFile(path, nimgnew, "image/jpg", new byte[]{1,2,3,4});
            multipartFileList.add(multipartFile);
        }

        return multipartFileList;
    }

    /*@Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void saveNotice() throws Exception {
        NoticeFormDto noticeFormDto = new NoticeFormDto();
        noticeFormDto.setNtitle("test title");
        noticeFormDto.setNcontent("test tcontent");

        List<MultipartFile> multipartFileList = createMultipartFiles();
        Long NoticeNid = noticeServiceImpl.saveNotice(noticeFormDto, multipartFileList);

        System.out.println(NoticeNid);

        Notice notice = noticeRepository.findById(NoticeNid)
                .orElseThrow(EntityNotFoundException::new);

        assertEquals(noticeFormDto.getNtitle(), notice.getNtitle());
        assertEquals(noticeFormDto.getNcontent(), notice.getNcontent());
        assertEquals(multipartFileList.get(0).getOriginalFilename(), noticeImgList.get(0).getNimgori());
    }*/

    @Test
    public void testList() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).build();

        PageResultDTO<NoticeFormDto, Notice> resultDTO = noticeService.getNoticeList(pageRequestDTO);

        for(NoticeFormDto noticeFormDto : resultDTO.getDtoList()){
            System.out.println(noticeFormDto);
        }
    }

}
