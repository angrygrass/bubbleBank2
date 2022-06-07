package nl.hva.miw.c27.team1.cryptobanking.utilities.validation;

import nl.hva.miw.c27.team1.cryptobanking.model.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestBSNValidator {

    @Test
    public void checkBsn_has_valid_bsn_should_return_true(){
        User user = Mockito.mock(User.class);
        /**
         * 318525227
         * 184373025
         * 822101683
         * 782308107
         * 927652900
         * 921291875
         * 768871311
         * 126423866
         * 298250524
         * 457287123
         */
        var validList = List.of(318525227,184373025,822101683,782308107, 927652900, 921291875, 768871311, 126423866, 298250524, 457287123);
        for(Integer bsn: validList){
            Mockito.when(user.getBsnNumber()).thenReturn(bsn); //  valid bsn
            assertTrue(BSNValidator.checkBsn(user));
        }

    }

    @Test
    public void checkBsn_has_invalid_bsn_should_return_true(){
        User user = Mockito.mock(User.class);
        /**
         318 ,81843730 ,88221016 ,878230817,892765200 ,892129875,878871311,12642866 ,82980524 ,57287,123
         */
        var validList = List.of(   318 ,81843730 ,88221016 ,878230817,892765200 ,892129875,878871311,12642866 ,82980524 ,57287,123);
        for(Integer bsn: validList){
            Mockito.when(user.getBsnNumber()).thenReturn(bsn); // an invalid bsn
            assertFalse(BSNValidator.checkBsn(user));
        }

    }

}