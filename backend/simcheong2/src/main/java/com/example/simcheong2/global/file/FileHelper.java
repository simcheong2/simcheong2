package com.example.simcheong2.global.file;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;


@Component
@Slf4j
public class FileHelper {
    // 파일을 삭제하는 메소드
    public void deleteFile(String uploadDirRealPath, String serverUploadFileName) {
        log.info("파일 삭제: {}", serverUploadFileName);
        File fileToDelete = new File(getFullPath(uploadDirRealPath, serverUploadFileName));
        if (fileToDelete.delete()) {
            log.info("파일 삭제 성공: {}", serverUploadFileName);
            return;
        }
        log.info("파일 삭제 실패: {}", serverUploadFileName);
    }

    // 파일을 저장하는 메소드
    public String saveFile(MultipartFile file, String uploadDirRealPath) {
        try {
            String serverUploadFileName = serverUploadFile(file, uploadDirRealPath);
            log.info("uploadFileName name: {}", serverUploadFileName);
            return serverUploadFileName;
        } catch (Exception e) {
            return null;
        }
    }

    /*
     * return 업로드된 파일 네임.
     */
    public String serverUploadFile(MultipartFile multipartFile, String uploadDirRealPath) {
        if (multipartFile.isEmpty()) return null;
        String originalFilename = multipartFile.getOriginalFilename(); //원래 파일명
        String serverUploadFileName = createServerFileName(originalFilename); //uuid 생성해서 뒤에 원래파일명의 확장자명 붙이기
        log.info("upload 된 파일 이름: {}", serverUploadFileName);
        try {
            multipartFile.transferTo(new File(getFullPath(uploadDirRealPath, serverUploadFileName)));//저장: (서버에 업로드되는 파일명, 업로드 되는 경로)
            return serverUploadFileName;
        } catch (Exception e) {
            return null;
        }
    }

    public String getFullPath(String fileDir, String filename) {
        return fileDir + filename;
    }

    private String createServerFileName(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(originalFilename);
        log.info("파일 확장자: {}", ext);
        return uuid + "." + ext;
    }

    //원래 파일명에서 확장자 뽑기(.jpg, .png ...)
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}