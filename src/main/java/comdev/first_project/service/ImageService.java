package comdev.first_project.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface ImageService {
    List<String> getListImages();
    void uploadImage(MultipartFile file);
    void updateImage(String key, MultipartFile file);
    void deleteImageByKey(String key);
}