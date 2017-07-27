import javafx.beans.property.BooleanProperty;

/**
 * Created by joseph on 26/07/17.
 */
public interface CoolingStrategy {
    public void processTimerTick();
    public void startCooling();
    public void stopCooling();
    public boolean isCooling();
    public BooleanProperty isCoolingProperty();
}
