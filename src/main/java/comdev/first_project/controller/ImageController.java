package comdev.first_project.controller;

import comdev.first_project.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;
    @GetMapping()
    public String imageView(Model model){
        List<String> imageUrlS = this.imageService.getListImages();
        model.addAttribute("images", imageUrlS);
        return "image";
    }
}