package com.yog.electronicstore.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/test")
public class HomeController {
    @GetMapping
    public String testing()
    {
        return "Welcome to home controller class electronic store";
    }
}
