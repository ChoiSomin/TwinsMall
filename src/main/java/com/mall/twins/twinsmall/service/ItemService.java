package com.mall.twins.twinsmall.service;

import com.mall.twins.twinsmall.dto.ItemFormDto;
import com.mall.twins.twinsmall.dto.ItemImgDto;
import com.mall.twins.twinsmall.dto.ItemSearchDto;
import com.mall.twins.twinsmall.dto.MainItemDto;
import com.mall.twins.twinsmall.entity.Item;
import com.mall.twins.twinsmall.entity.ItemImg;
import com.mall.twins.twinsmall.repository.ItemImgRepository;
import com.mall.twins.twinsmall.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemImgService itemImgService;
    private final ItemImgRepository itemImgRepository;

    public Long saveItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception{

        // 상품 등록
        Item item = itemFormDto.createItem();
        System.out.println("Item Detail: " + item.getPdesc()); // 로그 출력
        itemRepository.save(item);

        // 이미지 등록
        for (int i = 0; i < itemImgFileList.size(); i++) {
            ItemImg itemImg = new ItemImg();
            itemImg.setItem(item);
            if(i == 0)
                itemImg.setIimgrep("Y");
            else
                itemImg.setIimgrep("N");

            itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));
        }

        return item.getId();
    }


    @Transactional(readOnly = true)
    public ItemFormDto getItemDtl(Long ItemId){
        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(ItemId); // 해당 상품의 이미지 조회

        List<ItemImgDto> itemImgDtoList = new ArrayList<>();

        for(ItemImg itemImg : itemImgList){ // 조회한 ItemImg 엔티티를 ItemImgDto 객체로 만들어서 리스트에 추가
            ItemImgDto itemImgDto = ItemImgDto.of(itemImg);
            itemImgDtoList.add(itemImgDto);
        }

        Item item = itemRepository.findById(ItemId).orElseThrow(EntityNotFoundException::new); // 상품의 아이디를 통해 상품 엔티티를 조회

        ItemFormDto itemFormDto = ItemFormDto.of(item);
        itemFormDto.setItemImgDtoList(itemImgDtoList);

        return itemFormDto;
    }


    public Long updateItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception{
        //상품 수정
        Item item = itemRepository.findById(itemFormDto.getId())
                .orElseThrow(EntityNotFoundException::new);
        item.updateItem(itemFormDto);
        List<Long> itemImgIds = itemFormDto.getItemImgIds();

        //이미지 등록
        for(int i=0;i<itemImgFileList.size();i++){
            itemImgService.updateItemImg(itemImgIds.get(i),
                    itemImgFileList.get(i));
        }

        return item.getId();
    }

    @Transactional(readOnly = true)
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable){
        return itemRepository.getAdminItemPage(itemSearchDto, pageable);
    }

    @Transactional(readOnly = true)
    public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable){
        log.info("getMainItemPage method is called with itemSearchDto: {}", itemSearchDto);
        return itemRepository.getMainItemPage(itemSearchDto, pageable);
    }

}
