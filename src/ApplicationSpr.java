import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan

//rmv couchbd的dep
@SpringBootApplication(exclude = {CouchbaseAutoConfiguration.class, MongoDataAutoConfiguration.class,

        ElasticsearchDataAutoConfiguration.class
})
public class ApplicationSpr {
    public static void main(String[] args) {
//        System.out.println(org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings.class);
//        System.out.println(com.hazelcast.client.config.ClientConfig.class);
        ConfigurableApplicationContext context = SpringApplication.run(ApplicationSpr.class, args);

        // 注册一个新的 Bean
      //  context.registerBean(MyService.class);

        // 获取并使用 Bean
     //   MyService myService = context.getBean(MyService.class);
     //   myService.sayHello();
    }
}