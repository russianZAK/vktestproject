package by.russianzak.vktestproject.jsonplaceholder.user.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address{
  private String street;
  private String suite;
  private String city;
  private String zipcode;
  private Geo geo;
}

