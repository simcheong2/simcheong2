package com.example.simcheong2.global.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAiConfig {
    public String defaultMessage = "친절한 어투로 한국어로 답변 해줘.";

    @Bean
    ChatClient chatClient(ChatClient.Builder builder) {
        return builder.defaultSystem(defaultMessage)
                .build();
    }
}
