package com.mall.twins.twinsmall.service;

import com.mall.twins.twinsmall.constant.ItemSellStatus;
import com.mall.twins.twinsmall.dto.ItemFormDto;
import com.mall.twins.twinsmall.entity.Item;
import com.mall.twins.twinsmall.entity.ItemImg;
import com.mall.twins.twinsmall.repository.ItemImgRepository;
import com.mall.twins.twinsmall.repository.ItemRepository;
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
public class ItemServiceTest {

    @Autowired
    ItemService itemService;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemImgRepository itemImgRepository;

    List<MultipartFile> createMultipartFiles() throws Exception{

        List<MultipartFile> multipartFileList = new ArrayList<>();

        for(int i=0;i<5;i++){
            String path = "D:/upload/productImages/";
            String iimgnew = "image" + i + ".jpg";
            MockMultipartFile multipartFile =
                    new MockMultipartFile(path, iimgnew, "image/jpg", new byte[]{1,2,3,4});
            multipartFileList.add(multipartFile);
        }

        return multipartFileList;
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void saveItem() throws Exception {
        ItemFormDto itemFormDto = new ItemFormDto();
        itemFormDto.setPname("테스트상품");
        itemFormDto.setPstatus(ItemSellStatus.SELL);
        itemFormDto.setPdesc("테스트 상품 입니다.");
        itemFormDto.setPcate("TV");
        itemFormDto.setPprice(1000);
        itemFormDto.setPstock(100);

        List<MultipartFile> multipartFileList = createMultipartFiles();
        Long ItemId = itemService.saveItem(itemFormDto, multipartFileList);

        System.out.println(ItemId);

        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(ItemId);

        Item item = itemRepository.findById(ItemId)
                .orElseThrow(EntityNotFoundException::new);

        assertEquals(itemFormDto.getPname(), item.getPname());
        assertEquals(itemFormDto.getPstatus(), item.getPstatus());
        assertEquals(itemFormDto.getPdesc(), item.getPdesc());
        assertEquals(itemFormDto.getPprice(), item.getPprice());
        assertEquals(itemFormDto.getPstock(), item.getPstock());
        assertEquals(itemFormDto.getPcate(), item.getPcate());
        assertEquals(multipartFileList.get(0).getOriginalFilename(), itemImgList.get(0).getIimgori());
    }
}
