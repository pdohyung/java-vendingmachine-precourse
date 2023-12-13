package vendingmachine;

import vendingmachine.controller.VendingMachineController;
import vendingmachine.view.OutputView;

public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현
        OutputView outputView = new OutputView();
        VendingMachineController vendingMachineController = new VendingMachineController();

        while (true)
            try {
                vendingMachineController.run();
                break;
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
    }
}
