package com.mall.twins.twinsmall.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table (name = "persistentlogin")
public class PersistentLogin {
    
    @Id
    @Column (length = 64)
    private String series;

    @Column (length = 64, nullable = false)
    private String username;

    @Column (length = 64, nullable = false)
    private String token;

    @Column (length = 64, nullable = false)
    private Timestamp lastused; // 생성 시간 자동 기록
}
