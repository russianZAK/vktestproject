package by.russianzak.vktestproject.jsonplaceholder.album;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/albums")
@Api(tags = "Album Controller")
@RequiredArgsConstructor
public class AlbumController {

  private final AlbumService albumService;

  @GetMapping
  @ApiOperation(value = "Get all albums", notes = "Retrieve a list of all albums")
  public ResponseEntity<List<Album>> getAllAlbums() {
    return new ResponseEntity<>(albumService.getAllAlbums(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  @ApiOperation(value = "Get album by ID", notes = "Retrieve an album by its ID")
  public ResponseEntity<Album> getAlbumById(@PathVariable Long id) {
    return new ResponseEntity<>(albumService.getAlbumById(id), HttpStatus.OK);
  }

  @PostMapping
  @ApiOperation(value = "Create new album", notes = "Create a new album")
  public ResponseEntity<Album> createAlbum(@RequestBody Album album) {
    return new ResponseEntity<>(albumService.createAlbum(album), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  @ApiOperation(value = "Update album", notes = "Update an existing album")
  public ResponseEntity<Album> updateAlbum(@PathVariable Long id, @RequestBody Album album) {
    return new ResponseEntity<>(albumService.updateAlbum(id, album), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  @ApiOperation(value = "Delete album", notes = "Delete an existing album")
  public ResponseEntity<Void> deleteAlbum(@PathVariable Long id) {
    albumService.deleteAlbum(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
