package com.mall.twins.twinsmall.repository;

import com.mall.twins.twinsmall.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {

    @EntityGraph(attributePaths = "roleSet")    // 연관 관계가 지연로딩일 때도 엔티티 바로 조회
    @Query("SELECT m FROM Member m WHERE m.mid = :mid AND m.msocial = false")
        Optional<Member> getWithRoles(String mid);

    @EntityGraph(attributePaths = "roleSet")
    Optional<Member> findByMemail(String memail);

    @EntityGraph(attributePaths = "roleSet")
    Optional<Member> findByMphone(String mphone);

    /** Security - username이 DB에 존재하는지 확인 **/
    @EntityGraph(attributePaths = "roleSet")
    Member findByMid(String mid);

    @Query("SELECT COALESCE(m.mid, 0) FROM Member m WHERE m.mname = :mname AND m.mphone = :mphone")
    String searchId(@Param("mname")String mname, @Param("mphone") String mphone);

    /**
     * 유효성 검사 - 중복 체크
     * 중복 : true
     * 중복이 아닌 경우 : false
     */
    /*boolean existsByPhone(String mphone);
    boolean existsByEmail(String memail);*/
}
