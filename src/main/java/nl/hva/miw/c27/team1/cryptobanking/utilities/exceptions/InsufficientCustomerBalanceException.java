package nl.hva.miw.c27.team1.cryptobanking.utilities.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "The customer does not have sufficient balance to buy this quantity.")
public class InsufficientCustomerBalanceException extends RuntimeException {
}
