package com.cai.helppsy.freeBulletinBoard.serviece;

import com.cai.helppsy.freeBulletinBoard.entity.AttachEntity;
import com.cai.helppsy.freeBulletinBoard.entity.BulletinEntity;
import com.cai.helppsy.freeBulletinBoard.repository.AttachRepository;
import com.cai.helppsy.freeBulletinBoard.repository.BulletinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class FreeBulletinBoardService {
    private final AttachRepository attachRepository;
    private final BulletinRepository bulletinRepository;

    public void addBulletin(BulletinEntity bulletinEntity, MultipartFile[] files){
        if (files != null){
            for (MultipartFile f : files) {
                System.out.println(f.getSize());
                System.out.println(f.getOriginalFilename());
            }
        }
        bulletinEntity.setCreateDaate(LocalDateTime.now());
        bulletinRepository.save(bulletinEntity);

        AttachEntity attachEntity;
        String originFileName;
        UUID uuid;
        String fileName;
        File saveFile;
        String attachFileStoragePath = System.getProperty("user.dir") + "/src/main/resources/static/attachFiles";

        for(MultipartFile mf : files){
            uuid = UUID.randomUUID();
            fileName = uuid.toString() +"_"+ mf.getOriginalFilename();
            saveFile = new File(attachFileStoragePath,fileName);
            try {
                mf.transferTo(saveFile);
            }catch(Exception e) {
                e.printStackTrace();
            }
            attachEntity = new AttachEntity();
            attachEntity.setFileName(fileName);
            attachEntity.setCreateDaate(LocalDateTime.now());
            // bulletinEntity의 no 컬럼에서 최댓값 가져오기
            List<BulletinEntity> bList =  bulletinRepository.findAll();
            int max = 0;
            for(int i = 0; i < bList.size(); i++){
                if(bList.get(i).getNo() > max){
                    max = bList.get(i).getNo();
                }
            }

            attachEntity.setNo(max);

            attachRepository.save(attachEntity);
        }
    }
}
