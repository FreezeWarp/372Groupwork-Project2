import java.util.Observable;

/**
 * Specifies what the display system should do. Note that the implementation has
 * a lot of freedom to choose its display.
 */
public abstract class Display extends Observable {
	protected static FridgeContext contextFridge;
	protected static FreezerContext contextFreezer;
	protected static Display instance;

	/**
	 * Initializes the context and instance
	 */
	protected Display() {
		instance = this;
		contextFridge = FridgeContext.instance();
		contextFreezer = FreezerContext.instance();
	}

	/**
	 * For singleton
	 * 
	 * @return the object
	 */
	public static Display instance() {
		return instance;
	}

	/**
	 * Do the initializations to make the context an observer
	 */
	public void initialize() {
		instance().addObserver(contextFridge);
		instance().addObserver(contextFreezer);
		contextFridge.initialize();
		contextFreezer.initialize();
	}

	public abstract void displayFridgeTemp(int time);
	
	public abstract void displayFreezerTemp(int time);

	/**
	 * Indicate that the light is on
	 */
	public abstract void turnFridgeLightOn();

	/**
	 * Indicate that the light is off
	 */
	public abstract void turnFridgeLightOff();
	/**
	 * Indicate that the light is on
	 */
	
	public abstract void turnFreezerLightOn();

	/**
	 * Indicate that the light is off
	 */
	public abstract void turnFreezerLightOff();
}