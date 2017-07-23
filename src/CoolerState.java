/**
 * Created by joseph on 23/07/17.
 */
public abstract class CoolerState {
    protected static CoolerContext context;
    protected static Display display;

    /**
     * Initialzies the context and display
     */
    protected CoolerState() {
        context = CoolerContext.instance();
        display = context.getDisplay();
    }

    /**
     * Initializes the state
     */
    public abstract void run();

    /**
     * Handles an event
     *
     * @param event
     *            event to be processed
     */
    public abstract void handle(Object event);
}
