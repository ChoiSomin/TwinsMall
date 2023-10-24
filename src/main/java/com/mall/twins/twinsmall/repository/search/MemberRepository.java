package com.mall.twins.twinsmall.repository.search;

import com.mall.twins.twinsmall.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    /* Role을 같이 로딩 */
    @EntityGraph(attributePaths = "roleset")
    @Query("select m from Member m where m.mid = :mid and m.msocial = false")
    //직접 로그인할 때는 소셜 서비스를 통해서 회원가입된 회원들이 같은 패스워드를 가지므로
    //일반 회원들만 가져오도록 social값이 false인 사용자들만을 대상으로 처리
    abstract Optional<Member> getWithRoles(String mid);

}
