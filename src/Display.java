/**
 * Specifies what the display system should do. Note that the implementation has
 * a lot of freedom to choose its display.
 */
public interface Display {

	/**
	 * Indicate that the light is on
	 */
	public abstract void coolerOpened(CoolerContext coolerContext);

	/**
	 * Indicate that the light is off
	 */
	public abstract void coolerClosed(CoolerContext coolerContext);

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