package com.study.day01_chat_client;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ChatViewController {

    @GetMapping("/chat")
    public String chatView() {
        return "chat"; // 템플릿 작성 필요
    }

    @GetMapping("/threelinesummary")
    public String threeLineSummaryView() {
        return "threelinesummary";
    }

    @GetMapping("/hmmterestingyoutube")
    public String youtubeView() {
        return "youtube";
    }

}
