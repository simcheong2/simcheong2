package com.example.simcheong2.global.ai;

import com.example.simcheong2.global.exception.model.CustomException;
import com.example.simcheong2.global.exception.model.ErrorCode;
import com.example.simcheong2.global.file.FileHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class OpenAiHelper {
    private final ChatClient chatClient;
    private final FileHelper fileHelper;

    private String requestText = "What’s in this image? 한국어로 해줘. " +
            "단, 이 사진이 너무 야하거나 인종차별이나 장애차별적인 성격을 띄고 있다면 '부적절한 컨텐츠입니다.'라는 텍스트를 응답해줘";

    public String getTextFromMultipartFile(MultipartFile multipartFile, String uploadDirRealPath) {
        String uploadFileName = fileHelper.saveFile(multipartFile, uploadDirRealPath);
        if (uploadFileName == null) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR, "이미지 파일을 현재 처리할 수 없습니다. 잠시후 다시 시도해주세요.");
        }

        try {
            String responseText = analyzeImageWithChatClient(uploadDirRealPath, uploadFileName);
            log.info(responseText);
            return responseText;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        } finally {
            fileHelper.deleteFile(uploadDirRealPath, uploadFileName);
        }
    }

    public String analyzeImageWithChatClient(String uploadDirRealPath, String uploadFileName) {
        String imageFullPath = uploadDirRealPath + File.separator + uploadFileName;
        log.info("이미지 분석 시작! 경로: {}", imageFullPath);

        Resource imageData = new FileSystemResource(imageFullPath);
        UserMessage userMessage = new UserMessage(requestText, new Media(MimeTypeUtils.IMAGE_JPEG, imageData));
        return chatClient.prompt(new Prompt(userMessage)).call().content();
    }


}
