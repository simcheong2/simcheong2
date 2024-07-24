package com.example.simcheong2.global.ai;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Media;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Component
@RequiredArgsConstructor
public class OpenAiHelper {
    private final ChatClient chatClient;

    public String getTextFromMultipartFile(String realPath, MultipartFile multipartFile) {
        System.out.println("이미지 저장 시작");
//        String imagePath = saveFile(multipartFile, realPath);
        String imagePath = "";

        System.out.println("----");
        System.out.println(realPath);
        System.out.println(imagePath);

        System.out.println("이미지 분석 시작");
        String imageFullPath = realPath + File.separator + imagePath;

        Resource imageData = new FileSystemResource(imageFullPath);
        UserMessage userMessage = new UserMessage("What’s in this image? 한국어로 답변 해줘",
                new Media(MimeTypeUtils.IMAGE_JPEG, imageData));

        String responseText = chatClient.prompt(new Prompt(userMessage)).call().content();

        // 저장된 이미지 파일 삭제

        return responseText;
    }
}
