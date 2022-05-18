package nl.hva.miw.c27.team1.cryptobanking.controller.web;

import nl.hva.miw.c27.team1.cryptobanking.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationUserController {

  private final Logger logger = LoggerFactory.getLogger(RegistrationUserController.class);

  public RegistrationUserController() {
    super();
    logger.info("New RegistrationController.");
  }

  @PostMapping("/new_user")
  public String newUserHandler(
          @RequestParam(name="first_name") String firstName,
          @RequestParam(name="prefix") String prefix,
          @RequestParam(name="surname") String surName,
          @RequestParam(name="date_of_birth") String birthDate,
          @RequestParam(name="streetname") String streetName,
          @RequestParam(name="house_number") String houseNumber,
          @RequestParam(name="zipcode") String zipCode,
          @RequestParam(name="residence") String residence,
          @RequestParam(name="user_name") String userName,
          @RequestParam(name="password") String passWord,
          Model model) {

    User newUser = new User(); // new User needs attributes
    model.addAttribute("user", newUser);
    // or direct to profile?
    return "confirmation";
  }
}
