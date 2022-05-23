/*
package nl.hva.miw.c27.team1.cryptobanking.controller.web;

import nl.hva.miw.c27.team1.cryptobanking.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

@Controller
public class RegistrationUserController {

  private final Logger logger = LoggerFactory.getLogger(RegistrationUserController.class);

  public RegistrationUserController() {
    super();
    logger.info("New RegistrationController.");
  }

  @PostMapping("/new_user")
  public ResponseEntity<?> newUserHandler(
          @RequestParam(name="first_name") String firstName,
          @RequestParam(name="prefix") String prefix,
          @RequestParam(name="surname") String surName,
          @RequestParam(name="bsn_number") int bsnNumber,
          @RequestParam(name="date_of_birth") Date birthDate,
          @RequestParam(name="streetname") String streetName,
          @RequestParam(name="house_number") String houseNumber,
          @RequestParam(name="zipcode") String zipCode,
          @RequestParam(name="residence") String residence,
          @RequestParam(name="country") String country,
          @RequestParam(name="iban") String iban,
          @RequestParam(name="user_name") String userName,
          @RequestParam(name="password") String passWord) {

    User newUser = new Customer(firstName,prefix,surName,bsnNumber,birthDate,streetName,
                  houseNumber,zipCode,residence, country, new Profile(userName,passWord),
                  new BankAccount(iban), new Portfolio(), new ArrayList<>()); // correct?
    // niet zeker of onderstaande correct is
    // UserService.save(user) ?
    return ResponseEntity.ok().body(newUser);
  }
}
*/
