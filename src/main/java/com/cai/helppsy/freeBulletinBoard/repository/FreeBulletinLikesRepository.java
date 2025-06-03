package com.cai.helppsy.freeBulletinBoard.repository;

import com.cai.helppsy.freeBulletinBoard.entity.FreeBulletinLikes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FreeBulletinLikesRepository extends JpaRepository<FreeBulletinLikes, Integer> {
    List<FreeBulletinLikes> findByFreeBulletin_noAndUserName(int no, String userName);
}
