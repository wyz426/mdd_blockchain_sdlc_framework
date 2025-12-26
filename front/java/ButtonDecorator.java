public abstract class ButtonDecorator extends JButton {
    protected JButton button;

    public ButtonDecorator(JButton button) {
        this.button = button;
    }

    public void addActionListener(ActionListener listener) {
        button.addActionListener(listener);
    }

    public abstract void executeAction(ActionEvent e);
}