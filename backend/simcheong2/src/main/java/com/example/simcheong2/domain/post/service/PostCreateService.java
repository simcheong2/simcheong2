package com.example.simcheong2.domain.post.service;

import com.example.simcheong2.domain.image.entity.Image;
import com.example.simcheong2.domain.post.entity.Post;
import com.example.simcheong2.domain.post.entity.dto.ImageAnalysisResultDTO;
import com.example.simcheong2.domain.post.repository.PostRepository;
import com.example.simcheong2.domain.user.entity.User;
import com.example.simcheong2.domain.user.repository.UserRepository;
import com.example.simcheong2.global.ai.OpenAiHelper;
import com.example.simcheong2.global.exception.model.CustomException;
import com.example.simcheong2.global.exception.model.ErrorCode;
import com.example.simcheong2.global.s3.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PostCreateService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostValidationService postValidationService;

    private final OpenAiHelper openAiHelper;
    private final S3Uploader s3Uploader;

    @Value("${cloudfrornt.url.domain}")
    String cloudfrontDomain;

    public void createPost(int userId, List<MultipartFile> multipartFiles, String content, String uploadDirRealPath) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND, "등록된 유저가 아닙니다"));
        if (!postValidationService.isFileSizeWithinLimit(multipartFiles)) throw createImageSizeException();
        List<ImageAnalysisResultDTO> imageAnalysisResultDTOS = uploadImages(multipartFiles, uploadDirRealPath);
        createPost(user, imageAnalysisResultDTOS, content);
    }

    private void createPost(User user, List<ImageAnalysisResultDTO> imageAnalysisResultDTOS, String content) {
        log.info("게시물 추가");
        user.addPost(new Post(content, new HashSet<>(createImages(imageAnalysisResultDTOS))));
    }

    public static List<Image> createImages(List<ImageAnalysisResultDTO> imageAnalysisResultDTOS) {
        AtomicInteger index = new AtomicInteger();
        return imageAnalysisResultDTOS.stream()
                .map(dto -> Image.builder()
                        .imageIndex(index.getAndIncrement())
                        .imageText(dto.getAnalyzedText())
                        .fileUrl(dto.getImageUrl())
                        .build())
                .collect(Collectors.toList());
    }

    private List<ImageAnalysisResultDTO> uploadImages(List<MultipartFile> multipartFiles, String uploadDirRealPath) {
        return multipartFiles.stream()
                .map(multipartFile -> uploadImage(multipartFile, uploadDirRealPath))
                .collect(Collectors.toList());
    }

    private ImageAnalysisResultDTO uploadImage(MultipartFile multipartFile, String uploadDirRealPath) {
        try {
            String s3Url = s3Uploader.uploadFile(multipartFile, "static");
            String cloudFrontUrl = s3ToCloudFrontUrl(s3Url);
            log.info("cloudFront url: {}", cloudFrontUrl);
            String text = openAiHelper.getTextFromMultipartFile(multipartFile, uploadDirRealPath);
            return ImageAnalysisResultDTO.builder()
                    .imageUrl(cloudFrontUrl)
                    .analyzedText(text)
                    .build();
        } catch (Exception e) {
            throw createImageSizeException();
        }
    }

    private String s3ToCloudFrontUrl(String s3Url) {
        try {
            // Parse the S3 URL
            URI s3Uri = new URI(s3Url);

            // Extract the path from the S3 URL
            String path = s3Uri.getPath();

            // Construct the CloudFront URL
            URI cloudfrontUri = new URI("https", cloudfrontDomain, path, null, null);

            return cloudfrontUri.toString();
        } catch (URISyntaxException e) {
            // Handle the exception if URL parsing fails
            e.printStackTrace();
            return null;
        }
    }


    private CustomException createImageSizeException() {
        return new CustomException(ErrorCode.BAD_REQUEST, "이미지 업로드 과정에서 문제가 생겼습니다. 이미지 크기가 너무 큰 것은 아닌지 다시 확인해주세요.");
    }
}
