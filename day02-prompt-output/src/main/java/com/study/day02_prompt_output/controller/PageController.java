package com.study.day02_prompt_output.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/zeroVSfewshot")
    public String zeroVSfewshotPage() {
        // static 폴더 내의 zeroVSfewshot.html로 포워딩
        return "forward:/zeroVSfewshot.html";
    }
}
