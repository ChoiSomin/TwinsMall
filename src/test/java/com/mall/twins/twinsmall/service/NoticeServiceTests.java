package com.mall.twins.twinsmall.service;

import com.mall.twins.twinsmall.dto.NoticeFormDto;
import com.mall.twins.twinsmall.entity.Notice;
import com.mall.twins.twinsmall.repository.NoticeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class NoticeServiceTests {

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

    @Test
    void saveNotice() throws Exception {
        NoticeFormDto noticeFormDto = new NoticeFormDto();
        noticeFormDto.setNtitle("test title");
        noticeFormDto.setNcontent("test tcontent");

    }


}
