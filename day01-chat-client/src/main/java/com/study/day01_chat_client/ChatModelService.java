package com.study.day01_chat_client;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.retry.RetryOperations;
import org.springframework.stereotype.Service;

@Service
public class ChatModelService {

    @Autowired
    private ChatModel chatModel;
    @Autowired
    private RetryOperations retryOperations;

    public String chat(String message) {
        String response = chatModel.call(message); // 문자열 하나만 반환해 주는 메서드

        return response;
    }

}
