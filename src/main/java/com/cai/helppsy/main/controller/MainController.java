package com.cai.helppsy.main.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class MainController {

    @GetMapping("main")
    public String main(HttpSession session){
        System.out.println("___________________________________");
        System.out.println(session.getAttribute("userId"));
        System.out.println(session.getAttribute("userPass"));
        System.out.println(session.getAttribute("userAlias"));
        System.out.println("___________________________________");
        return "main/mainPage";
    }
}
