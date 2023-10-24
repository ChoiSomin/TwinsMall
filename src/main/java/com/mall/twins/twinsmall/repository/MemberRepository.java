package com.mall.twins.twinsmall.repository;

import com.mall.twins.twinsmall.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
