package com.example.bossi.service.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.example.bossi.util.MultipartUtil;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Component
public class AwsS3UploadService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final AmazonS3Client amazonS3Client;
    private final AmazonS3 s3Client;

    public String store(String fullPath, MultipartFile multipartFile, String id) {
        LocalDate localDate = LocalDate.now();
        String folderKey = "users/"+id+"/"+localDate+"/";
        String objectKey = folderKey + fullPath;

        File file = new File(MultipartUtil.getLocalHomeDirectory(), fullPath);
        try {
            multipartFile.transferTo(file);
            amazonS3Client.putObject(new PutObjectRequest(bucket, objectKey, file)
                    .withCannedAcl(CannedAccessControlList.PublicRead));    // 권한 설정/ 업로드된 객체에 대해 공개 읽기 액세스 권한을 부여합니다. 즉, 누구나 해당 객체를 읽을 수 있습니다.
        } catch (Exception e) {
            throw new RuntimeException();
        } finally {
            if (file.exists()) {
                file.delete();
            }
            // 이미지의 URL 생성

            return objectKey;
            //return amazonS3Client.getUrl(bucket, objectKey).toString();
        }
    }

    public void deleteMissingImages(List<String> deleteList){
        for(String imgKey : deleteList){
            DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, imgKey);
            s3Client.deleteObject(deleteObjectRequest);
        }
    }
}
