package nl.hva.miw.c27.team1.cryptobanking.controller.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

  private final Logger logger = LoggerFactory.getLogger(HomeController.class);

  public HomeController() {
    super();
    logger.info("New HomeController.");
  }

  @GetMapping("/register")
  public String registerHandler() {
    return "registration";
  }
}
