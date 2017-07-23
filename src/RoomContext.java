public class RoomContext {

	private static int roomTemp;
	/**
	 * Gets the temperature
	 * 
	 *  @return freezerTemp
	 */
	public static int getRoomTemp() {
		return roomTemp;
	}
	
	/**
	 * Sets the temperature 
	 * 
	 * @param temp
	 *            temp of the room
	 */
	public static void setRoomTemp(int temp) {
		roomTemp = temp;
	}
}