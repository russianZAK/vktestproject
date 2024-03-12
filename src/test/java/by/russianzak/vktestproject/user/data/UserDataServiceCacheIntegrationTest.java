package by.russianzak.vktestproject.user.data;

import by.russianzak.vktestproject.jsonplaceholder.user.data.UserData;
import by.russianzak.vktestproject.jsonplaceholder.user.data.UserDataService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserDataServiceCacheIntegrationTest {

  @Autowired
  private UserDataService userDataService;

  @Test
  void testGetAllUsers_CacheHit() {
    long startTimeFirstCall = System.nanoTime();
    List<UserData> firstCallResult = userDataService.getAllUsers();
    long endTimeFirstCall = System.nanoTime();

    long startTimeSecondCall = System.nanoTime();
    List<UserData> secondCallResult = userDataService.getAllUsers();
    long endTimeSecondCall = System.nanoTime();

    assertEquals(firstCallResult, secondCallResult);
    assertTrue((endTimeSecondCall - startTimeSecondCall) < (endTimeFirstCall - startTimeFirstCall));
  }

  @Test
  void testGetUserById_CacheHit() {
    Long id = 1L;

    long startTimeFirstCall = System.nanoTime();
    UserData firstCallResult = userDataService.getUserById(id);
    long endTimeFirstCall = System.nanoTime();

    long startTimeSecondCall = System.nanoTime();
    UserData secondCallResult = userDataService.getUserById(id);
    long endTimeSecondCall = System.nanoTime();

    assertEquals(firstCallResult, secondCallResult);
    assertTrue((endTimeSecondCall - startTimeSecondCall) < (endTimeFirstCall - startTimeFirstCall));
  }
}
