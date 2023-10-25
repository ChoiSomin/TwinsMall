package com.mall.twins.twinsmall.entity;


import com.mall.twins.twinsmall.constant.ItemSellStatus;
import com.mall.twins.twinsmall.dto.ItemFormDto;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import java.time.LocalDate;

@Entity //클래스를 엔티티로 선언
@Table(name="item") //엔티티와 매핑할 테이블을 지정(테이블 명)
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Item extends BaseEntity {

    @Id
    @Column(name="pno")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false) // not null 설정 및 길이 지정, nullable = false : not null
    private String pname; //상품명

    @Column(nullable = false)
    private int pprice;    //가격

    @Column(nullable = false)
    private Integer pstock; // 재고

    @Column(nullable = false)
    private String pcate;  // 카테고리

    @Lob //BLOB, CLOB 타입 매핑
    // CLOB : 사이즈가 큰 테이터를 외부 파일로 저장하기 위한 데이터 타입 (문자형 대용량 파일 저장)
    // BLOB : 바이너리 데이터를 DB외부에 저장하기 위한 타입 (이미지, 사운드, 비디오 : 멀티미디어)
    @Column(nullable = false, length = 5000)
    private String pdesc; // 상품 상세 설명

    @Enumerated(EnumType.STRING)  // enum 타입 매핑
    private ItemSellStatus pstatus; //상품 판매 상태

    public void updateItem(ItemFormDto itemFormDto){
        this.pname = itemFormDto.getPname();
        this.pprice = itemFormDto.getPprice();
        this.pcate = itemFormDto.getPcate();
        this.pstock = itemFormDto.getPstock();
        this.pdesc = itemFormDto.getPdesc();
        this.pstatus = itemFormDto.getPstatus();
    }
}