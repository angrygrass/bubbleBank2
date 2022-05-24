package nl.hva.miw.c27.team1.cryptobanking.utilities.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "Password needs to have at least 8 characters," +
                                                    "and at least one upper case and one special character")
public class RegistrationFailedExceptionPassWord extends RuntimeException {}
