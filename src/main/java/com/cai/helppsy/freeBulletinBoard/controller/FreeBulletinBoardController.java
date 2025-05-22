package com.cai.helppsy.freeBulletinBoard.controller;

import com.cai.helppsy.freeBulletinBoard.entity.BulletinEntity;
import com.cai.helppsy.freeBulletinBoard.serviece.FreeBulletinBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Controller
public class FreeBulletinBoardController {
    private final FreeBulletinBoardService freeBulletinBoardService;

    @GetMapping("/")
    public String main() {
        return "freeBulletinBoard/newFreeBulletin";
    }

    @PostMapping("addFreeBulletinBoard")
    @ResponseBody
    public String addFreeBulletinBoard(@ModelAttribute BulletinEntity bulletinEntity
            , @RequestParam(value = "file", required = false) MultipartFile[] files) {
        System.out.println("제목 : " + bulletinEntity.getTitle());
        System.out.println("내용 : " + bulletinEntity.getContent());
        freeBulletinBoardService.addBulletin(bulletinEntity, files);
        return "성공적으로 등록되었습니다.";
    }
}
