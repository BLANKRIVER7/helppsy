package com.cai.helppsy.freeBulletinBoard.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentInCommentDTO {
    private int no;

    private String type;

    private String writer;

    private String content;

    private int likes;

    private LocalDateTime createDate;

    private int fkNo;

    public CommentInCommentDTO(){

    }

    public CommentInCommentDTO(int no, String type, String writer, String content, int likes, LocalDateTime createDate){
        this.no = no;
        this.type = type;
        this.writer = writer;
        this.content = content;
        this.likes = likes;
        this.createDate = createDate;
    }
}
