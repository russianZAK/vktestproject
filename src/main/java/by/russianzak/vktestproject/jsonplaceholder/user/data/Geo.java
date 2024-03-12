package by.russianzak.vktestproject.jsonplaceholder.user.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Geo {
  private String lat;
  private String lng;
}
