package vendingmachine.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import vendingmachine.domain.Coin;


import java.util.EnumMap;

public class VendingMachineControllerTest {

    @ParameterizedTest
    @ValueSource(ints = {450, 1500, 5400, 10200})
    void coinGeneration은_자판기_보유_금액에_따라_동전을_무작위로_생성한다(int vendingMachineMoney) {
        EnumMap<Coin, Integer> coins = new VendingMachineController().coinGeneration(vendingMachineMoney);

        int result = coins.keySet().stream()
                .mapToInt(coin -> coin.getAmount() * coins.get(coin))
                .sum();

        Assertions.assertThat(result).isEqualTo(vendingMachineMoney);
    }
}
