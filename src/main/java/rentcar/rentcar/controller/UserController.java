package rentcar.rentcar.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import rentcar.rentcar.domain.CreditCard;
import rentcar.rentcar.domain.Fine;
import rentcar.rentcar.domain.RentHistory;
import rentcar.rentcar.service.UserService;
import rentcar.rentcar.domain.User;

import java.util.ArrayList;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user, user.getId() != 0 ? HttpStatus.OK : HttpStatus.CONFLICT);
    }

    @GetMapping("all")
    public ResponseEntity<ArrayList<User>> getAllUser() {
        return new ResponseEntity<>(userService.getAllUser(), HttpStatus.OK);
    }

    @PutMapping("update")
    public ResponseEntity<HttpStatus> updateUser(@RequestBody User user, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            for(ObjectError o : bindingResult.getAllErrors()) {
                log.warn(o.getDefaultMessage());
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        userService.updateUser(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("delete")
    public ResponseEntity<HttpStatus> deleteUser(@RequestBody User user) {
        userService.deleteUser(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/cr")
    public ResponseEntity<ArrayList<CreditCard>> getAllUserCreditCards() {
        return new ResponseEntity<>(userService.getAllCreditCardsByUserId(), HttpStatus.OK);
    }

    @GetMapping("/fine")
    public ResponseEntity<ArrayList<Fine>> getAllUserFines() {
        return new ResponseEntity<>(userService.getAllFinesByUserId(), HttpStatus.OK);
    }

    @GetMapping("/rh")
    public ResponseEntity<ArrayList<RentHistory>> getAllRentHistoryByUserId() {
        return new ResponseEntity<>(userService.getAllRentHistoryByUserId(), HttpStatus.OK);
    }
}
