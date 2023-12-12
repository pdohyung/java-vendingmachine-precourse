package vendingmachine.view;

import vendingmachine.domain.Product;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class InputValidator {
    private static final String INVALID_NUMBER_PATTERN = "^[1-9]\\d*$";
    private static final String INVALID_NUMBER_FORMAT_MESSAGE = "올바른 숫자 형식 입력이 아닙니다.";
    private static final String INVALID_PRICE_FORMAT_MESSAGE = "상품 가격은 100원부터 시작하며, 10원으로 나누어떨어져야 한다.";

    public static int validateVendingMachineMoney(String input) {
        validateNumberPattern(input);
        return convertStringToInteger(input);
    }

    public static List<Product> validateProducts(String input) {
        List<String> products = splitProductsInput(input);

        return products.stream()
                .map(InputValidator::convertStringToProduct)
                .collect(Collectors.toList());
    }

    public static int validateAmount(String input) {
        return convertStringToInteger(input);
    }

    public static String validatePurchaseProduct(String input) {
        return input;
    }

    public static Product convertStringToProduct(String productString) {
        String[] parts = productString.replace("[", "").replace("]", "").split(",");
        String name = parts[0];
        validateProductPrice(Integer.parseInt(parts[1]));
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

    private static void validateNumberPattern(String input) {
        if (!Pattern.matches(INVALID_NUMBER_PATTERN, input)) {
            throw new IllegalArgumentException(INVALID_NUMBER_FORMAT_MESSAGE);
        }
    }

    private static void validateProductPrice(int price) {
        if (price < 100 || (price % 10) != 0) {
            throw new IllegalArgumentException(INVALID_PRICE_FORMAT_MESSAGE);
        }
    }
}