package rentcar.rentcar.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @NotBlank
    @Size(max = 8)
    private String firstName;

    @NotBlank
    @Size(max = 10)
    private String lastName;

    @NotBlank
    @Pattern(regexp = "(\\+?375)([0-9]{9})")
    private String phoneNumber;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String login;

    @NotBlank
    private String password;
}
