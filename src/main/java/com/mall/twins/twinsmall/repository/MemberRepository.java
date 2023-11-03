package com.mall.twins.twinsmall.repository;

import com.mall.twins.twinsmall.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {

    Member findByMid(String mid);

    Optional<Member> findByMemail(String memail);

    @Query("select m from Member m where m.mid = :mid and m.msocial = false ")
    Optional<Member> getWithRoles(String mid);
}
