package apiUsr;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//http://localhost:8080/reg?name=Tom
// Controller
@RestController
class RegController {
   // private final MyService myService;

    // 通过构造方法注入
//    public MyController(MyService myService) {
//        this.myService = myService;
//    }


    ////http://localhost:8080/reg?name=Tom
    @GetMapping("/reg")
    public String greet(@RequestParam String name) {
        return "get prm,name="+name;
    }
}
