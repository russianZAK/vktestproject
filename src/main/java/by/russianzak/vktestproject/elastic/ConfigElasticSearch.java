package by.russianzak.vktestproject.elastic;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.http.HttpHeaders;

@Configuration
@EnableElasticsearchRepositories()
public class ConfigElasticSearch extends AbstractElasticsearchConfiguration {

  @Value("${elasticsearch.host}")
  private String host;

  @Value("${elasticsearch.port}")
  private int port;

  @Override
  @Bean
  public RestHighLevelClient elasticsearchClient() {
    ClientConfiguration clientConfiguration = ClientConfiguration.builder()
        .connectedTo(host + ":" + port)
        .withDefaultHeaders(compatibilityHeaders())
        .build();

    return RestClients.create(clientConfiguration).rest();
  }

  @Bean
  public ElasticsearchRestTemplate elasticsearchRestTemplate() {
    return new ElasticsearchRestTemplate(elasticsearchClient());
  }

  private HttpHeaders compatibilityHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Accept", "application/vnd.elasticsearch+json;compatible-with=7");
    headers.add("Content-Type", "application/vnd.elasticsearch+json;compatible-with=7");
    return headers;
  }

}