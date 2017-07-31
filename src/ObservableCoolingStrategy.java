import java.util.Observable;

/**
 * Created by joseph on 31/07/17.
 */
public abstract class ObservableCoolingStrategy extends Observable implements CoolingStrategy {
    public enum Events {
        COOLING_ACTIVATED, COOLING_DEACTIVATED
    };
}