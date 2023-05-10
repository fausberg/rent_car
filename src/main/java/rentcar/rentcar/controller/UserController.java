package rentcar.rentcar.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rentcar.rentcar.domain.CreditCard;
import rentcar.rentcar.domain.DTO.CreditCardDTO;
import rentcar.rentcar.domain.DTO.FineDTO;
import rentcar.rentcar.domain.DTO.RentHistoryDTO;
import rentcar.rentcar.domain.DTO.UserDTO;
import rentcar.rentcar.domain.Fine;
import rentcar.rentcar.domain.RentHistory;
import rentcar.rentcar.domain.User;
import rentcar.rentcar.mappers.CreditCardMapper;
import rentcar.rentcar.mappers.FineMapper;
import rentcar.rentcar.mappers.RentHistoryMapper;
import rentcar.rentcar.mappers.UserMapper;
import rentcar.rentcar.service.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final UserService userService;

    private final UserMapper userMapper;

    private final CreditCardMapper creditCardMapper;

    private final FineMapper fineMapper;

    private final RentHistoryMapper rentHistoryMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper, CreditCardMapper creditCardMapper, FineMapper fineMapper, RentHistoryMapper rentHistoryMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.creditCardMapper = creditCardMapper;
        this.fineMapper = fineMapper;
        this.rentHistoryMapper = rentHistoryMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable int id) {
        User user = userService.getUserById(id);
        UserDTO userDTO = userMapper.mapUserToUserDTO(user);
        return new ResponseEntity<>(userDTO, user.getId() != 0 ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUser() {
        if (!userService.getAllUser().isEmpty()) {
            List<User> users = userService.getAllUser();
            List<UserDTO> userDTOS = new ArrayList<>();
            for (User user : users) {
                userDTOS.add(userMapper.mapUserToUserDTO(user));
            }
            return new ResponseEntity<>(userDTOS, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update")
    public ResponseEntity<HttpStatus> updateUser(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {
                log.warn(o.getDefaultMessage());
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (userService.updateUser(userMapper.mapUserDTOtoUser(userDTO))) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/update/admin")
    public ResponseEntity<HttpStatus> updateUserByAdmin(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError o : bindingResult.getAllErrors()) {
                log.warn(o.getDefaultMessage());
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (userService.updateUserByAdmin(userMapper.mapUserDTOtoUser(userDTO))) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<HttpStatus> deleteUser() {
        if (userService.deleteUser()) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUserById(@PathVariable int id) {
        if (userService.deleteUserById(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/credit-cards")
    public ResponseEntity<List<CreditCardDTO>> getAllUserCreditCards() {
        if (!userService.getAllCreditCardsByUserId().isEmpty()) {
            List<CreditCard> creditCards = userService.getAllCreditCardsByUserId();
            List<CreditCardDTO> creditCardDTOS = new ArrayList<>();
            for (CreditCard creditCard : creditCards) {
                creditCardDTOS.add(creditCardMapper.mapCreditCardToCreditCardDTO(creditCard));
            }
            return new ResponseEntity<>(creditCardDTOS, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/fines")
    public ResponseEntity<List<FineDTO>> getAllUserFines() {
        if (!userService.getAllFinesByUserId().isEmpty()) {
            List<Fine> fines = userService.getAllFinesByUserId();
            List<FineDTO> fineDTOS = new ArrayList<>();
            for (Fine fine : fines) {
                fineDTOS.add(fineMapper.mapFineToFineDTO(fine));
            }
            return new ResponseEntity<>(fineDTOS, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/rent-histories")
    public ResponseEntity<List<RentHistoryDTO>> getAllRentHistoryByUserId() {
        if (!userService.getAllRentHistoryByUserId().isEmpty()) {
            List<RentHistory> rentHistories = userService.getAllRentHistoryByUserId();
            List<RentHistoryDTO> rentHistoryDTOS = new ArrayList<>();
            for(RentHistory rentHistory : rentHistories) {
                rentHistoryDTOS.add(rentHistoryMapper.mapRentHistoryToRentHistoryDTO(rentHistory));
            }
            return new ResponseEntity<>(rentHistoryDTOS, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
