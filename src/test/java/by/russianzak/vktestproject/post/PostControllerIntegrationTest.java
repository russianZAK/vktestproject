package by.russianzak.vktestproject.post;

import by.russianzak.vktestproject.jsonplaceholder.album.Album;
import by.russianzak.vktestproject.jsonplaceholder.post.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostControllerIntegrationTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  private String baseUrl;

  private HttpHeaders headers;

  @BeforeEach
  public void setUp() {
    baseUrl = "http://localhost:" + port + "/api/posts";
    headers = new HttpHeaders();
    headers.setBasicAuth("admin@example.com", "admin");
  }

  @Test
  public void testGetAllPosts() {
    HttpEntity<String> entity = new HttpEntity<>(null, headers);
    ResponseEntity<List> response = restTemplate.exchange(baseUrl, HttpMethod.GET, entity, List.class);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
  }

  @Test
  public void testGetPostById() {
    HttpEntity<String> entity = new HttpEntity<>(null, headers);
    ResponseEntity<Post> response = restTemplate.exchange(baseUrl + "/1", HttpMethod.GET, entity, Post.class);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(1L, response.getBody().getId());
  }

  @Test
  public void testCreatePost() {
    Post newPost = new Post(1L, 101L, "New Post", "New Post Body");
    HttpEntity<Post> request = new HttpEntity<>(newPost, headers);
    ResponseEntity<Post> response = restTemplate.exchange(baseUrl, HttpMethod.POST, request, Post.class);
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals("New Post", response.getBody().getTitle());
  }

  @Test
  public void testUpdatePost() {
    Post updatedPost = new Post(1L, 1L, "Updated Post", "Updated Post Body");
    HttpEntity<Post> request = new HttpEntity<>(updatedPost, headers);
    restTemplate.exchange(baseUrl + "/1", HttpMethod.PUT, request, Void.class);
    ResponseEntity<Post> response = restTemplate.exchange(baseUrl + "/1", HttpMethod.GET, new HttpEntity<>(null, headers), Post.class);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals("Updated Post", response.getBody().getTitle());
  }

  @Test
  public void testDeletePost() {
    HttpEntity<String> entity = new HttpEntity<>(null, headers);
    restTemplate.exchange(baseUrl + "/1", HttpMethod.DELETE, entity, Void.class);
    ResponseEntity<Post> response = restTemplate.exchange(baseUrl + "/1", HttpMethod.GET, entity, Post.class);
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }


  @Test
  public void testUnauthorizedAccess() {
    ResponseEntity<Void> response = restTemplate.getForEntity(baseUrl, Void.class);
    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
  }

  @Test
  public void testUnauthorizedAccessForViewer() {
    HttpHeaders viewerHeaders = new HttpHeaders();
    viewerHeaders.setBasicAuth("viewer@example.com", "password");
    Post newPost = new Post(1L, 101L, "Unauthorized Post Creation", "Body");
    HttpEntity<Post> request = new HttpEntity<>(newPost, viewerHeaders);
    ResponseEntity<Void> responsePOST = restTemplate.exchange(baseUrl, HttpMethod.POST, request, Void.class);
    ResponseEntity<Void> responseDELETE = restTemplate.exchange(baseUrl, HttpMethod.DELETE, request, Void.class);
    ResponseEntity<Void> responsePUT = restTemplate.exchange(baseUrl, HttpMethod.PUT, request, Void.class);

    assertEquals(HttpStatus.UNAUTHORIZED, responsePOST.getStatusCode());
    assertEquals(HttpStatus.UNAUTHORIZED, responseDELETE.getStatusCode());
    assertEquals(HttpStatus.UNAUTHORIZED, responsePUT.getStatusCode());
  }

  @Test
  public void testUnauthorizedAccessForEditor() {
    HttpHeaders viewerHeaders = new HttpHeaders();
    viewerHeaders.setBasicAuth("editor@example.com", "editor");
    Post newPost = new Post(1L, 101L, "Unauthorized Post Creation", "Body");
    HttpEntity<Post> request = new HttpEntity<>(newPost, viewerHeaders);
    ResponseEntity<Void> responsePOST = restTemplate.exchange(baseUrl, HttpMethod.POST, request, Void.class);
    ResponseEntity<Void> responseDELETE = restTemplate.exchange(baseUrl, HttpMethod.DELETE, request, Void.class);
    ResponseEntity<Void> responsePUT = restTemplate.exchange(baseUrl, HttpMethod.PUT, request, Void.class);

    assertNotEquals(HttpStatus.UNAUTHORIZED, responsePOST.getStatusCode());
    assertNotEquals(HttpStatus.UNAUTHORIZED, responseDELETE.getStatusCode());
    assertNotEquals(HttpStatus.UNAUTHORIZED, responsePUT.getStatusCode());
  }
}
