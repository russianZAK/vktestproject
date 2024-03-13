package by.russianzak.vktestproject;

import by.russianzak.vktestproject.jsonplaceholder.post.Post;
import by.russianzak.vktestproject.jsonplaceholder.user.data.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserDataControllerIntegrationTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  private String baseUrl;

  private HttpHeaders headers;

  @BeforeEach
  public void setUp() {
    baseUrl = "http://localhost:" + port + "/api/userdata";
    headers = new HttpHeaders();
    headers.setBasicAuth("admin@example.com", "admin");
  }

  @Test
  public void testGetAllUsers() {
    HttpEntity<String> entity = new HttpEntity<>(null, headers);
    ResponseEntity<List> response = restTemplate.exchange(baseUrl, HttpMethod.GET, entity, List.class);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
  }

  @Test
  public void testGetUserById() {
    HttpEntity<String> entity = new HttpEntity<>(null, headers);
    ResponseEntity<UserData> response = restTemplate.exchange(baseUrl + "/1", HttpMethod.GET, entity, UserData.class);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(1L, response.getBody().getId());
  }

  @Test
  public void testCreateUser() {
    UserData newUser = new UserData();
    newUser.setName("John Doe");
    newUser.setUsername("johndoe");
    newUser.setEmail("johndoe@example.com");

    HttpEntity<UserData> request = new HttpEntity<>(newUser, headers);
    ResponseEntity<UserData> response = restTemplate.exchange(baseUrl, HttpMethod.POST, request, UserData.class);
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals("John Doe", response.getBody().getName());
  }

  @Test
  public void testUpdateUser() {
    UserData updatedUser = new UserData();
    updatedUser.setId(1L);
    updatedUser.setName("Updated Name");
    updatedUser.setUsername("updatedusername");
    updatedUser.setEmail("updatedemail@example.com");

    HttpEntity<UserData> request = new HttpEntity<>(updatedUser, headers);
    restTemplate.exchange(baseUrl + "/1", HttpMethod.PUT, request, UserData.class);
    ResponseEntity<UserData> response = restTemplate.exchange(baseUrl + "/1", HttpMethod.GET, new HttpEntity<>(null, headers), UserData.class);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals("Updated Name", response.getBody().getName());
  }

  @Test
  public void testDeleteUser() {
    HttpEntity<String> entity = new HttpEntity<>(null, headers);
    restTemplate.exchange(baseUrl + "/1", HttpMethod.DELETE, entity, Void.class);
    ResponseEntity<UserData> response = restTemplate.exchange(baseUrl + "/1", HttpMethod.GET, entity, UserData.class);
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void testUnauthorizedAccessForViewer() {
    HttpHeaders viewerHeaders = new HttpHeaders();
    viewerHeaders.setBasicAuth("viewer@example.com", "password");
    UserData newUserData = new UserData(1L, "Unauthorized Post Creation", "username", "email", null, "phone", "website", null);
    HttpEntity<UserData> request = new HttpEntity<>(newUserData, viewerHeaders);
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
    UserData newUserData = new UserData(1L, "Unauthorized Post Creation", "username", "email", null, "phone", "website", null);
    HttpEntity<UserData> request = new HttpEntity<>(newUserData, viewerHeaders);
    ResponseEntity<Void> responsePOST = restTemplate.exchange(baseUrl, HttpMethod.POST, request, Void.class);
    ResponseEntity<Void> responseDELETE = restTemplate.exchange(baseUrl, HttpMethod.DELETE, request, Void.class);
    ResponseEntity<Void> responsePUT = restTemplate.exchange(baseUrl, HttpMethod.PUT, request, Void.class);

    assertNotEquals(HttpStatus.UNAUTHORIZED, responsePOST.getStatusCode());
    assertNotEquals(HttpStatus.UNAUTHORIZED, responseDELETE.getStatusCode());
    assertNotEquals(HttpStatus.UNAUTHORIZED, responsePUT.getStatusCode());
  }
}
