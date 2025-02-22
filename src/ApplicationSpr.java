import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@ComponentScan
@SpringBootApplication
public class ApplicationSpr {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ApplicationSpr.class, args);

        // 注册一个新的 Bean
      //  context.registerBean(MyService.class);

        // 获取并使用 Bean
     //   MyService myService = context.getBean(MyService.class);
     //   myService.sayHello();
    }
}