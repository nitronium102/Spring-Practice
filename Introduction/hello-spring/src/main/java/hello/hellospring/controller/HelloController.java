package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    @GetMapping("hello")
    public String hello(Model model){ // MVC에서의 model(뒤에서 렌더링할 때 사용)
        model.addAttribute("data", "hello!!");
        return "hello"; // resources/templates/hello.html로 전달하여 렌더링해라.
    }

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam(value="name") String name, Model model){ // 외부에서 파라미터 받는다
        model.addAttribute("name", name); // 앞에가 key 뒤에가 value
        return "hello-template";
    }

    @GetMapping("hello-string")
    @ResponseBody // http에서의 body부의 데이터를 직접 넣어주겠다.
    public String helloString(@RequestParam("name") String name){
        // template 엔진과의 차이 -> view 없이 그대로 전송
        return "hello "+name;
    }

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name){
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }

    static class Hello {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
