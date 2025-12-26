import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public abstract class ModeWindow extends JFrame {
    protected JTextField inputField, outputField;
    protected JButton validateButton, resetButton, generateButton;

    public ModeWindow(String title) {
        setTitle(title);
        setLayout(new GridLayout(4, 2));

        inputField = new JTextField(20);
        outputField = new JTextField(20);

        validateButton = new JButton("Validate Models");
        resetButton = new JButton("Reset");
        generateButton = new JButton("Generate");

        add(new JLabel("Input File:"));
        add(inputField);
        add(new JLabel("Output File:"));
        add(outputField);
        add(validateButton);
        add(resetButton);
        add(generateButton);

        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Example actions for buttons, to be customized for each mode.
        validateButton.addActionListener(this::onValidate);
        resetButton.addActionListener(this::onReset);
        generateButton.addActionListener(this::onGenerate);
    }

    protected abstract void onValidate(ActionEvent e);
    protected abstract void onReset(ActionEvent e);
    protected abstract void onGenerate(ActionEvent e);
}
