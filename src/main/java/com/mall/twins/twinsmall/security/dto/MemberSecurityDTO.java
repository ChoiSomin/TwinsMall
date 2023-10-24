package com.mall.twins.twinsmall.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
@ToString
public class MemberSecurityDTO extends User {

    private String mid;
    private String mpw;
    private String memail;
    private String mname;
    private String mbirth;
    private String mphone;
    private boolean mdel;
    private boolean msocial;

    public MemberSecurityDTO(String username, String password, String email, boolean del, boolean social,
                             Collection<? extends GrantedAuthority> authorities){
        super(username, password, authorities);
                this.mid = username;
                this.mpw = password;
                this.memail = email;
                this.mdel = del;
                this.msocial = social;
    }
}
