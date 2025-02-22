package apiUsr;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// Controller
@RestController
class LoginController {
   // private final MyService myService;

    // 通过构造方法注入
//    public MyController(MyService myService) {
//        this.myService = myService;
//    }

    @GetMapping("/greet")
    public String greet(@RequestParam String name) {
        return "get prm,name="+name;
    }
}
