package com.cai.helppsy.freeBulletinBoard.controller;

import com.cai.helppsy.freeBulletinBoard.entity.FreeBulletin;
import com.cai.helppsy.freeBulletinBoard.entity.FreeBulletinComment;
import com.cai.helppsy.freeBulletinBoard.entity.FreeBulletinCommentInComment;
import com.cai.helppsy.freeBulletinBoard.serviece.FreeBulletinBoardService;
import com.cai.helppsy.tools.Paging;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class FreeBulletinBoardController {
    private final FreeBulletinBoardService freeBulletinBoardService;

    @GetMapping("addFreeBulletinMain")
    public String addFreeBulletin() {
        return "freeBulletinBoard/newFreeBulletin";
    }

    @GetMapping("mainFreeBulletin")
    public String main(Model model, @RequestParam(value = "page", defaultValue = "1") int page
            , @RequestParam(value = "currentPages", defaultValue = "1") int currentPages) {
        List<FreeBulletin> bulletinList = freeBulletinBoardService.bulletinList();
        model.addAttribute("bulletin", Paging.pagingList(10, page, bulletinList));
        model.addAttribute("numList", Paging.perPageNums(10, 5, currentPages, bulletinList));
        model.addAttribute("allPages", Paging.allPages(10, bulletinList));
        model.addAttribute("currentPage", page);
        model.addAttribute("currentPages", currentPages);
        model.addAttribute("pageNumListNumCnt", Paging.pageNumListNumCnt(10, bulletinList));
        return "freeBulletinBoard/mainFreeBulletin";
    }

    @PostMapping("addFreeBulletin")
    public String addFreeBulletinBoard(@ModelAttribute FreeBulletin bulletinEntity
            , @RequestParam(value = "file", required = false) MultipartFile[] files
            , @RequestParam(value = "imgName", required = false) MultipartFile imgFile
            , @RequestParam(value = "currentUserName", defaultValue = "guest") String currentUserName) {
        freeBulletinBoardService.addBulletin(bulletinEntity, files, imgFile, currentUserName);
        return "redirect:/mainFreeBulletin";
    }

    @GetMapping("specificBulletin")
    public String specificBulletin(Model model, @RequestParam("no") Integer no){
        freeBulletinBoardService.increaseViews(no);
        FreeBulletin bulletinOne = freeBulletinBoardService.bulletinOne(no);
        String imgName = freeBulletinBoardService.findImgName(no);
        model.addAttribute("bulletinOne", bulletinOne);
        model.addAttribute("imgName", imgName);
        model.addAttribute("comments", freeBulletinBoardService.getComments(no));
        return "freeBulletinBoard/specificFreeBulletin";
    }

    @PostMapping("freeBulletin/addComment")
    public String addComment(@ModelAttribute FreeBulletinComment freeBulletinComment
            , @RequestParam(value = "fkNo") Integer fkNo){
        if(freeBulletinComment.getWriter() == ""){
            freeBulletinComment.setWriter("guest");
        }

        freeBulletinBoardService.addComment(freeBulletinComment, fkNo);

        return "redirect:/specificBulletin?no="+fkNo;
    }

    @PostMapping("freeBulletin/addCommentInComment")
    public String addCommentInComment(@ModelAttribute FreeBulletinCommentInComment freeBulletinCommentInComment
            , @RequestParam(value = "fkBulletinNo") Integer fkBulletinNo
            , @RequestParam(value = "fkCommentNo") Integer fkCommentNo){
        if(freeBulletinCommentInComment.getWriter() == ""){
            freeBulletinCommentInComment.setWriter("guest");
        }

        freeBulletinBoardService.addCommentInComment(freeBulletinCommentInComment, fkBulletinNo, fkCommentNo);

        return "redirect:/specificBulletin?no="+fkBulletinNo;
    }

/*    @ResponseBody
    @PostMapping("testing")
    public Map<String, String> testControllerForAjax(@RequestParam(value = "testValue1", required = false) String a
            , @RequestParam(value = "testValue2", required = false) String b
            , Model model){
        Map<String, String> fb = new HashMap<>();
        System.out.println(a);
        System.out.println(b);
        for(int i = 0; i < 10; i ++){
            fb.put(i+"",b);
        }
        System.out.println(fb.size());
        return fb;
    }*/
/*    @ResponseBody
    @PostMapping("testing")
    public FreeBulletin testControllerForAjax(@RequestParam(value = "testValue1", required = false) String a
            , @RequestParam(value = "testValue2", required = false) String b
            , Model model){
        FreeBulletin fb = new FreeBulletin();
        System.out.println(a);
        System.out.println(b);
        fb.setWriter(b);
        fb.setContent(a);
        return fb;
    }*/
    @PostMapping("commentInComment")
    @ResponseBody
    public FreeBulletinCommentInComment[] commentInComment(@RequestParam(value = "no", required = false) Integer no){
        System.out.println("-----------------1___________________");
        /*FreeBulletinCommentInComment[] a = */freeBulletinBoardService.getCommentInComments(no);
        System.out.println("-----------------4___________________");
        return null;
    }


    @ResponseBody
    @PostMapping("testing")
    public FreeBulletin[] testControllerForAjax(@RequestParam(value = "testValue1", required = false) String a
            , @RequestParam(value = "testValue2", required = false) String b){
//        List<FreeBulletin> list = new ArrayList<>();
        FreeBulletin[] list = new FreeBulletin[10];
        for(int i = 0; i < 10; i++) {
            FreeBulletin fb = new FreeBulletin();
            fb.setWriter(b);
            fb.setContent(a);
            list[i] = fb;
        }
        return list;
    }
}
