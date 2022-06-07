package nl.hva.miw.c27.team1.cryptobanking.repository.dao;

import nl.hva.miw.c27.team1.cryptobanking.model.Transaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class JdbcTransactionDaoTest {



    private TransactionDao daoUnderTest;

    private Transaction transaction1;
    private Transaction transaction2;
    private List<Transaction> sellerHistoryCustomer1;
    private List<Transaction> buyerHistoryCustomer1;
    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    public JdbcTransactionDaoTest(TransactionDao daoUnderTest) {
        super();
        this.daoUnderTest = daoUnderTest;
    }

//    public Transaction(int transactionId, double quantity, double rateInEuro,
//                       LocalDateTime dateTime, double transactionCosts,
//                       int buyerId, int sellerId, String assetCode) {
//    }
    @BeforeEach
    void setupTest() {
        transaction1 = new Transaction(1, 33.0, 1.02,
                LocalDateTime.parse("2022-03-30 11:20:34", FORMATTER), 98, 1, 1, "EUR");
        transaction2 = new Transaction(2, 35.0, 1.08,
                LocalDateTime.parse("2022-06-30 11:20:34", FORMATTER), 198, 5, 3, "EUR");
        sellerHistoryCustomer1 = new ArrayList<>();
        sellerHistoryCustomer1.add(transaction1);
        buyerHistoryCustomer1 = new ArrayList<>();
        buyerHistoryCustomer1.add(transaction2);
    }



    @AfterEach
    void tearDown() {
    }

    @Test
    void findById() {
        Optional<Transaction> actual = daoUnderTest.findById(1);
        Transaction expected = transaction1;
        assertThat(actual).isNotNull().isEqualTo(expected);
    }

//    @Test
//    void save() {
//    }
//
//    @Test
//    void getAll() {
//    }
}