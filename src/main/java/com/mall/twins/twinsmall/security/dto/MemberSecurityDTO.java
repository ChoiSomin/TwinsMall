package com.mall.twins.twinsmall.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Getter
@Setter
@ToString
public class MemberSecurityDTO extends User implements OAuth2User {

    private String mid;
    private String mpw;
    private String mname;
    private String memail;
    private String mbirth;
    private String mphone;
    private Boolean mdel;
    private Boolean msocial;
    private Map<String, Object> props;  // 소셜 로그인 정보

    public MemberSecurityDTO(String mid, String mpw, String mname, String memail,
                             String mbirth, String mphone, Boolean mdel, Boolean msocial,
                             Collection<? extends GrantedAuthority> authorities) {

        super(mid, mpw, authorities == null ? Collections.emptyList() : authorities);

        this.mid = mid;
        this.mpw = mpw;
        this.mname = mname;
        this.memail = memail;
        this.mbirth = mbirth;
        this.mphone = mphone;
        this.mdel = mdel;
        this.msocial = msocial;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.props;
    }

    @Override
    public String getName() {
        return this.mid;
    }
}
