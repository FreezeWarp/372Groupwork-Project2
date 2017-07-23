/**
 * Super class for all fridge states.
 * 
 *
 */
public abstract class FridgeState extends CoolerState {
	protected static FridgeContext context;
	protected static Display display;

	/**
	 * Initialzies the context and display
	 */
	protected FridgeState() {
		context = FridgeContext.instance();
		display = context.getDisplay();
	}
}
