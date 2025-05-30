package com.cai.helppsy.freeBulletinBoard.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class FreeBulletinCommentInComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int no;

    @Column(length = 10)
    private String type;

    @Column(length = 15)
    private String writer;

    @Column(length = 400)
    private String content;

    @Column(length = 9)
    private int likes;

    private LocalDateTime createDate;

    @ManyToOne
    @JoinColumn(name = "fk_bulletin_no")
    private FreeBulletin freeBulletin;

    @ManyToOne
    @JoinColumn(name = "fk_comment_no")
    private FreeBulletinComment freeBulletinComment;
}
