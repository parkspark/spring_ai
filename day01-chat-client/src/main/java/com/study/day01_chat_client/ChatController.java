package com.study.day01_chat_client;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController // REST API 방식의 컨트롤러
public class ChatController {

    // 서비스 레이어 의존성 주입
    private final ChatService chatService;

    // 생성자 주입 DI
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/api/chat")
    public String chat(@RequestParam String message) {
        return chatService.chat(message);
    }

    @GetMapping("/api/teacher")
    public String teacher(@RequestParam String message) {
        return chatService.teacher(message);
    }

    @PostMapping("/api/summary")
    public String summary(@RequestParam String text) {
        return chatService.threeLineSummary(text);
    }

    @PostMapping("/api/youtube")
    public String youtube(@RequestParam String prompt) {
        return chatService.recommendYoutube(prompt);
    }

}
