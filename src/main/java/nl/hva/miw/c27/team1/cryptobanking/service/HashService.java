package nl.hva.miw.c27.team1.cryptobanking.service;

import nl.hva.miw.c27.team1.cryptobanking.model.Profile;
import nl.hva.miw.c27.team1.cryptobanking.repository.repository.RootRepository;
import nl.hva.miw.c27.team1.cryptobanking.utilities.authentication.HashHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HashService {
    private static final int DEFAULT_ROUNDS = 2;
    private final PepperService pepperService;
    private final RootRepository rootRepository;
    private final int rounds;

    @Autowired
    public HashService(PepperService pepperService, RootRepository rootRepository) {
        this(pepperService, rootRepository, DEFAULT_ROUNDS);
    }

    public HashService(PepperService pepperService, RootRepository rootRepository, int rounds) {
        this.pepperService = pepperService;
        this.rootRepository = rootRepository;
        this.rounds = rounds;
    }

    public String hash(String password, String salt, String pepper) {
        String hash = HashHelper.hash(password, salt, pepperService.getPepper());
        //key stretch
        return processRounds(hash, numberOfRounds(rounds));
    }

    private String processRounds(String hash, long r) {
        for (long i = 0; i < r; i++) {
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

    public String getSaltByUserName(String username) {
        Profile profile = rootRepository.getProfileByUsername(username).orElse(null);
        if (profile != null) {
            return rootRepository.getProfileByUsername(username).orElse(null).getSalt();
        } else return "";
    }
}
