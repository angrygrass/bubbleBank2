package nl.hva.miw.c27.team1.cryptobanking.controller.api;

import nl.hva.miw.c27.team1.cryptobanking.model.User;
import nl.hva.miw.c27.team1.cryptobanking.service.AdminService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

//@RestController
@Controller
@RequestMapping(value=("/admins"))
public class AdminApiController extends BaseApiController {

    private final Logger logger = LogManager.getLogger(AdminApiController.class);
    private AdminService adminService;

    @Autowired
    public AdminApiController(AdminService adminService) {
        this.adminService = adminService;
        logger.info("New AdminApiController");
    }

    @PostMapping
    @ResponseBody
    ResponseEntity<?> createUser(@RequestBody User user) {
        adminService.saveUser(user);
        return ResponseEntity.ok(user);
    }

    @GetMapping(value="/find/{id}")
    User getUserById(@PathVariable("id") int id) {
        Optional<User> optUser = adminService.getUserById(id);
        if (optUser.isPresent()) {
            return optUser.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }

    @GetMapping(value="/find")
    User findUserByRole(@RequestParam("role") String role) {
        return adminService.getUserByRole(role);
    }

    @GetMapping("all_users")
    public List getAllUsers() {
        return adminService.getAllUsers();
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable("id") int id) {
        Optional<User> optUser = adminService.getUserById(id);
        if (optUser.isPresent()) {
            adminService.updateUser(user);
            return ResponseEntity.ok().body(user);
        } else {
            adminService.saveUser(user);
            return ResponseEntity.ok().body(user);
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteUser(@PathVariable("id") int id) {
        Optional<User> optUser = adminService.getUserById(id);
        if (optUser.isPresent()) {
            adminService.deleteUser(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // getters & setters
    public Logger getLogger() {
        return logger;
    }

    public AdminService getAdminService() {
        return adminService;
    }

    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }
}
