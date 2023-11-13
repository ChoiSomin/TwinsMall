package com.mall.twins.twinsmall.repository;

import com.mall.twins.twinsmall.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {

    @EntityGraph(attributePaths = "roleSet")    // 연관 관계가 지연로딩일 때도 엔티티 바로 조회
    @Query("SELECT m FROM Member m WHERE m.mid = :mid AND m.msocial = false")
        Optional<Member> getWithRoles(String mid);

    @EntityGraph(attributePaths = "roleSet")
    Optional<Member> findByMemail(String memail);

    @EntityGraph(attributePaths = "roleSet")
    Member findByMid(String mid);

    /**
     * 유효성 검사 - 중복 체크
     * 중복 : true
     * 중복이 아닌 경우 : false
     */
    boolean existsByPhone(String mphone);
    boolean existsByEmail(String memail);
}
