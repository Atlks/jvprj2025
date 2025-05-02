package api.usr;





//http://localhost:8080/reg?name=Tom
// Controller

class RegController {
   // private final MyService myService;

    // 通过构造方法注入..
//    public MyController(MyService myService) {
//        this.myService = myService;
//    }


    ////http://localhost:8080/reg?name=Tom
  //  @GetMapping("/reg")
    public String greet( String name) {
        return "get prm,name="+name;
    }
}
