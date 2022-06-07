package nl.hva.miw.c27.team1.cryptobanking.utilities.validation;

import nl.hva.miw.c27.team1.cryptobanking.model.Customer;
import nl.hva.miw.c27.team1.cryptobanking.repository.repository.RootRepository;

public class NameValidator {
    private static RootRepository rootRepository;

    public static boolean checkUserName(Customer customer) {
        if (rootRepository.getProfileByUsername(customer.getProfile().getUserName()).orElse(null) != null) {
            return false;
        }
        return true;
    }
}
