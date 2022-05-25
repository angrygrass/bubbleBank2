package nl.hva.miw.c27.team1.cryptobanking.utilities.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "Password needs to have at least 8 characters," +
                                                    "one digit [0-9], one upper/lower case, one special character")
public class RegistrationFailedExceptionPassWord extends RuntimeException {}
