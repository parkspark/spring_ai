package com.study.day02_prompt_output.controller;

import com.study.day02_prompt_output.dto.InquiryResult;
import com.study.day02_prompt_output.dto.PromptRecommendation;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PromptController {

    private final ChatClient chatClient;

    private static final String CLASSIFY_TEMPLATE = """
            다음 고객 문의를 분류하세요.
            
            - category: 배송, 환불, 상품, 계정, 기타 중 하나
            - priority: HIGH, MEDIUM, LOW 중 하나
            - reason: 그렇게 분류한 이유 한 문장
            
            문의 내용: {text}
            """;

    private static final String PROMPT_RECOMMEND_TEMPLATE = """
            사용자가 다음 목적을 달성하려고 할 때 최고의 결과를 얻을 수 있는 3개의 프롬프트를 추천해주세요.
            
            목적: {purpose}
            
            예시:
            목적: "영문 이메일 작성"
            결과:
            [
              {
                "prompt": "내가 작성한 한국어 이메일을 비즈니스 영어로 번역하고, 더 정중한 표현으로 다듬어주세요.",
                "reason": "번역과 함께 톤 앤 매너까지 교정하여 비즈니스에 적합한 결과를 얻을 수 있습니다."
              },
              {
                "prompt": "외국 파트너에게 미팅 일정을 조율하는 영문 이메일 초안을 작성해주세요. 미팅 가능 시간은 다음 주 화요일 오전 10시입니다.",
                "reason": "구체적인 상황과 조건을 제시하여 즉시 사용 가능한 초안을 얻을 수 있습니다."
              }
            ]
            """;

    public PromptController(ChatClient.Builder builder) {
        this.chatClient = builder
                .defaultSystem("당신은 정중한 한국어로 답하는 어시스턴트이며 추측하지 않는다")
                .build();
    }

    @GetMapping("/summary")
    public java.util.Map<String, String> summary(@RequestParam("text") String text, @RequestParam("audience") String audience) {
        String result = chatClient.prompt()
                .user(u -> u.text("""
                        다음 고객 문의를 {audience}가 읽기 좋게 3줄 이내로 요약하세요.
                        핵심 요구사항이 무엇인지 첫 줄에 쓰세요.
                        
                        문의 내용: {text}
                        """)
                        .param("audience", audience)
                        .param("text", text))
                .call()
                .content();
        return java.util.Map.of("summary", result);
    }

    @GetMapping("/classify/raw")
    public String classifyRaw(@RequestParam("text") String text) {
        return chatClient.prompt()
                .user(u -> u.text(CLASSIFY_TEMPLATE)
                        .param("text", text))
                .call()
                .content();
    }

    @GetMapping("/classify")
    public InquiryResult classify(@RequestParam("text") String text) {
        return chatClient.prompt()
                .user(u -> u.text(CLASSIFY_TEMPLATE)
                        .param("text", text))
                .call()
                .entity(InquiryResult.class);
    }

    @GetMapping("/prompts")
    public List<PromptRecommendation> recommendPrompts(@RequestParam("purpose") String purpose) {
        return chatClient.prompt()
                .user(u -> u.text(PROMPT_RECOMMEND_TEMPLATE)
                        .param("purpose", purpose))
                .call()
                .entity(new ParameterizedTypeReference<List<PromptRecommendation>>() {});
    }
}
