// Created by huub
// Creation date 2021-03-20

package nl.hva.miw.c27.team1.cryptobanking.utilities.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "Username already registered.")
public class RegistrationFailedException extends RuntimeException {}
