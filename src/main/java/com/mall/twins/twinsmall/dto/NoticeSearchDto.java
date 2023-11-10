package com.mall.twins.twinsmall.dto;

import com.mall.twins.twinsmall.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Setter
public class NoticeSearchDto {

    private String type;

    private String keyword;


}
