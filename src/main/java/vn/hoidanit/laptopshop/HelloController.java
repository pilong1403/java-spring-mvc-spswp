package vn.hoidanit.laptopshop;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/")
    public String index() {
        return "Hello world update 1!";
    }

    @GetMapping("/user")
    public String userPage() {
        return "user Page!";
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "admin Page!";
    }

}
