package com.example.bossi.service.email;

import com.example.bossi.entity.WaitingListStatus;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailSenderService {

    private final JavaMailSender javaMailSender;

    public void sendEmailWithAttachment(String to, String status) {
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);

            String subject;
            String htmlContent;
            byte[] contentBytes;

            if(status.equals("enteringStore")){
                subject = "[Bossi] 입점 심사 가이드";

                Path path = Paths.get("./src/main/resources/template/email/guideEmail.html");
                contentBytes = Files.readAllBytes(path);


                // 파일 추가
                String paths = System.getProperty("user.dir");
                System.out.println("현재 작업 경로: " + paths);

                String filePath = "../../enteringStoreFile/bossi_storeGuide.pdf";
                File file = new File(filePath);
                helper.addAttachment(file.getName(), file);
            }else if(status.equals(WaitingListStatus.ALLOW.name())) {
                subject = "[Bossi] 입점 심사 통과 안내드립니다.";

                // 파일 추가
                /*String paths = System.getProperty("user.dir");
                System.out.println("현재 작업 경로: " + paths);*/

                Path path = Paths.get("./src/main/resources/template/email/allowEmail.html");
                contentBytes = Files.readAllBytes(path);
            }else {
                subject = "[Bossi] 입점 심사 결과 안내드립니다.";

                Path path = Paths.get("./src/main/resources/template/email/refusalEmail.html");
                contentBytes = Files.readAllBytes(path);
            }

            htmlContent = new String(contentBytes, StandardCharsets.UTF_8);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
