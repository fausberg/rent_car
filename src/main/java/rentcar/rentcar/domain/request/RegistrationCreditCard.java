package rentcar.rentcar.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationCreditCard {
    @NotBlank
    @Pattern(regexp = "[0-9]{16}")
    private String cardNumber;

    @NotBlank
    @Pattern(regexp = "(0[1-9]|1[0-2])/([0-9]{2})")
    private String date;

    @NotBlank
    @Pattern(regexp = "[0-9]{3}")
    private String CVV;
}
