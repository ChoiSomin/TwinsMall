package com.mall.twins.twinsmall.dto;

import com.mall.twins.twinsmall.constant.ItemSellStatus;
import com.mall.twins.twinsmall.entity.Item;
import lombok.Data;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
public class ItemFormDto {

    private Long id;

    @NotBlank(message = "상품명은 필수 입력 값입니다.")
    private String pname; // 상품명

    @NotNull(message = "가격은 필수 입력 값입니다.")
    private int pprice; // 가격

    @NotNull(message = "재고는 필수 입력 값입니다.")
    private Integer pstock; // 재고

    @NotBlank(message = "카테고리는 필수 입력 값입니다.")
    private String pcate; // 카테고리

    @NotBlank(message = "상품 상세 설명은 필수 입력 값입니다.")
    private String pdesc; // 상품 상세 설명

    private ItemSellStatus pstatus; // 상품 판매 상태

    private List<ItemImgDto> itemImgDtoList = new ArrayList<>(); // 상품 저장 후 수정할 때 상품 이미지 정보를 저장하는 리스트

    private List<Long> itemImgIds = new ArrayList<>(); // 상품의 이미지 아이디를 저장하는 리스트

    private static ModelMapper modelMapper = new ModelMapper();

    public Item createItem() { // 엔티티 객체의 데이터를 복사하여 복사한 객체를 반환해줌
        return modelMapper.map(this, Item.class);
    }

    public static ItemFormDto of(Item item){ // dto 객체의 데이터를 복사하여 복사한 객체를 반환해줌
        return modelMapper.map(item, ItemFormDto.class);
    } // modelMapper 이용

    /*public static ItemFormDto of(Item item) {
        ItemFormDto dto = new ItemFormDto();
        dto.setId(item.getId()); // 변경한 내용
        dto.setItemNm(item.getTitle());
        dto.setItemDetail(item.getDetail());
        dto.setPrice(item.getPrice());
        dto.setGenre(item.getGenre());
        dto.setDeveloper(item.getDeveloper());
        dto.setItemSellStatus(item.getItemSellStatus());
        return dto;
    } // 직접 매핑*/
}
