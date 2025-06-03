package com.cai.helppsy.freeBulletinBoard.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
public class ForeignKeyNo {
    private FreeBulletin freeBulletin;
    private FreeBulletinComment freeBulletinComment;
    private FreeBulletinCommentInComment freeBulletinCommentInComment;
}
