package comdev.first_project.service.impl;

import comdev.first_project.constant.SystemConstant.*;
import comdev.first_project.constant.PropertiesConstant.*;
import comdev.first_project.exception.ErrorMessages;
import comdev.first_project.exception.UploadObjectException;
import comdev.first_project.service.ImageService;
import comdev.first_project.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final S3Service s3Service;
    @Value(AWS.IMAGE_BUCKET_NAME)
    private String bucketName;

    @Override
    public void uploadImage(MultipartFile file) {
        this.checkValidImage(file);
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        try {
            this.s3Service.uploadObject(this.bucketName, file.getBytes(), fileName);
        } catch (IOException e) {
            throw new UploadObjectException(ErrorMessages.UPLOAD_ERROR);
        }
    }

    @Override
    public void updateImage(String key, MultipartFile file) {
        this.checkValidImage(file);
        try {
            this.s3Service.updateObject(this.bucketName, file.getBytes(), key);
        } catch (IOException e) {
            throw new UploadObjectException(ErrorMessages.UPLOAD_ERROR);
        }
    }

    @Override
    public void deleteImageByKey(String key) {
        this.s3Service.deleteObject(this.bucketName, key);
    }

    @Override
    public List<String> getListImages() {
        return this.s3Service.getUrlsOfObjectsInBucket(this.bucketName);
    }

    private void checkValidImage(MultipartFile file) {
        if (file.isEmpty()) {
            throw new UploadObjectException(ErrorMessages.FILE_IS_EMPTY);
        }
        //check this file is an image or not
        String fileName = file.getOriginalFilename();
        if (ObjectUtils.isEmpty(fileName) ||
                !fileName.endsWith(IMAGE.JPEG_EXTENSION) &&
                !fileName.endsWith(IMAGE.JPG_EXTENSION) &&
                !fileName.endsWith(IMAGE.PNG_EXTENSION)) {
            throw new UploadObjectException(ErrorMessages.FILE_IS_NOT_IMAGE);
        }
    }
}