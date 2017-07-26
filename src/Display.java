import java.util.Observable;

/**
 * Specifies what the display system should do. Note that the implementation has
 * a lot of freedom to choose its display.
 */
public abstract class Display extends Observable {
	static Display display;
	static RoomContext roomContext;
	static CoolerContext fridge;
	static CoolerContext freezer;

	/**
	 * Do the initializations to make the context an observer
	 */
	public abstract void initialize();

	public abstract void displayFridgeTemp(int time);
	
	public abstract void displayFreezerTemp(int time);

	/**
	 * Indicate that the light is on
	 */
	public abstract void turnLightOn(CoolerContext coolerContext);

	/**
	 * Indicate that the light is off
	 */
	public abstract void turnLightOff(CoolerContext coolerContext);

	/**
	 * Indicate that the cooling is on
	 */
	public abstract void turnCoolingOn(CoolerContext coolerContext);

	/**
	 * Indicate that the cooling is off
	 */
	public abstract void turnCoolingOff(CoolerContext coolerContext);

	/**
	 * Display temp
	 */
	public abstract void displayTemp(CoolerContext coolerContext);
}