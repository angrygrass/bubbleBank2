package nl.hva.miw.c27.team1.cryptobanking.utilities.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "This is not a valid entry. Our available coins are " +
        "bitcoin, solana, ethereum, flow,cardano, dai, apecoin, tether, gas, tron, polkadot, waves, loopring, dogecoin," +
        " tezos, chiliz, eos, uma, radix & kusama")
public class InvalidAssetRequest extends RuntimeException {}

