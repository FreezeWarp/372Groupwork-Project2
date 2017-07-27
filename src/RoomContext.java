import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class RoomContext {
	private final IntegerProperty roomTemp;

	public RoomContext(int roomTemp) {
		this.roomTemp = new SimpleIntegerProperty(roomTemp);
	}

	/**
	 * Gets the temperature
	 *
	 *  @return freezerTemp
	 */
	public int getRoomTemp() {
		return roomTemp.get();
	}

	/**
	 * Sets the temperature 
	 *
	 * @param temp
	 *            temp of the room
	 */
	public void setRoomTemp(int temp) {
		roomTemp.set(temp);
	}

	public IntegerProperty roomTempProperty() { return roomTemp; }
}