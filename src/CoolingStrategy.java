import javafx.beans.property.BooleanProperty;

/**
 * Any cooling strategy that updates every so often and can receive timer events.
 */
public interface CoolingStrategy {
    public void processTimerTick();
    public void startCooling();
    public void stopCooling();
    public boolean isCooling();
    public BooleanProperty isCoolingProperty();
    public void handle(Object arg);
}
