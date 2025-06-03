package com.cai.helppsy.freeBulletinBoard.serviece;

import com.cai.helppsy.freeBulletinBoard.entity.*;
import com.cai.helppsy.freeBulletinBoard.repository.*;
import lombok.RequiredArgsConstructor;
import org.hibernate.cache.spi.support.EntityReadOnlyAccess;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class FreeBulletinBoardService {
    private final FreeBulletinRepository freeBulletinRepository;
    private final FreeBulletinAttachRepository freeBulletinAttachRepository;
    private final FreeBulletinImageAttachRepository imageAttachRepository;
    private final FreeBulletinCommentRepository freeBulletinCommentRepository;
    private final FreeBulletinCommentInCommentRepository freeBulletinCommentInCommentRepository;
    private final FreeBulletinLikesRepository freeBulletinLikesRepository;

    public FreeBulletin bulletinOne(int no) {
        return freeBulletinRepository.findById(no).get();
    }

    public void increaseViews(int no) {
        FreeBulletin freeBulletin = freeBulletinRepository.findById(no).get();
        freeBulletin.setViews(freeBulletin.getViews() + 1);
        freeBulletinRepository.saveAndFlush(freeBulletin); // flush는 변경사항의 동기화를 의미한다.
    }

    public List<FreeBulletin> bulletinList() {
        return freeBulletinRepository.findAll();
    }

    public void addBulletin(FreeBulletin bulletinEntity, MultipartFile[] files, MultipartFile imgFile
            , String crrentUserName) {

        LocalDateTime createDate = LocalDateTime.now();
        bulletinEntity.setCreateDate(createDate);
        bulletinEntity.setViews(0);
        bulletinEntity.setWriter(crrentUserName);
        freeBulletinRepository.save(bulletinEntity);


        FreeBulletinAttach attachEntity;
        String originFileName;
        UUID uuid;
        String fileName;
        File savingInfo;
        String attachFileStoragePath = System.getProperty("user.dir") + "/files/freeBulletin";
        int max = 0;

        //첨부파일 등록
        if (files != null) {
            for (MultipartFile mf : files) {
                uuid = UUID.randomUUID();
                fileName = uuid.toString() + "_" + mf.getOriginalFilename();
                savingInfo = new File(attachFileStoragePath, fileName);
                // 위에는 파일의 저장 위치와 파일의 이름을 저장
                try {
                    mf.transferTo(savingInfo);
                } catch (Exception e) {
                    System.out.println("_____________________________");
                    System.out.println("파일 저장을 실패하였습니다.");
                    System.out.println("_____________________________");
                }
                attachEntity = new FreeBulletinAttach();
                attachEntity.setFileName(fileName);
                attachEntity.setCreateDate(createDate);
                attachEntity.setFreeBulletin(bulletinEntity);

                freeBulletinAttachRepository.save(attachEntity);
            }
        }
        //이미지 파일 업로드
        uuid = UUID.randomUUID();
        fileName = uuid.toString() + "_" + imgFile.getOriginalFilename();
        savingInfo = new File(attachFileStoragePath, fileName);

        try {
            imgFile.transferTo(savingInfo);
        } catch (Exception e) {
            System.out.println("_____________________________");
            System.out.println("이미지 파일 저장을 실패하였습니다.");
            System.out.println("_____________________________");
        }

        FreeBulletinImageAttach imgEntity = new FreeBulletinImageAttach();
        imgEntity.setImgName(fileName);
        imgEntity.setCreateDate(createDate);
        imgEntity.setFreeBulletin(bulletinEntity);

        imageAttachRepository.save(imgEntity);
    }

    public String findImgName(Integer no) {
        return imageAttachRepository.findByFreeBulletin_no(no).getImgName();
    }

    // 댓글

    public void addComment(FreeBulletinComment freeBulletinComment, int fkNo) {
        freeBulletinComment.setFreeBulletin(freeBulletinRepository.findById(fkNo).get());
        freeBulletinComment.setCreateDate(LocalDateTime.now());
        freeBulletinCommentRepository.save(freeBulletinComment);
    }

    public List<FreeBulletinComment> getComments(int fkNo) {
        List<FreeBulletinComment> cList = freeBulletinCommentRepository.findByFreeBulletin_no(fkNo);
        return cList;
    }

    // 대댓글

    public void addCommentInComment(FreeBulletinCommentInComment freeBulletinCommentInComment, int fkNo) {
        freeBulletinCommentInComment.setFreeBulletinComment(freeBulletinCommentRepository.findById(fkNo).get());
        freeBulletinCommentInComment.setCreateDate(LocalDateTime.now());
        freeBulletinCommentInCommentRepository.save(freeBulletinCommentInComment);
    }

    public FreeBulletinCommentInComment[] getCommentInComments(int no) {
        return freeBulletinCommentInCommentRepository.findByFreeBulletinComment_no(no);
    }

    // 좋아요

    public void likesUp(FreeBulletinLikes freeBulletinLikes, int fkNo) {

        // 게시글 테이블의 튜플에 좋아요 갱신
        FreeBulletin bulletin = freeBulletinRepository.findById(fkNo).get();
        bulletin.setLikes(bulletin.getLikes() + 1);
        freeBulletinRepository.saveAndFlush(bulletin);
        // 외래키 지정
        freeBulletinLikes.setFreeBulletin(bulletin);


        // 좋아요 테이블의 튜플에 insert
        freeBulletinLikesRepository.save(freeBulletinLikes);
    }

    // 해당 게시물에 좋아요를 눌렀었는지 여부
    public int isPressedLike(String userName, int no) {
        List<FreeBulletinLikes> freeBulletinLikes = freeBulletinLikesRepository.findByFreeBulletin_noAndUserName(no, userName);
        System.out.println(userName);
        System.out.println(no);
        System.out.println("---------------");
        System.out.println(freeBulletinLikes);
        System.out.println("---------------");

        if (freeBulletinLikes != null)
            return 1;
        else
            return 0;

    }
}
