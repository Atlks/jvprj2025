//package orgx.u;
//
//import org.springframework.web.bind.annotation.*;
//
//import java.beans.BeanProperty;
//import java.util.List;
//
//import static orgx.uti.Uti.encodeJson;
//
//@RestController
//@RequestMapping("/users")
//public class UserController {
//
//
//    @GetMapping
//    public List<User> getAllUsers() {
//        return  null;
//    }
//
//    ///users/createUser?name=Alice&age=30
//    //  /users/createUser
//    @GetMapping("/createUser")
//    public User createUser(@ModelAttribute  User user) {
//        //return userService.createUser(user);
//        System.out.println(encodeJson(user));
//        return user;
//    }
//}
