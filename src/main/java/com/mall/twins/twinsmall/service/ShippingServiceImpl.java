package com.mall.twins.twinsmall.service;

import com.mall.twins.twinsmall.dto.ShippingDto;
import com.mall.twins.twinsmall.entity.Shipping;
import com.mall.twins.twinsmall.repository.ShippingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class ShippingServiceImpl implements ShippingService {

    private final ModelMapper modelMapper;                  // 엔티티(Board)와 DTO(BoardDTO) 간의 변환
    private final ShippingRepository shippingRepository;    // 엔티티 객체 연결


    // 등록
    @Override
    public Long register(ShippingDto ShippingDTO) {

        Shipping shipping = modelMapper.map(ShippingDTO, Shipping.class);
        Long sno = shippingRepository.save(shipping).getSno();

        return sno;
    }

    // 조회 -> mid(사용자 아이디)로 배송 주소 DTO 전체 리스트 리턴
    @Override
    public List<ShippingDto> readAll(String mid) {

        List<Shipping> shippingList = shippingRepository.listOfShipping(mid);

        List<ShippingDto> shippingDTOList = shippingList.stream()
                .map(data -> modelMapper.map(data, ShippingDto.class))
                .collect(Collectors.toList());

        return shippingDTOList;
    }

    @Override
    public ShippingDto readOne(Long sno) {

        Optional<Shipping> result = shippingRepository.findBySno(sno);
        Shipping shipping = result.orElseThrow();
        ShippingDto shippingDTO = modelMapper.map(shipping, ShippingDto.class);

        return shippingDTO;
    }

    @Override
    public void modify(ShippingDto shippingDTO) {

        Optional<Shipping> result = shippingRepository.findBySno(shippingDTO.getSno());
        Shipping shipping = result.orElseThrow();
        shipping.change(shippingDTO.getSname(), shippingDTO.getSperson(), shippingDTO.getZonecode(), shippingDTO.getAddress(), shippingDTO.getSaddress(), shippingDTO.getSphone(), shippingDTO.getSdefault());
        shippingRepository.save(shipping);

    }

    @Override
    public void remove(Long sno) {

        shippingRepository.deleteById(sno);

    }

    @Override
    public void modifySdefault(String mid) {

        shippingRepository.updateSdefault(mid);

    }
}
