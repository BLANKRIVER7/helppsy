package com.cai.helppsy.freeBulletinBoard.controller;

import com.cai.helppsy.freeBulletinBoard.dto.CommentInCommentDTO;
import com.cai.helppsy.freeBulletinBoard.entity.FreeBulletin;
import com.cai.helppsy.freeBulletinBoard.entity.FreeBulletinComment;
import com.cai.helppsy.freeBulletinBoard.entity.FreeBulletinCommentInComment;
import com.cai.helppsy.freeBulletinBoard.entity.FreeBulletinLikes;
import com.cai.helppsy.freeBulletinBoard.serviece.FreeBulletinBoardService;
import com.cai.helppsy.tools.Paging;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
    public String mainFreeBulletin(Model model, @RequestParam(value = "page", defaultValue = "1") int page
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
    public String specificBulletin(Model model, @RequestParam("no") Integer no
            , @RequestParam(value = "userName", defaultValue = "guest") String userName) {
        freeBulletinBoardService.increaseViews(no);
        FreeBulletin bulletinOne = freeBulletinBoardService.bulletinOne(no);
        String imgName = freeBulletinBoardService.findImgName(no);
        model.addAttribute("bulletinOne", bulletinOne);
        model.addAttribute("imgName", imgName);
        model.addAttribute("comments", freeBulletinBoardService.getComments(no));
        model.addAttribute("likes",bulletinOne.getLikes());
        model.addAttribute("isPressedLike", freeBulletinBoardService.isPressedLike(userName, no));
        return "freeBulletinBoard/specificFreeBulletin";
    }

    @ResponseBody
    @PostMapping("freeBulletin/addComment")
    public String addComment(@ModelAttribute FreeBulletinComment freeBulletinComment
            , @RequestParam(value = "fkNo") Integer fkNo) {
        if (freeBulletinComment.getWriter() == "") {
            freeBulletinComment.setWriter("guest");
        }

        freeBulletinBoardService.addComment(freeBulletinComment, fkNo);

        return "succeeded";
    }

    @ResponseBody
    @PostMapping("freeBulletin/addCommentInComment")
    public String addCommentInComment(@ModelAttribute FreeBulletinCommentInComment freeBulletinCommentInComment
            , @RequestParam(value = "fkNo") Integer fkNo) {
        if (freeBulletinCommentInComment.getWriter() == "") {
            freeBulletinCommentInComment.setWriter("guest");
        }

        freeBulletinBoardService.addCommentInComment(freeBulletinCommentInComment, fkNo);

        return "succeeded";
    }

    @PostMapping("commentInComment")
    @ResponseBody
    public CommentInCommentDTO[] commentInComment(@RequestParam(value = "no", required = false) Integer no) {
        FreeBulletinCommentInComment[] cicList = freeBulletinBoardService.getCommentInComments(no);

        if(cicList == null)
            return null;

        CommentInCommentDTO[] list = new CommentInCommentDTO[cicList.length];
        int i = 0;
        for (FreeBulletinCommentInComment cic : cicList) {
            list[i] = new CommentInCommentDTO(cic.getNo(), cic.getType(), cic.getWriter()
                    , cic.getContent(), cic.getLikes(), cic.getCreateDate());
            i++;
        }

        return list;
    }
    @ResponseBody
    @PostMapping("likesUp")
    public String likesUp(@ModelAttribute FreeBulletinLikes freeBulletinLikes
            , @RequestParam("fkNo") int fkNo){
        if(freeBulletinLikes.getUserName().equals("")){
            freeBulletinLikes.setUserName("guest");
        }
        freeBulletinBoardService.likesUp(freeBulletinLikes, fkNo);
        return "succeeded";
    }
}
