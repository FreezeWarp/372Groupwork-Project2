import java.util.Observable;

/**
 * A CoolingStrategy that is also observable and thus sends events.
 */
public abstract class ObservableCoolingStrategy extends Observable implements CoolingStrategy {
    /**
     * Events sent from the cooling strategy.
     */
    public enum Events {
        /**
         * When cooling should be turned on.
         */
        COOLING_ACTIVATED,

        /**
         * When cooling should be turned off.
         */
        COOLING_DEACTIVATED
    };
}