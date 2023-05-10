package rentcar.rentcar.mappers;

import org.springframework.stereotype.Component;
import rentcar.rentcar.domain.DTO.UserDTO;
import rentcar.rentcar.domain.User;

@Component
public class UserMapper {
    public User mapUserDTOtoUser(UserDTO userDTO) {
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setLogin(userDTO.getLogin());
        user.setPhoneNumber(user.getPhoneNumber());
        return user;
    }

    public UserDTO mapUserToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        return userDTO;
    }
}
