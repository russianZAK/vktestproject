package by.russianzak.vktestproject.jsonplaceholder.post;

import static by.russianzak.vktestproject.resttemplate.RestTemplateConfig.baseUrl;

import java.util.Arrays;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

  private final RestTemplate restTemplate;

  @Cacheable("posts")
  public List<Post> getAllPosts() {
    return Arrays.asList(
        Objects.requireNonNull(restTemplate.getForObject(baseUrl + "/posts", Post[].class)));
  }

  @Cacheable(value = "posts", key = "#id")
  public Post getPostById(Long id) {
    return restTemplate.getForObject(baseUrl + "/posts/{id}", Post.class, id);
  }

  @CachePut(value = "posts", key = "#result.id")
  public Post createPost(Post post) {
    return restTemplate.postForObject(baseUrl + "/posts", post, Post.class);
  }

  @CachePut(value = "posts", key = "#id")
  public Post updatePost(Long id, Post post) {
    restTemplate.put(baseUrl + "/posts/{id}", post, id);
    return post;
  }

  @CacheEvict(value = "posts", key = "#id")
  public void deletePost(Long id) {
    restTemplate.delete(baseUrl + "/posts/{id}", id);
  }
}
