import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class GUIDisplay extends Display implements ActionListener {
	private static SimpleDisplay frame;

	/**
	 * Makes it a singleton
	 */
	private GUIDisplay() {
		frame = new SimpleDisplay();
		initialize();
	}

	/**
	 * This class has most of the widgets
	 *
	 */
	private class SimpleDisplay extends JFrame {
		private JButton bSetRoomTemp = new JButton("Set room temp");
		private JButton bSetFridgeTemp = new JButton("Set desired fridge temp");
		private JButton bSetFreezerTemp = new JButton("Set desired freezer temp");
		private JButton bFridgeDoorCloser = new JButton("Close fridge door");
		private JButton bFridgeDoorOpener = new JButton("Open fridge door");
		private JButton bFreezerDoorCloser = new JButton("Close freezer door");
		private JButton bFreezerDoorOpener = new JButton("Open freezer door");
		private JLabel lRoomTemp = new JLabel("Room Temp");
		private JLabel lFridgeTemp = new JLabel("Desired fridge temp");
		private JLabel lFreezerTemp = new JLabel("Desired freezer temp");
		private JLabel lStatus = new JLabel("Status");
		private JLabel lFridgeLightStatus = new JLabel("Fridge light: Off");
		private JLabel lFreezerLightStatus = new JLabel("Freezer light: Off");
		private JLabel lFridgeTempStatus = new JLabel("Fridge temp: ");
		private JLabel lFreezerTempStatus = new JLabel("Freezer temp: ");
		private JLabel lFridgeCoolingStatus = new JLabel("Fridge cooling: ");
		private JLabel lFreezerCoolingStatus = new JLabel("Freezer cooling: ");
		private JLabel lPlaceHolder = new JLabel();
		private JLabel lPlaceHolder2 = new JLabel();
		private JLabel lPlaceHolder3 = new JLabel();
		private JLabel lPlaceHolder4 = new JLabel();
		private JLabel lPlaceHolder5 = new JLabel();
		private JLabel lPlaceHolder6 = new JLabel();
		private JTextField tRoomTemp = new JTextField();
		private JTextField tFridgeTemp = new JTextField();
		private JTextField tFreezerTemp = new JTextField();
		
		/**
		 * Sets up the interface
		 */
		private SimpleDisplay() {
			super("Refrigerator");
			getContentPane().setLayout(new GridLayout(9,3));
			getContentPane().add(lRoomTemp);
			getContentPane().add(tRoomTemp);
			getContentPane().add(bSetRoomTemp);
			getContentPane().add(lFridgeTemp);
			getContentPane().add(tFridgeTemp);
			getContentPane().add(bSetFridgeTemp);
			getContentPane().add(lFreezerTemp);
			getContentPane().add(tFreezerTemp);
			getContentPane().add(bSetFreezerTemp);
			getContentPane().add(bFridgeDoorOpener);
			getContentPane().add(bFridgeDoorCloser);
			getContentPane().add(lPlaceHolder);
			getContentPane().add(bFreezerDoorOpener);
			getContentPane().add(bFreezerDoorCloser);
			getContentPane().add(lPlaceHolder2);
			getContentPane().add(lStatus);
			getContentPane().add(lPlaceHolder3);
			getContentPane().add(lPlaceHolder4);
			getContentPane().add(lFridgeLightStatus);
			getContentPane().add(lFreezerLightStatus);
			getContentPane().add(lPlaceHolder5);
			getContentPane().add(lFridgeTempStatus);
			getContentPane().add(lFreezerTempStatus);
			getContentPane().add(lPlaceHolder6);
			getContentPane().add(lFridgeCoolingStatus);
			getContentPane().add(lFreezerCoolingStatus);

			
			bSetRoomTemp.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {     
	        		RoomContext.setRoomTemp(Integer.parseInt(tRoomTemp.getText()));
	             }
	          });
			bSetFridgeTemp.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {     
	        		FridgeContext.setDesiredFridgeTemp(Integer.parseInt(tFridgeTemp.getText()));
	             }
	          });
			bSetFreezerTemp.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {     
	        		FreezerContext.setDesiredFreezerTemp(Integer.parseInt(tFreezerTemp.getText()));
	             }
	          });

			bFridgeDoorCloser.addActionListener(GUIDisplay.this);
			bFridgeDoorOpener.addActionListener(GUIDisplay.this);
			bFreezerDoorCloser.addActionListener(GUIDisplay.this);
			bFreezerDoorOpener.addActionListener(GUIDisplay.this);

			pack();
			setVisible(true);
		}
	}

	/**
	 * Handles the clicks
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource().equals(frame.bFridgeDoorCloser)) {
			FridgeContext.instance().processEvent(
					FridgeContext.Events.DOOR_CLOSED_EVENT);
		} else if (event.getSource().equals(frame.bFridgeDoorOpener)) {
			FridgeContext.instance().processEvent(
					FridgeContext.Events.DOOR_OPENED_EVENT);
		}
		else if (event.getSource().equals(frame.bFreezerDoorCloser)) {
			FreezerContext.instance().processEvent(
					FreezerContext.Events.DOOR_CLOSED_EVENT);
		} else if (event.getSource().equals(frame.bFreezerDoorOpener)) {
			FreezerContext.instance().processEvent(
					FreezerContext.Events.DOOR_OPENED_EVENT);
		}
	}

	/**
	 * Indicate that the light is on
	 */
	@Override
	public void turnFridgeLightOn() {
		frame.lFridgeLightStatus.setText("Fridge light: On");
	}

	/**
	 * Indicate that the light is off
	 */
	@Override
	public void turnFridgeLightOff() {
		frame.lFridgeLightStatus.setText("Fridge light: Off");
	}
	
	/**
	 * Indicate that the light is on
	 */
	@Override
	public void turnFreezerLightOn() {
		frame.lFreezerLightStatus.setText("Freezer light: On");
	}

	/**
	 * Indicate that the light is off
	 */
	@Override
	public void turnFreezerLightOff() {
		frame.lFreezerLightStatus.setText("Freezer light: Off");
	}

	/**
	 * display the fridge temperature
	 * 
	 * @param the current temp
	 */
	@Override
	public void displayFridgeTemp(int value) {
		frame.lFridgeTempStatus.setText("Fridge temp: " + value);
	}
	
	/**
	 * display the freezer temperature
	 * 
	 * @param the current temp
	 */
	@Override
	public void displayFreezerTemp(int value) {
		frame.lFreezerTempStatus.setText("Freezer temp: " + value);
	}

	/**
	 * The main method. Creates the interface
	 * 
	 * @param args
	 *            not used
	 */
	public static void main(String[] args) {
		Display display = new GUIDisplay();
		RoomContext.setRoomTemp(70);
		FridgeContext.setFridgeTemp(40);
		FridgeContext.setDesiredFridgeTemp(35);
		FridgeContext.setFridgeCoolRate(5);
		FridgeContext.setFridgeLossRateOpen(2);
		FridgeContext.setFridgeLossRateClose(10);
		FreezerContext.setFreezerTemp(0);
		FreezerContext.setDesiredFreezerTemp(-5);
		FreezerContext.setFreezerCoolRate(6);
		FreezerContext.setFreezerLossRateOpen(1);
		FreezerContext.setFreezerLossRateClose(12);
	}
}