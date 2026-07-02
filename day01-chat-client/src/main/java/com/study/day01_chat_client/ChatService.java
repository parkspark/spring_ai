package com.study.day01_chat_client;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.google.genai.GoogleGenAiChatOptions;
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
                .options(GoogleGenAiChatOptions.builder()
                        .temperature(0.7))
                .call() // 호출 -> 응답할때까지 기다림
                .content(); // text 응답꺼내기

        return result;
    }

    // 3줄 요약 기능
    public String threeLineSummary(String text) {
        String result = chatClient.prompt()
                .system("""
                        당신은 텍스트 요약 전문가입니다.
                        입력받은 내용을 핵심 위주로 분석하여 반드시 정확히 3줄의 한국어 문장으로 요약해 주세요.
                        각 줄은 완전한 문장 형태여야 합니다.
                        추가적인 서론, 결론, 설명이나 인사말 없이 오직 3줄의 요약만 제공해야 합니다.
                        각 줄 앞에는 번호(1., 2., 3.)를 반드시 붙여주세요. 예:
                        1. 첫 번째 요약 내용
                        2. 두 번째 요약 내용
                        3. 세 번째 요약 내용
                        """)
                .user(text)
                .call()
                .content();

        return result;
    }

    // 유튜브 추천 기능
    public String recommendYoutube(String prompt) {
        String systemPrompt = """
                당신은 사용자의 관심사나 질문(프롬프트)에 최적인 유튜브 동영상 3개를 추천하는 전문가입니다.
                반드시 다음 JSON 형식으로만 답변해야 하며, 앞뒤에 설명이나 인사말 없이 오직 순수 JSON 문자열만 반환해 주세요.
                [
                  {
                    "title": "추천 동영상 제목",
                    "channel": "채널명",
                    "description": "이 영상을 추천하는 이유 (사용자의 프롬프트와 어울리도록 친근하고 명확하게 2~3문장 설명)",
                    "searchQuery": "유튜브 검색창에 검색할 검색어 (예: '채널명 동영상제목')"
                  },
                  ...
                ]
                반드시 정확히 3개의 아이템을 가진 배열이어야 합니다.
                """;

        String result = chatClient.prompt()
                .system(systemPrompt)
                .user(prompt)
                .call()
                .content();

        return result;
    }

}

