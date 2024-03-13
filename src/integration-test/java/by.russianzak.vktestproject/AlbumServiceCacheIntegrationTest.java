package by.russianzak.vktestproject;

import by.russianzak.vktestproject.jsonplaceholder.album.Album;
import by.russianzak.vktestproject.jsonplaceholder.album.AlbumService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AlbumServiceCacheIntegrationTest {

  @Autowired
  private AlbumService albumService;

  @Test
  void testGetAllAlbums_CacheHit() {
    long startTimeFirstCall = System.nanoTime();
    List<Album> firstCallResult = albumService.getAllAlbums();
    long endTimeFirstCall = System.nanoTime();

    long startTimeSecondCall = System.nanoTime();
    List<Album> secondCallResult = albumService.getAllAlbums();
    long endTimeSecondCall = System.nanoTime();

    assertEquals(firstCallResult, secondCallResult);
    assertTrue((endTimeSecondCall - startTimeSecondCall) < (endTimeFirstCall - startTimeFirstCall));
  }

  @Test
  void testGetAlbumById_CacheHit() {
    Long id = 1L;

    long startTimeFirstCall = System.nanoTime();
    Album firstCallResult = albumService.getAlbumById(id);
    long endTimeFirstCall = System.nanoTime();

    long startTimeSecondCall = System.nanoTime();
    Album secondCallResult = albumService.getAlbumById(id);
    long endTimeSecondCall = System.nanoTime();

    assertEquals(firstCallResult, secondCallResult);
    assertTrue((endTimeSecondCall - startTimeSecondCall) < (endTimeFirstCall - startTimeFirstCall));
  }
}
