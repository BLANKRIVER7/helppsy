package com.cai.helppsy.memberManager.repository;


import com.cai.helppsy.memberManager.entity.SinupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface sinupRepository extends JpaRepository<SinupEntity,String> {

    SinupEntity findByuserId(String userId);

}
