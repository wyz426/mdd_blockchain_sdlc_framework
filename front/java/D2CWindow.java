import javax.swing.*;
import java.awt.event.*;

public class D2CWindow extends ModeWindow {

    public D2CWindow() {
        super("D2C Mode");
    }

    @Override
    protected void onValidate(ActionEvent e) {
        // Call the D2C model validation logic
        System.out.println("Validating D2C Model...");
    }

    @Override
    protected void onReset(ActionEvent e) {
        inputField.setText("");
        outputField.setText("");
    }

    @Override
    protected void onGenerate(ActionEvent e) {
        // Call the D2C model generation logic
        System.out.println("Generating D2C Model...");
    }
}
