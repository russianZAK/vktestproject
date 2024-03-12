package by.russianzak.vktestproject.album;

import by.russianzak.vktestproject.jsonplaceholder.album.Album;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
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
public class AlbumControllerIntegrationTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  private String baseUrl;

  private HttpHeaders headers;

  @BeforeEach
  public void setUp() {
    baseUrl = "http://localhost:" + port + "/api/albums";
    headers = new HttpHeaders();
    headers.setBasicAuth("admin@example.com", "admin");
  }

  @Test
  public void testGetAllAlbums() {
    HttpEntity<String> entity = new HttpEntity<>(null, headers);
    ResponseEntity<List> response = restTemplate.exchange(baseUrl, HttpMethod.GET, entity, List.class);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
  }

  @Test
  public void testGetAlbumById() {
    HttpEntity<String> entity = new HttpEntity<>(null, headers);
    ResponseEntity<Album> response = restTemplate.exchange(baseUrl + "/1", HttpMethod.GET, entity, Album.class);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(1L, response.getBody().getId());
  }

  @Test
  public void testCreateAlbum() {
    Album newAlbum = new Album(1L, 101L, "New Album");
    HttpEntity<Album> request = new HttpEntity<>(newAlbum, headers);
    ResponseEntity<Album> response = restTemplate.exchange(baseUrl, HttpMethod.POST, request, Album.class);
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals("New Album", response.getBody().getTitle());
  }

  @Test
  public void testUpdateAlbum() {
    Album updatedAlbum = new Album(1L, 1L, "Updated Album");
    HttpEntity<Album> request = new HttpEntity<>(updatedAlbum, headers);
    restTemplate.exchange(baseUrl + "/1", HttpMethod.PUT, request, Void.class);
    ResponseEntity<Album> response = restTemplate.exchange(baseUrl + "/1", HttpMethod.GET, new HttpEntity<>(null, headers), Album.class);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals("Updated Album", response.getBody().getTitle());
  }

  @Test
  public void testDeleteAlbum() {
    HttpEntity<String> entity = new HttpEntity<>(null, headers);
    restTemplate.exchange(baseUrl + "/1", HttpMethod.DELETE, entity, Void.class);
    ResponseEntity<Album> response = restTemplate.exchange(baseUrl + "/1", HttpMethod.GET, entity, Album.class);
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
    Album newAlbum = new Album(1L, 101L, "Unauthorized Album Creation");
    HttpEntity<Album> request = new HttpEntity<>(newAlbum, viewerHeaders);
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
    Album newAlbum = new Album(1L, 101L, "new Album Creation");
    HttpEntity<Album> request = new HttpEntity<>(newAlbum, viewerHeaders);
    ResponseEntity<Void> responsePOST = restTemplate.exchange(baseUrl, HttpMethod.POST, request, Void.class);
    ResponseEntity<Void> responseDELETE = restTemplate.exchange(baseUrl, HttpMethod.DELETE, request, Void.class);
    ResponseEntity<Void> responsePUT = restTemplate.exchange(baseUrl, HttpMethod.PUT, request, Void.class);

    assertNotEquals(HttpStatus.UNAUTHORIZED, responsePOST.getStatusCode());
    assertNotEquals(HttpStatus.UNAUTHORIZED, responseDELETE.getStatusCode());
    assertNotEquals(HttpStatus.UNAUTHORIZED, responsePUT.getStatusCode());
  }
}
