package by.russianzak.vktestproject.jsonplaceholder.album;

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
public class AlbumService {

  private final RestTemplate restTemplate;


  @Cacheable("albums")
  public List<Album> getAllAlbums() {
    return Arrays.asList(
        Objects.requireNonNull(restTemplate.getForObject(baseUrl + "/albums", Album[].class)));
  }

  @Cacheable(value = "albums", key = "#id")
  public Album getAlbumById(Long id) {
    return restTemplate.getForObject(baseUrl + "/albums/{id}", Album.class, id);
  }

  @CachePut(value = "albums", key = "#result.id")
  public Album createAlbum(Album album) {
    return restTemplate.postForObject(baseUrl + "/albums", album, Album.class);
  }

  @CachePut(value = "albums", key = "#id")
  public Album updateAlbum(Long id, Album album) {
    restTemplate.put(baseUrl + "/albums/{id}", album, id);
    return album;
  }

  @CacheEvict(value = "albums", key = "#id")
  public void deleteAlbum(Long id) {
    restTemplate.delete(baseUrl + "/albums/{id}", id);
  }
}
