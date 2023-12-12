package vendingmachine.controller;

import camp.nextstep.edu.missionutils.Randoms;
import vendingmachine.domain.Coin;
import vendingmachine.domain.Product;
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
        List<Product> products = inputView.inputProducts();
        int amount = inputView.inputAmount();
        amount = buying(products, amount);

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