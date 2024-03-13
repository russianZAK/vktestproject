package by.russianzak.vktestproject;

import by.russianzak.vktestproject.jsonplaceholder.post.Post;
import by.russianzak.vktestproject.jsonplaceholder.post.PostService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostServiceCacheIntegrationTest {

  @Autowired
  private PostService postService;

  @Test
  void testGetAllPosts_CacheHit() {
    long startTimeFirstCall = System.nanoTime();
    List<Post> firstCallResult = postService.getAllPosts();
    long endTimeFirstCall = System.nanoTime();

    long startTimeSecondCall = System.nanoTime();
    List<Post> secondCallResult = postService.getAllPosts();
    long endTimeSecondCall = System.nanoTime();

    assertEquals(firstCallResult, secondCallResult);
    assertTrue((endTimeSecondCall - startTimeSecondCall) < (endTimeFirstCall - startTimeFirstCall));
  }

  @Test
  void testGetPostById_CacheHit() {
    Long id = 1L;

    long startTimeFirstCall = System.nanoTime();
    Post firstCallResult = postService.getPostById(id);
    long endTimeFirstCall = System.nanoTime();

    long startTimeSecondCall = System.nanoTime();
    Post secondCallResult = postService.getPostById(id);
    long endTimeSecondCall = System.nanoTime();

    assertEquals(firstCallResult, secondCallResult);
    assertTrue((endTimeSecondCall - startTimeSecondCall) < (endTimeFirstCall - startTimeFirstCall));
  }
}
