public class ValidateButtonDecorator extends ButtonDecorator {

    public ValidateButtonDecorator(JButton button) {
        super(button);
    }

    @Override
    public void executeAction(ActionEvent e) {
        System.out.println("Validating...");
    
    }
}