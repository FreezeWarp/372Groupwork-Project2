/**
 * Super class for all freezer states.
 * 
 *
 */
public abstract class FreezerState extends CoolerState {
	protected static FreezerContext context;
	protected static Display display;

	/**
	 * Initialzies the context and display
	 */
	protected FreezerState() {
		context = FreezerContext.instance();
		display = context.getDisplay();
	}
}
