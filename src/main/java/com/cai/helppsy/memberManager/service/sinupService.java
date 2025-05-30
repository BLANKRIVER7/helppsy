package com.cai.helppsy.memberManager.service;



import com.cai.helppsy.memberManager.entity.SinupEntity;
import com.cai.helppsy.memberManager.repository.sinupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class sinupService {

    private final sinupRepository sinuprepository;


    public void sinup(SinupEntity sinupentity){ // 회원가입
        sinuprepository.save(sinupentity);
    }

   public SinupEntity login(String userId){
        return sinuprepository.findByuserId(userId);
   }


}
