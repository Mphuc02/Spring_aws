package comdev.first_project.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface S3Service {
    List<String> getUrlsOfObjectsInBucket(String bucketName);
    void uploadObject(String bucketName, byte[] data, String key);
    void updateObject(String bucketName, byte[] data, String key);
    void deleteObject(String bucketName, String key);
}