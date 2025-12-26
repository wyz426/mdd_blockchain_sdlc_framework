import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainWindow extends JFrame {

    private JButton d2cButton, c2scButton, bpmnButton;

    public MainWindow() {
        setTitle("Model Selection");
        setLayout(new FlowLayout());
        
        d2cButton = new JButton("D2C Mode");
        c2scButton = new JButton("C2SC Mode");
        bpmnButton = new JButton("BPMN Mode");

        d2cButton.addActionListener(e -> openWindow(Mode.D2C));
        c2scButton.addActionListener(e -> openWindow(Mode.C2SC));
        bpmnButton.addActionListener(e -> openWindow(Mode.BPMN));

        add(d2cButton);
        add(c2scButton);
        add(bpmnButton);

        setSize(300, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void openWindow(Mode mode) {
        ModeFactory factory = new ModeFactory();
        ModeWindow window = factory.createWindow(mode);
        window.setVisible(true);
    }

    public static void main(String[] args) {
        new MainWindow();
    }
}
