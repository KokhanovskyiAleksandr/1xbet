package ua.xbet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BettingControllerTest {

    @LocalServerPort
    private int port;

    private String baseUrl;

    @BeforeEach
    public void setUp() {
        baseUrl = "http://localhost:" + port + "/bet";
    }

    @Test
    public void betMoneyInvalidCarMarkTest() {
        TestRestTemplate restTemplate = new TestRestTemplate();
        Bet bet = new Bet("BUGATTI", 100);

        String result = restTemplate.postForObject(baseUrl, bet, String.class);

        assertEquals("You can only bet on cars that are in the competition", result);
    }

    @Test
    @DirtiesContext
    public void betMoneyTest() {
        TestRestTemplate restTemplate = new TestRestTemplate();
        Bet bet = new Bet("FERRARI", 100);

        String result = restTemplate.postForObject(baseUrl, bet, String.class);
        assertEquals("OK", result);
    }

    @Test
    @DirtiesContext
    public void showBankForAllCarsTest() {
        TestRestTemplate restTemplate = new TestRestTemplate();
        Bet bet = new Bet("Ferrari", 100);

        restTemplate.postForObject(baseUrl, bet, String.class);
        int totalBank = restTemplate.getForObject(baseUrl, Integer.class);

        assertEquals(100, totalBank);
    }

    @Test
    @DirtiesContext
    public void showBankForSpecificCarTest() {
        TestRestTemplate restTemplate = new TestRestTemplate();

        Bet ferrariBet = new Bet("Ferrari", 100);
        Bet bmwBet = new Bet("BMW", 10);

        restTemplate.postForObject(baseUrl, ferrariBet, String.class);
        restTemplate.postForObject(baseUrl, bmwBet, String.class);

        int bankForFerrari = restTemplate.getForObject(baseUrl + "?carMark=FERRARI", Integer.class);
        int bankForBMW = restTemplate.getForObject(baseUrl + "?carMark=BMW", Integer.class);

        assertEquals(100, bankForFerrari);
        assertEquals(10, bankForBMW);
    }
}