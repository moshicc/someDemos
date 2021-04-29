import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author zcc
 * @ClassName WebApplication
 * @description
 * @date 2021/4/29 19:12
 * @Version 1.0
 */
@SpringBootApplication
@ComponentScan("com.zcc")
public class WebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class);
    }
}
