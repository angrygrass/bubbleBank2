package nl.hva.miw.c27.team1.cryptobanking.controller.api;

import nl.hva.miw.c27.team1.cryptobanking.model.User;
import nl.hva.miw.c27.team1.cryptobanking.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

// should this Controller also handle login and registration, or
// under ProfileApiController?
@RestController
@RequestMapping(value=("/users"))
public class UserApiController extends BaseApiController {

    private final Logger logger = LogManager.getLogger(UserApiController.class);
    private UserService userService;

    @Autowired
    public UserApiController(UserService userService) {
        this.userService = userService;
        logger.info("New UserApiController");
    }

    // to do: register(UserDto userDto) (or under Profile?)

    @PostMapping
    @ResponseBody
    ResponseEntity<?> createUser(@RequestBody User user) {
        userService.saveUser(user);
        return ResponseEntity.ok(user);
    }

    @GetMapping(value="/find/{id}")
    User getUserById(@PathVariable("id") int id) {
        Optional<User> optUser = userService.getUserById(id);
        if (optUser.isPresent()) {
            return optUser.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }

    @GetMapping(value="/find")
    User findUserByRole(@RequestParam("role") String role) {
        return userService.getUserByRole(role);
    }

    @GetMapping("all_users")
    public List getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable("id") int id) {
        Optional<User> optUser = userService.getUserById(id);
        if (optUser.isPresent()) {
            userService.updateUser(user);
            return ResponseEntity.ok().body(user);
        } else {
            userService.saveUser(user);
            return ResponseEntity.ok().body(user);
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteUser(@PathVariable("id") int id) {
        Optional<User> optUser = userService.getUserById(id);
        if (optUser.isPresent()) {
            userService.deleteUser(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // getters & setters
    public Logger getLogger() {
        return logger;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }


}
