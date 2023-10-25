package com.mall.twins.twinsmall.repository;

import com.mall.twins.twinsmall.constant.ItemSellStatus;
import com.mall.twins.twinsmall.entity.Item;
import com.mall.twins.twinsmall.entity.QItem;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("상품 저장 테스트")
    public void createItemTest(){
        Item item = new Item();
        item.setPname("테스트 상품");
        item.setPprice(10000);
        item.setPdesc("테스트 상품 상세 설명");
        item.setPstatus(ItemSellStatus.SELL);
        item.setPcate("TV");
        item.setPstock(100);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());
        Item savedItem = itemRepository.save(item);
        System.out.println(savedItem.toString());
    }

    public void createItemList(){
        for(int i=1;i<=10;i++){
            Item item = new Item();
            item.setPname("테스트 상품" + i);
            item.setPprice(10000 + i);
            item.setPdesc("테스트 상품 상세 설명" + i);
            item.setPstatus(ItemSellStatus.SELL);
            item.setPcate("TV");
            item.setPstock(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            Item savedItem = itemRepository.save(item);
        }
    }

    @Test
    @DisplayName("상품명 조회 테스트")
    public void findByItemNmTest(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByPname("테스트 상품1");
        for(Item item : itemList){
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("상품명, 상품상세설명 or 테스트")
    public void findByItemNmOrItemDetailTest(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByPnameOrPdesc("테스트 상품1", "테스트 상품 상세 설명5");
        for(Item item : itemList){
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("가격 LessThan 테스트")
    public void findByPriceLessThanTest(){
        // this.createItemList();
        List<Item> itemList = itemRepository.findByPpriceLessThan(10005);
        for(Item item : itemList){
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("가격 내림차순 조회 테스트")
    public void findByPriceLessThanOrderByPriceDesc(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByPpriceLessThanOrderByPpriceDesc(10005);
        for(Item item : itemList){
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("@Query를 이용한 상품 조회 테스트")
    public void findByItemDetailTest(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByPdesc("테스트 상품 상세 설명");
        for(Item item : itemList){
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("Querydsl 조회 테스트1")
    public void queryDslTest(){
        // this.createItemList();
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QItem qItem = QItem.item;
        JPAQuery<Item> query  = queryFactory.selectFrom(qItem)
                .where(qItem.pstatus.eq(ItemSellStatus.SELL))
                .where(qItem.pdesc.like("%" + "테스트 상품 상세 설명" + "%"))
                .orderBy(qItem.pprice.desc());

        List<Item> itemList = query.fetch();

        for(Item item : itemList){
            System.out.println(item.toString());
        }
    }

    public void createItemList2(){
        for(int i=1;i<=5;i++){
            Item item = new Item();
            item.setPname("테스트 상품" + i);
            item.setPprice(10000 + i);
            item.setPdesc("테스트 상품 상세 설명" + i);
            item.setPstatus(ItemSellStatus.SELL);
            item.setPcate("TV");
            item.setPstock(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            itemRepository.save(item);
        }

        for(int i=6;i<=10;i++){
            Item item = new Item();
            item.setPname("테스트 상품" + i);
            item.setPprice(10000 + i);
            item.setPdesc("테스트 상품 상세 설명" + i);
            item.setPstatus(ItemSellStatus.SOLD_OUT);
            item.setPcate("TV");
            item.setPstock(0);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            itemRepository.save(item);
        }
    }

    @Test
    @DisplayName("상품 Querydsl 조회 테스트 2")
    public void queryDslTest2(){

        this.createItemList2();

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QItem item = QItem.item;
        String pdesc = "테스트 상품 상세 설명";
        int pprice = 10003;
        String pstatus = "SELL";

        booleanBuilder.and(item.pdesc.like("%" + pdesc + "%"));
        booleanBuilder.and(item.pprice.gt(pprice));
        System.out.println(ItemSellStatus.SELL);
        if(StringUtils.equals(pstatus, ItemSellStatus.SELL)){
            booleanBuilder.and(item.pstatus.eq(ItemSellStatus.SELL));
        }

        Pageable pageable = PageRequest.of(0, 5);
        Page<Item> itemPagingResult = itemRepository.findAll(booleanBuilder, pageable);
        System.out.println("total elements : " + itemPagingResult. getTotalElements ());

        List<Item> resultItemList = itemPagingResult.getContent();
        for(Item resultItem: resultItemList){
            System.out.println(resultItem.toString());
        }
    }

}
