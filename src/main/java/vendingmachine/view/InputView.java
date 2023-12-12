package vendingmachine.view;

import vendingmachine.domain.Product;

import java.util.List;
import java.util.Scanner;

public class InputView {
    Scanner sc = new Scanner(System.in);

    public int inputVendingMachineMoney() {
        System.out.println("자판기가 보유하고 있는 금액을 입력해 주세요.");
        return InputValidator.validateVendingMachineMoney(sc.nextLine());
    }

    public List<Product> inputProducts() {
        System.out.println("\n상품명과 가격, 수량을 입력해 주세요.");
        return InputValidator.validateProducts(sc.nextLine());
    }

    public int inputAmount() {
        System.out.println("\n투입 금액을 입력해 주세요.");
        return InputValidator.validateAmount(sc.nextLine());
    }

    public String inputPurchaseProduct() {
        System.out.println("구매할 상품명을 입력해 주세요.");
        return InputValidator.validatePurchaseProduct(sc.nextLine());
    }
}
