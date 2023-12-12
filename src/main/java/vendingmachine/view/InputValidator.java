package vendingmachine.view;

public class InputValidator {
    private static final String INVALID_NUMBER_FORMAT_MESSAGE = "숫자 형식 입력이 아닙니다.";

    public static int validateVendingMachineMoney(String input) {
        return convertStringToInteger(input);
    }

    private static int convertStringToInteger(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INVALID_NUMBER_FORMAT_MESSAGE);
        }
    }
}
