package vendingmachine.view;

import vendingmachine.domain.Coin;

import java.text.MessageFormat;
import java.util.EnumMap;

public class OutputView {
    private final String PRINT_COIN_FORMAT = "{0}원 - {1}개";
    private final String PRINT_AMOUNT_FORMAT = "\n투입 금액: {0}원";
    private final String PRINT_COINS_HAVE_VENDING_MACHINE = "\n자판기가 보유한 동전";
    private final String PRINT_CHANGES = "잔돈";

    public void printCoinsHaveVendingMachine(EnumMap<Coin, Integer> coins) {
        System.out.println(PRINT_COINS_HAVE_VENDING_MACHINE);
        coins.keySet().forEach(coin ->
                System.out.println(MessageFormat.format(PRINT_COIN_FORMAT, coin.getAmount(), coins.get(coin))));
    }

    public void printAmount(int amount) {
        System.out.println(MessageFormat.format(PRINT_AMOUNT_FORMAT, amount));
    }

    public void printChanges(EnumMap<Coin, Integer> coins) {
        System.out.println(PRINT_CHANGES);
        coins.keySet().stream()
                .filter(coin -> coins.get(coin) != 0)
                .forEach(coin -> System.out.println(MessageFormat.format(PRINT_COIN_FORMAT, coin.getAmount(), coins.get(coin))));
    }
}