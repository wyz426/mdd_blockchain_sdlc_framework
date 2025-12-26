import javax.swing.*;
import java.awt.event.*;

public class BPMNWindow extends ModeWindow {

    public BPMNWindow() {
        super("BPMN Mode");
    }

    @Override
    protected void onValidate(ActionEvent e) {
        // Call the BPMN model validation logic
        System.out.println("Validating BPMN Model...");
    }

    @Override
    protected void onReset(ActionEvent e) {
        inputField.setText("");
        outputField.setText("");
    }

    @Override
    protected void onGenerate(ActionEvent e) {
        // Call the BPMN model generation logic
        System.out.println("Generating BPMN Model...");
    }
}
