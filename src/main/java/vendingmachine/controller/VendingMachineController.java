package vendingmachine.controller;

import camp.nextstep.edu.missionutils.Randoms;
import vendingmachine.domain.Coin;
import vendingmachine.view.InputView;
import vendingmachine.view.OutputView;

import java.util.EnumMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class VendingMachineController {
    private final InputView inputView;
    private final OutputView outputView;

    public VendingMachineController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        int vendingMachineMoney = inputView.inputVendingMachineMoney();
        outputView.printCoinsHaveVendingMachine(coinGeneration(vendingMachineMoney));
    }

    public EnumMap<Coin, Integer> coinGeneration(int vendingMachineMoney) {
        EnumMap<Coin, Integer> coins = new EnumMap<>(Coin.class);
        int leftMoney = vendingMachineMoney;
        for (Coin coin : Coin.values()) {
            int randomNumber = randomChoice(leftMoney, coin);
            coins.put(coin, randomNumber);
            leftMoney -= randomNumber * coin.getAmount();
        }
        return coins;
    }

    private int randomChoice(int vendingMachineMoney, Coin coin) {
        int numberOfCoins = vendingMachineMoney / coin.getAmount();
        List<Integer> coinNumbers = createCoinList(numberOfCoins);
        if (Coin.COIN_10 == coin) {
            return vendingMachineMoney / coin.getAmount();
        }
        return Randoms.pickNumberInList(coinNumbers);
    }

    private List<Integer> createCoinList(int numberOfCoins) {
        return IntStream.rangeClosed(0, numberOfCoins)
                .boxed()
                .collect(Collectors.toList());
    }
}