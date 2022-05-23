package nl.hva.miw.c27.team1.cryptobanking.service;

import nl.hva.miw.c27.team1.cryptobanking.utilities.authentication.HashHelper;
import nl.hva.miw.c27.team1.cryptobanking.utilities.authentication.SaltMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HashService {
    private static final int DEFAULT_ROUNDS = 2;
    private final int DEFAULT_HASH_LENGTH = 64;
    private final PepperService pepperService;
    private final int rounds;

    @Autowired
    public HashService(PepperService pepperService) {
        this(pepperService, DEFAULT_ROUNDS);
    }

    public HashService(PepperService pepperService, int rounds) {
        this.pepperService = pepperService;
        this.rounds = rounds;
    }

    public String hash(String value) {
        String hash = HashHelper.hash(value, new SaltMaker(DEFAULT_HASH_LENGTH).generateSalt(), pepperService.getPepper());
        //key stretch
        return processRounds(hash, numberOfRounds(rounds));
    }

    private String processRounds(String hash, long r) {
        for (long i = 0; i < r; i++) {
            //todo jjs refactor to make method more efficient -->
            // niet zo efficient om dit met String te doen en HashHelper hash maakt ook steeds nieuwe objecten aan
            // wordt wel al heel snel erg traag
            hash = HashHelper.hash(hash);
        }
        return hash;
    }

    private long numberOfRounds(int load){
        int base = 10;
        long result = base; // base ^ 1

        for (int i = 0; i < load; i++) {
            result *= base;
        }
        return result;
    }
}
