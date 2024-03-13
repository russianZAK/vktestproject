package by.russianzak.vktestproject;

import by.russianzak.vktestproject.user.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerIntegrationTest {

  @LocalServerPort
  private int port;

  private String baseUrl;

  private RestTemplate restTemplate;

  @BeforeEach
  public void setUp() {
    baseUrl = "http://localhost:" + port + "/auth";
    restTemplate = new RestTemplate();
  }

  @Test
  public void testRegister_ValidData() {
    UserDTO userDTO = new UserDTO("testUser", "testPassword");

    ResponseEntity<Void> responseEntity = restTemplate.postForEntity(baseUrl + "/register", userDTO, Void.class);

    assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
  }

}
