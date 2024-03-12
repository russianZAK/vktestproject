package by.russianzak.vktestproject.jsonplaceholder.album;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Album {
  private Long userId;
  private Long id;
  private String title;
}
