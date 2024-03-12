package by.russianzak.vktestproject.audit;

import java.util.List;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Document(indexName = "requests-audits")
public class RequestAudit {
  @Id
  private String id;

  @Field(type = FieldType.Keyword, name = "username")
  private String username;

  @Field(type = FieldType.Keyword, name = "timeStamp")
  private String timeStamp;

  @Field(type = FieldType.Boolean, name = "isAuthenticated")
  private Boolean isAuthenticated;

  @Field(type = FieldType.Text, name = "requestURL")
  private String requestURL;

  @Field(type = FieldType.Text, name = "requestParams")
  private List<String> requestParams;

  @Field(type = FieldType.Text, name = "pathVariables")
  private List<String> pathVariables;

  @Field(type = FieldType.Keyword, name = "HTTPMethod")
  private String HTTPMethod;

  @Field(type = FieldType.Keyword, name = "access")
  private String access;
}
