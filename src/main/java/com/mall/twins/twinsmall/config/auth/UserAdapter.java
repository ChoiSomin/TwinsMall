package com.mall.twins.twinsmall.config.auth;

import com.mall.twins.twinsmall.entity.Member;
import lombok.Getter;

import java.util.Map;

@Getter
public class UserAdapter extends CustomUserDetails{

    private Member member;
    private Map<String, Object> attributes;

    public UserAdapter(Member member){
        super(member);
        this.member = member;
    }

    public UserAdapter(Member member, Map<String, Object> attributes){
        super(member, attributes);
        this.member = member;
        this.attributes = attributes;
    }
}
