package ua.koniukh.cargomanagementsystem.model.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

  @NotEmpty(message = "Username should not be empty")
  @Size(min = 2, max = 18, message = "Username should be between 2 and 18 characters")
  @Column(name = "user_name")
  private String username;

  @Size(min = 6, max = 20, message = "Password should be between 6 and 20 characters")
  @NotEmpty(message = "Password should not be empty")
  private String password;

  //    @Email(message = "Please write a valid email")
  //    private String email;

}
