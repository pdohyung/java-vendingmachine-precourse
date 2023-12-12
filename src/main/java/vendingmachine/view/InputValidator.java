package vendingmachine.view;

import vendingmachine.domain.Product;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InputValidator {
    private static final String INVALID_NUMBER_FORMAT_MESSAGE = "숫자 형식 입력이 아닙니다.";

    public static int validateVendingMachineMoney(String input) {
        return convertStringToInteger(input);
    }

    public static List<Product> validateProducts(String input) {
        List<String> products = splitProductsInput(input);

        return products.stream()
                .map(InputValidator::convertStringToProduct)
                .collect(Collectors.toList());
    }

    public static Product convertStringToProduct(String productString) {
        String[] parts = productString.replace("[", "").replace("]", "").split(",");
        String name = parts[0];
        int price = Integer.parseInt(parts[1]);
        int quantity = Integer.parseInt(parts[2]);
        return new Product(name, price, quantity);
    }

    private static List<String> splitProductsInput(String input) {
        return Arrays.stream(input.split(";"))
                .collect(Collectors.toList());
    }

    private static int convertStringToInteger(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INVALID_NUMBER_FORMAT_MESSAGE);
        }
    }
}
