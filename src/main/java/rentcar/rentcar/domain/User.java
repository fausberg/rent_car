package rentcar.rentcar.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "u_seq")
    @SequenceGenerator(name = "u_seq", sequenceName = "users_s", allocationSize = 1)
    private int id;

    @NotBlank
    @Size(max = 8)
    @Column(name = "first_name")
    private String firstName;

    @NotBlank
    @Size(max = 10)
    @Column(name = "last_name")
    private String lastName;

    @NotBlank
    @Pattern(regexp = "(\\+?375)([0-9]{9})")
    @Column(name = "phone_number")
    private String phoneNumber;

    @NotBlank
    @Email
    @Column(name = "email")
    private String email;

    @NotBlank
    @Column(name = "login")
    private String login;

    @NotBlank
    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    @Column(name = "rent_history_start_time")
    private Timestamp rentHistoryStartTime;

}
