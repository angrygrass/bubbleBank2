package nl.hva.miw.c27.team1.cryptobanking.utilities.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Username/password combination not found.")
public class NoSuchUserPasswordCombination extends RuntimeException {
}