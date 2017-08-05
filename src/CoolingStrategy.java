/**
 * Any cooling strategy that updates every so often and can receive timer events.
 */
public interface CoolingStrategy {
    public void handle(Object arg);
}
