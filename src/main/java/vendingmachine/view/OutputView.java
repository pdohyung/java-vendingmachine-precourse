package vendingmachine.view;

import vendingmachine.domain.Coin;

import java.text.MessageFormat;
import java.util.EnumMap;

public class OutputView {
    private final String PRINT_COIN_FORMAT = "{0}원 - {1}개";

    public void printCoinsHaveVendingMachine(EnumMap<Coin, Integer> coins) {
        coins.keySet().forEach(coin ->
                System.out.println(MessageFormat.format(PRINT_COIN_FORMAT, coin.getAmount(), coins.get(coin))));
    }
}
