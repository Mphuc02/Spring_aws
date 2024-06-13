package comdev.first_project.schedule;

import comdev.first_project.constant.PropertiesConstant.*;
import comdev.first_project.constant.SystemConstant.*;
import comdev.first_project.exception.ErrorMessages;
import comdev.first_project.exception.NotFoundException;
import comdev.first_project.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.io.FileInputStream;
import java.io.IOException;

@EnableAsync
@Component
@RequiredArgsConstructor
public class BackupLogFileToS3 {
    private final S3Service s3Service;

    @Value(AWS.BACKUP_BUCKET_NAME)
    private String backUpBucketName;

    private final Logger logger = LoggerFactory.getLogger(BackupLogFileToS3.class);

    @Async
//    @Scheduled(fixedRate = 1000 * 60 * 5) //5 minute
    @Scheduled(fixedRate = 1000 * 60) //5 minute
    public void backupLogFile(){
        logger.info("Start backup log file to S3 cloud");
        String logFileName = BACKUP.LOG_FILE_NAME;

        try(FileInputStream fis = new FileInputStream("/log/server.log")
        ) {
            this.s3Service.uploadObject(this.backUpBucketName, fis.readAllBytes() , logFileName);
        } catch (IOException e) {
            throw new NotFoundException(ErrorMessages.LOG_FILE_NOT_FOUND);
        }
    }
}