package vendingmachine.controller;

import camp.nextstep.edu.missionutils.Randoms;
import vendingmachine.domain.Coin;
import vendingmachine.domain.Product;
import vendingmachine.view.InputView;
import vendingmachine.view.OutputView;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.stream.Collectors;

public class VendingMachineController {
    private final InputView inputView;
    private final OutputView outputView;

    public VendingMachineController() {
        this.inputView = new InputView();
        this.outputView = new OutputView();
    }

    public void run() {
        int vendingMachineMoney = inputView.inputVendingMachineMoney();
        EnumMap<Coin, Integer> coins = coinGeneration(vendingMachineMoney);
        outputView.printCoinsHaveVendingMachine(coins);
        List<Product> products = inputView.inputProducts();
        int amount = inputView.inputAmount();
        amount = buying(products, amount);
        EnumMap<Coin, Integer> change = change(coins, amount, vendingMachineMoney);
        outputView.printAmount(amount);
        outputView.printChanges(change);
    }

    public EnumMap<Coin, Integer> coinGeneration(int vendingMachineMoney) {
        EnumMap<Coin, Integer> coins = new EnumMap<>(Coin.class);
        for (Coin coin : Coin.values()) {
            coins.put(coin, 0);
        }
        int leftMoney = vendingMachineMoney;
        do {
            Coin randomCoin = randomChoice(leftMoney);
            coins.put(randomCoin, coins.getOrDefault(randomCoin, 0) + 1);
            leftMoney -= randomCoin.getAmount();

        } while (leftMoney != 0);
        return coins;
    }

    public int buying(List<Product> products, int amount) {
        while (true) {
            outputView.printAmount(amount);
            String purchaseProduct = inputView.inputPurchaseProduct();
            Product product = findProduct(products, purchaseProduct);
            if (product.getQuantity() == 0) {
                break;
            }
            amount -= product.getPrice();
            if (checkMinimumProductPrice(products, amount)) {
                break;
            }
        }
        return amount;
    }

    public EnumMap<Coin, Integer> change(EnumMap<Coin, Integer> coins, int amount, int vendingMachineMoney) {
        EnumMap<Coin, Integer> change = new EnumMap<>(Coin.class);
        int changeAmount = amount;
        for (Coin coin : coins.keySet()) {
            int numberOfCoins = changeAmount / coin.getAmount();
            if (numberOfCoins == 0 || coins.get(coin) == 0) {
                continue;
            }
            if (numberOfCoins > coins.get(coin)) {
                change.put(coin, coins.get(coin));
                changeAmount -= numberOfCoins * coin.getAmount();
                continue;
            }
            change.put(coin, numberOfCoins);
            changeAmount -= numberOfCoins * coin.getAmount();
        }
        if (totalChange(coins) > totalChange(change)) {
            return coins;
        }
        return change;
    }

    private int totalChange(EnumMap<Coin, Integer> change) {
        return change.keySet().stream()
                .mapToInt(c -> c.getAmount() * change.get(c))
                .sum();
    }

    private Product findProduct(List<Product> products, String purchaseProduct) {
        return products.stream()
                .filter(product -> product.getName().equals(purchaseProduct))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    private boolean checkMinimumProductPrice(List<Product> products, int amount) {
        int min = products.stream()
                .mapToInt(Product::getPrice)
                .min()
                .orElse(0);
        return min > amount;
    }

    private Coin randomChoice(int leftMoney) {
        List<Integer> amounts = Arrays.stream(Coin.values())
                .map(Coin::getAmount)
                .collect(Collectors.toList());
        while (true) {
            int randomAmount = Randoms.pickNumberInList(amounts);

            if ((leftMoney - randomAmount) >= 0) {
                return findCoin(randomAmount);
            }
        }
    }

    private Coin findCoin(int amount) {
        return Arrays.stream(Coin.values())
                .filter(coin -> coin.getAmount() == amount)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}