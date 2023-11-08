package com.mall.twins.twinsmall.service;

import com.mall.twins.twinsmall.dto.ShippingDto;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Log4j2
public class ShippingServiceTests {

    @Autowired
    private ShippingService shippingService;

    @Test
    public void testReadAll() {

        List<ShippingDto> shippingDTOList = shippingService.readAll("member10");

        log.info(shippingDTOList);
    }

    @Test
    public void testReadOne() {

        ShippingDto shippingDTO = shippingService.readOne(1L);

        log.info(shippingDTO);
    }
}
