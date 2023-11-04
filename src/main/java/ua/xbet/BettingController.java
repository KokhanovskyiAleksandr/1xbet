package ua.xbet;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/bet")
public class BettingController {

    private final Map<String, Integer> participants = new ConcurrentHashMap<>() {{
        put("FERRARI", 0);
        put("BMW", 0);
        put("AUDI", 0);
        put("HONDA", 0);
    }};

    @PostMapping
    public String betMoney(@RequestBody Bet bet) {
        if (isCarNotParticipatingInTheCompetition(bet)) {
            return "You can only bet on cars that are in the competition";
        }

        participants.compute(bet.getCarMark().toUpperCase(), (key, value) -> value + bet.getAmountOfMoney());
        return "OK";
    }

    private boolean isCarNotParticipatingInTheCompetition(Bet bet) {
        Optional<String> carMarkOptional = Optional.of(bet).map(Bet::getCarMark).map(String::toUpperCase);
        return carMarkOptional.isEmpty() || !participants.containsKey(carMarkOptional.get());
    }

    @GetMapping
    public Integer showBank(@RequestParam(defaultValue = "all") String carMark) {
        String carMarkFormatted = carMark.toUpperCase();
        if (!participants.containsKey(carMarkFormatted)) {
            int totalBank = 0;
            for (Integer i : participants.values()) {
                totalBank += i;
            }
            return totalBank;
        }
        return participants.get(carMarkFormatted);
    }
}
