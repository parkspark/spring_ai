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

    // 일반 응답
    public String chat(String message) {
        String result = chatClient.prompt()
                .user(message) // User Prompt 사용자가 실제 입력한 질문
                .call() // 호출 -> 응답할때까지 기다림
                .content(); // text 응답꺼내기

        return result;
    }

    // 교사 역할 응답
    public String teacher(String message) {
        String result = chatClient.prompt()
                .system("""
                        당신은 Java, Spring Boot, Spring AI를 가르치는 선생님입니다.
                        초등 학생에게 설명하듯이 답변 해 주세요.

                        """) // System Prompt 역할을 지시
                .user(message) // User Prompt 사용자가 실제 입력한 질문
                .call() // 호출 -> 응답할때까지 기다림
                .content(); // text 응답꺼내기

        return result;
    }

}
