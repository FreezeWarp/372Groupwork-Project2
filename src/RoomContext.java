public class RoomContext {
	private int roomTemp;

	public RoomContext(int roomTemp) {
		this.roomTemp = roomTemp;
	}

	/**
	 * Gets the temperature
	 * 
	 *  @return freezerTemp
	 */
	public int getRoomTemp() {
		return roomTemp;
	}
	
	/**
	 * Sets the temperature 
	 * 
	 * @param temp
	 *            temp of the room
	 */
	public void setRoomTemp(int temp) {
		roomTemp = temp;
	}
}