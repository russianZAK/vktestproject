package by.russianzak.vktestproject.resttemplate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

  public final static String baseUrl = "https://jsonplaceholder.typicode.com";

  @Bean
  public RestTemplate restTemplate() {
    SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
    return new RestTemplate(requestFactory);
  }
}
