package comdev.first_project.controller;

import comdev.first_project.entity.Province;
import comdev.first_project.repository.ProvinceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/hello")
public class HelloRestController {
    private final ProvinceRepository provinceRepository;

    @GetMapping()
    public List<Province> hello()
    {
        return this.provinceRepository.findAll();
    }
}
