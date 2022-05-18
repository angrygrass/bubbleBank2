package nl.hva.miw.c27.team1.cryptobanking.controller.api;

import nl.hva.miw.c27.team1.cryptobanking.utilities.exceptions.NoSuchUserException;
import nl.hva.miw.c27.team1.cryptobanking.utilities.exceptions.UserExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

public abstract class BaseApiController {


    @ExceptionHandler(NoSuchUserException.class)
    public String handleNoSuchUserException() {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No such member");
    }

    @ExceptionHandler(UserExistsException.class)
    public  String handleUserExistsException() {
        return "User already exits";
    }

}
