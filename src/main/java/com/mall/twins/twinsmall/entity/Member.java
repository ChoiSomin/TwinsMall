package com.mall.twins.twinsmall.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Member {

    @Id
    private Long mno;

    private String mid;

    private String mpw;

    private String mname;

    private String memail;

    private String mphone;

    private Date mbrith;


}
