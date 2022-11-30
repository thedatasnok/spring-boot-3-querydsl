package cool.datasnok.repros.springboot3querydsl.projection;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserAccountProjection {
  private UUID id;
  private String fullName;
  private String username;
  private String email;
}
