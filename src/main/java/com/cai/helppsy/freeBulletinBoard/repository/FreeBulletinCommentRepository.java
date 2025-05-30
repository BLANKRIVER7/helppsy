package com.cai.helppsy.freeBulletinBoard.repository;

import com.cai.helppsy.freeBulletinBoard.entity.FreeBulletin;
import com.cai.helppsy.freeBulletinBoard.entity.FreeBulletinComment;
import com.cai.helppsy.freeBulletinBoard.entity.FreeBulletinLikes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FreeBulletinCommentRepository  extends JpaRepository<FreeBulletinComment, Integer> {
    List<FreeBulletinComment> findByFreeBulletin_no(Integer no);
}
