package com.cai.helppsy.freeBulletinBoard.repository;

import com.cai.helppsy.freeBulletinBoard.entity.FreeBulletinCommentInComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FreeBulletinCommentInCommentRepository extends JpaRepository<FreeBulletinCommentInComment, Integer> {
    List<FreeBulletinCommentInComment> findByFreeBulletin_no(Integer no);
    List<FreeBulletinCommentInComment> findByFreeBulletinComment_no(Integer no);
    List<FreeBulletinCommentInComment> findByNo(Integer no);
}
