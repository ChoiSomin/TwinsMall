package com.mall.twins.twinsmall.repository;

import com.mall.twins.twinsmall.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String>, QuerydslPredicateExecutor<Member> {

    // 로그인 시에 MemberRole을 같이 로딩
    @EntityGraph(attributePaths = "roleSet")    // 연관 관계가 지연로딩일 때도 엔티티 바로 조회
    @Query("SELECT m FROM Member m WHERE m.mid = :mid AND m.msocial = false")
    Optional<Member> getWithRoles(String mid);

    // 이메일로 회원 정보 조회
    @EntityGraph(attributePaths = "roleSet")
    <Member>
    Optional<Member> findByMemail(String memail);

    // 소셜 로그인으로 로그인 시 비밀번호 변경 기능 (기본값 1111)
    @Modifying  // DML(insert/update/delete) 처리 가능
    @Transactional
    @Query("UPDATE Member m SET m.mpw = :mpw WHERE m.mid = :mid")
    void updatePassword(@Param("mid") String mid, @Param("mpw") String mpw);


}
