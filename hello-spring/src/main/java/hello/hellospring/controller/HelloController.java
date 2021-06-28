package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    @GetMapping("hello")
    public String hello(Model model){ // MVC에서의 model
        model.addAttribute("data", "hello!!");
        return "hello"; // resources/templates/hello.html로 전달하여 렌더링해라.
    }
}
