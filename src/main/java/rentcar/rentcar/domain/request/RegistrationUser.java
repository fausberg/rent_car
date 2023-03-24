package rentcar.rentcar.domain.request;

import lombok.Data;

@Data
public class RegistrationUser {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String login;
    private String password;
}
