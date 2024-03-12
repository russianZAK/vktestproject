package by.russianzak.vktestproject.jsonplaceholder.post;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@Api(tags = "Post Controller")
@RequiredArgsConstructor
public class PostController {

  private final PostService postService;

  @GetMapping
  @ApiOperation(value = "Get all posts", notes = "Retrieve a list of all posts")
  public ResponseEntity<List<Post>> getAllPosts() {
    return new ResponseEntity<>(postService.getAllPosts(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  @ApiOperation(value = "Get post by ID", notes = "Retrieve a post by its ID")
  public ResponseEntity<Post> getPostById(@PathVariable Long id) {
    return new ResponseEntity<>(postService.getPostById(id), HttpStatus.OK);
  }

  @PostMapping
  @ApiOperation(value = "Create new post", notes = "Create a new post")
  public ResponseEntity<Post> createPost(@RequestBody Post post) {
    return new ResponseEntity<>(postService.createPost(post), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  @ApiOperation(value = "Update post", notes = "Update an existing post")
  public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post post) {
    return new ResponseEntity<>(postService.updatePost(id, post), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  @ApiOperation(value = "Delete post", notes = "Delete an existing post")
  public ResponseEntity<Void> deletePost(@PathVariable Long id) {
    postService.deletePost(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
