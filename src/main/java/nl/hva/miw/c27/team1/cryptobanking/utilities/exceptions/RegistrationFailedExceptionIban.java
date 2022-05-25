package nl.hva.miw.c27.team1.cryptobanking.utilities.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "You need to have a valid IBAN in order to register.")
public class RegistrationFailedExceptionIban extends RuntimeException {}
