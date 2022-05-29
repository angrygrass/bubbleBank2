package nl.hva.miw.c27.team1.cryptobanking.utilities.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "The bank does not have sufficient assets in stock.")
public class InsufficientBankCryptoBalanceException extends RuntimeException {
}
