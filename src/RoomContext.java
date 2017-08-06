import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * The environment of a room. May be real-world or simulated.
 *
 * @author  Eric Fulwiler, Daniel Johnson, Joseph T. Parsons, Cory Stadther
 * @version 2.0
 * @since   2017-August-05
 */
public class RoomContext {
	/**
	 * The room temperature.
	 */
	private final IntegerProperty roomTemp;

	/**
	 * Create a new room context with a given temperature.
	 *
	 * @param roomTemp {@link RoomContext#roomTemp}, as an integer.
	 */
	public RoomContext(int roomTemp) {
		this.roomTemp = new SimpleIntegerProperty(roomTemp);
	}

	/**
	 * @return {@link RoomContext#roomTemp}, as an integer.
	 */
	public int getRoomTemp() {
		return roomTemp.get();
	}

	/**
	 * @param temp {@link RoomContext#roomTemp}, as an integer.
	 */
	public void setRoomTemp(int temp) {
		roomTemp.set(temp);
	}

	/**
	 * @return {@link RoomContext#roomTemp}, as a property.
	 */
	public IntegerProperty roomTempProperty() { return roomTemp; }
}