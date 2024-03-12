package by.russianzak.vktestproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableCaching
@EnableWebMvc
@SpringBootApplication
public class VkTestProjectApplication {

  public static void main(String[] args) {
    SpringApplication.run(VkTestProjectApplication.class, args);
  }
}
