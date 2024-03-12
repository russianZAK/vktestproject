package by.russianzak.vktestproject.jsonplaceholder.user.data;

import static by.russianzak.vktestproject.resttemplate.RestTemplateConfig.baseUrl;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDataService {

  private final RestTemplate restTemplate;

  @Cacheable("userdata")
  public List<UserData> getAllUsers() {
    return Arrays.asList(
        Objects.requireNonNull(restTemplate.getForObject(baseUrl + "/users", UserData[].class)));
  }

  @Cacheable(value = "userdata", key = "#id")
  public UserData getUserById(Long id) {
    return restTemplate.getForObject(baseUrl + "/users/{id}", UserData.class, id);
  }

  @CachePut(value = "userdata", key = "#result.id")
  public UserData createUser(UserData user) {
    return restTemplate.postForObject(baseUrl + "/users", user, UserData.class);
  }

  @CachePut(value = "userdata", key = "#id")
  public UserData updateUser(Long id, UserData user) {
    restTemplate.put(baseUrl + "/users/{id}", user, id);
    return user;
  }

  @CacheEvict(value = "userdata", key = "#id")
  public void deleteUser(Long id) {
    restTemplate.delete(baseUrl + "/users/{id}", id);
  }
}
