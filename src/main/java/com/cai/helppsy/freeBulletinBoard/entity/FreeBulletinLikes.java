package com.cai.helppsy.freeBulletinBoard.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import jakarta.persistence.*;


@Entity
@Data
public class FreeBulletinLikes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int no;

//    private String fkNo;

    @Column(length = 10)
    private String type;

    @Column(length = 15)
    private String writer;

    @ManyToOne
    @JoinColumn(name = "fk_no")
    private FreeBulletin freeBulletin;
}
