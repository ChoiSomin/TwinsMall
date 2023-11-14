package com.mall.twins.twinsmall.config.auth;

import com.mall.twins.twinsmall.entity.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Getter
public class CustomUserDetails implements UserDetails, OAuth2User {

    private Member member;
    private Map<String, Object> attribute;

    /* 일반 로그인 생성자 */
    public CustomUserDetails(Member member){
        this.member = member;
    }

    /* OAuth2 로그인 사용자 */
    public CustomUserDetails(Member member, Map<String, Object> attributes) {
        this.member = member;
        this.attribute = attributes;
    }

    /* 유저의 권한 목록, 권한 반환 */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return member.getRole().getValue();
            }
        });
        return collect;
    }

    @Override
    public String getPassword() {
        return member.getMpw();
    }

    @Override
    public String getUsername() {
        return member.getMid();
    }

    /* 계정 만료 여부 : 만료 */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /* 계정 잠김 여부 : 잠기지 않음 */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /* 비밀번호 만료 여부 : 만료 */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /* 사용자 활성화 여부 : 활성화 됨 */
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }
}
