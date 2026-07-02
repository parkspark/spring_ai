package com.study.day01_chat_client;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service // 컴포넌트 선언
public class ChatService {

    private final ChatClient chatClient;

    // 생성자 주입 DI : Spring Boot 가 구성해 줄 수 있도록 빌더 패턴으로 주입 DI
    public ChatService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    // 실제 AI 질문 메서드
    public String chat(String message) {
        String result = chatClient.prompt()
                .user(message) // 사용자가 실제 입력한 질문
                .call() // 호출 -> 응답할때까지 기다림
                .content(); // text 응답꺼내기

        return result;
    }

}
