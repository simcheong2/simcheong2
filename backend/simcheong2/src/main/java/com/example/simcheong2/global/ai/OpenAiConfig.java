package com.example.simcheong2.global.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAiConfig {
    private final String defaultMessage = "한국어로 답변 해줘. " +
            "단, 이 사진이 너무 성적으로 야하거나 인종차별적이나 장애차별적인 성격을 띄고 있다면 " +
            "'잘 모르겠어요'라는 텍스트로 응답해줘";

    @Bean
    ChatClient chatClient(ChatClient.Builder builder) {
        return builder.defaultSystem(defaultMessage)
                .build();
    }
}
