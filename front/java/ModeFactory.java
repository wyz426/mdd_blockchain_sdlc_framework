
public class ModeFactory {
    public ModeWindow createWindow(Mode mode) {
        switch (mode) {
            case D2C:
                return new D2CWindow();
            case C2SC:
                return new C2SCWindow();
            case BPMN:
                return new BPMNWindow();
            default:
                throw new IllegalArgumentException("Unknown mode: " + mode);
        }
    }
}
