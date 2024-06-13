package comdev.first_project.rest;

import comdev.first_project.exception.ErrorMessages;
import comdev.first_project.response.ResponseCustom;
import comdev.first_project.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/image")
public class ImageRest {
    private final ImageService imageService;

    @GetMapping()
    public ResponseCustom<String> getAll(){
        return new ResponseCustom<>(this.imageService.getListImages(), ErrorMessages.SUCCESS);
    }
    @PostMapping()
    public ResponseEntity<Object> uploadImage(@RequestParam MultipartFile file){
        this.imageService.uploadImage(file);
        return ResponseEntity.ok(new ResponseCustom<>());
    }
    @PutMapping("/{key}")
    public ResponseEntity<Object> updateImage(@PathVariable String key, @RequestParam MultipartFile file){
        this.imageService.updateImage(key, file);
        return ResponseEntity.ok(new ResponseCustom<>());
    }
    @DeleteMapping("/{key}")
    public ResponseEntity<Object> deleteImage(@PathVariable String key){
        this.imageService.deleteImageByKey(key);
        return ResponseEntity.ok(new ResponseCustom<>());
    }
}