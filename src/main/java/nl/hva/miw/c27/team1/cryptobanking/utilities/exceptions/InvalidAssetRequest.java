package nl.hva.miw.c27.team1.cryptobanking.utilities.exceptions;

import nl.hva.miw.c27.team1.cryptobanking.repository.repository.RootRepository;
import nl.hva.miw.c27.team1.cryptobanking.service.AssetService;
import nl.hva.miw.c27.team1.cryptobanking.utilities.validation.AgeValidator;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Objects;


@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class InvalidAssetRequest extends RuntimeException {


    public InvalidAssetRequest(String d) {
        super(d);

    }
}

