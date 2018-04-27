package main;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;



@SpringBootApplication(scanBasePackages = {"configuration","entity","dto","repository","service","controller","main","validators"})
@EnableJpaRepositories(basePackages = {"repository"})
@PropertySource(value = "classpath:application.properties")
@EntityScan(basePackages ={"entity"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}