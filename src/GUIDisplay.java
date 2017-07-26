import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class GUIDisplay extends Display implements ActionListener {
	private static SimpleDisplay frame;

	/**
	 * Makes it a singleton
	 */
	private GUIDisplay() {
		frame = new SimpleDisplay();
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
	        		roomContext.setRoomTemp(Integer.parseInt(tRoomTemp.getText()));
	             }
	          });
			bSetFridgeTemp.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {     
	        		fridge.setDesiredCoolerTemp(Integer.parseInt(tFridgeTemp.getText()));
	             }
	          });
			bSetFreezerTemp.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {     
	        		freezer.setDesiredCoolerTemp(Integer.parseInt(tFreezerTemp.getText()));
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
			fridge.processEvent(
					CoolerContext.Events.DOOR_CLOSED_EVENT);
		} else if (event.getSource().equals(frame.bFridgeDoorOpener)) {
			fridge.processEvent(
                    CoolerContext.Events.DOOR_OPENED_EVENT);
		} else if (event.getSource().equals(frame.bFreezerDoorCloser)) {
			freezer.processEvent(
                    CoolerContext.Events.DOOR_CLOSED_EVENT);
		} else if (event.getSource().equals(frame.bFreezerDoorOpener)) {
			freezer.processEvent(
                    CoolerContext.Events.DOOR_OPENED_EVENT);
		}
	}

	/**
	 * Indicate that the light is on
	 */
	private void turnFridgeLightOn() {
		frame.lFridgeLightStatus.setText("Fridge light: On");
	}

	/**
	 * Indicate that the light is off
	 */
	private void turnFridgeLightOff() {
		frame.lFridgeLightStatus.setText("Fridge light: Off");
	}
	
	/**
	 * Indicate that the light is on
	 */
	private void turnFreezerLightOn() {
		frame.lFreezerLightStatus.setText("Freezer light: On");
	}

	/**
	 * Indicate that the light is off
	 */
	private void turnFreezerLightOff() {
		frame.lFreezerLightStatus.setText("Freezer light: Off");
	}
	
	/**
	 * Indicate that the cooling is on
	 */
	private void turnFridgeCoolingOn() {
		frame.lFridgeCoolingStatus.setText("Fridge cooling: Active");
	}

	/**
	 * Indicate that the cooling is off
	 */
	private void turnFridgeCoolingOff() {
		frame.lFridgeCoolingStatus.setText("Fridge cooling: Idle");
	}
	
	/**
	 * Indicate that the cooling is on
	 */
	private void turnFreezerCoolingOn() {
		frame.lFreezerCoolingStatus.setText("Freezer cooling: Active");
	}

	/**
	 * Indicate that the cooling is off
	 */
	private void turnFreezerCoolingOff() {
		frame.lFreezerCoolingStatus.setText("Freezer cooling: Idle");
	}

	/**
	 * display the fridge temperature
	 * 
	 * @param value the current temp
	 */
	public void displayFridgeTemp(int value) {
		frame.lFridgeTempStatus.setText("Fridge temp: " + value);
	}
	
	/**
	 * display the freezer temperature
	 * 
	 * @param value the current temp
	 */
	public void displayFreezerTemp(int value) {
		frame.lFreezerTempStatus.setText("Freezer temp: " + value);
	}



    /* Contextual handlers.
     * NOTE: the use of "==" instead of "equals()" is correct here. We are comparing references, not values. */

	public void turnLightOn(CoolerContext context) {
	    if (context == fridge) {
	        turnFridgeLightOn();
        }
        else if (context == freezer) {
	        turnFreezerLightOn();
        }
        else {
	        // TODO: error
        }
    }

    public void turnLightOff(CoolerContext context) {
        if (context == fridge) {
            turnFridgeLightOff();
        }
        else if (context == freezer) {
            turnFreezerLightOff();
        }
        else {
            // TODO: error
        }
    }

    public void displayTemp(CoolerContext context) {
        if (context == fridge) {
            displayFridgeTemp(context.getCoolerTemp());
        }
        else if (context == freezer) {
            displayFreezerTemp(context.getCoolerTemp());
        }
        else {
            // TODO: error
        }
    }

    public void turnCoolingOn(CoolerContext context) {
        if (context == fridge) {
            turnFridgeCoolingOn();
        }
        else if (context == freezer) {
            turnFreezerCoolingOn();
        }
        else {
            // TODO: error
        }
    }

    public void turnCoolingOff(CoolerContext context) {
        if (context == fridge) {
            turnFridgeCoolingOff();
        }
        else if (context == freezer) {
            turnFreezerCoolingOff();
        }
        else {
            // TODO: error
        }
    }




	/**
	 * The main method. Creates the interface
	 * 
	 * @param args
	 *            not used
	 */
	public static void main(String[] args) {
        ConfigurationMap<String, Integer> config = new ConfigurationMap<String, Integer>(
                new String[]{"FridgeLow", "FridgeHigh", "FreezerLow", "FreezerHigh", "RoomLow", "RoomHigh",
                             "FridgeRateLossDoorClosed", "FridgeRateLossDoorOpen", "FreezerRateLossDoorClosed", "FreezerRateLossDoorOpen",
                             "FridgeCompressorStartDiff", "FreezerCompressorStartDiff", "FridgeCoolRate", "FreezerCoolRate"},
                new Integer[]{37, 41, -9, 0, 50, 75,
                              30, 2, 10, 1,
                              2, 1, 5, 4}
        );

	    if (args.length > 0) {
	        Path filePath = Paths.get(args[0]);

            System.out.println("Attempting to use " + filePath.toAbsolutePath() + " as configuration file.");
            if (!Files.exists(filePath)) {
                System.err.println("Could not use " + filePath.toAbsolutePath() + " as configuration file. The file does not exist.");
            }
            else {
                try {
                    Files.lines(filePath).forEach((line) -> {
                        String[] lineParts = line.split("=");
                        try {
                            config.put(lineParts[0].trim(), Integer.parseInt(lineParts[1].trim()));
                        } catch (NoKeyException exception) {
                            System.err.println("Config value is not recgonised: " + lineParts[0]);
                        } catch (Exception exceptiopn) {
                            System.err.println("Could not parse config value: " + lineParts[0] + "=" + lineParts[1]);
                        }
                    });
                } catch (IOException exception) {
                    System.err.println("Could not use " + filePath.toAbsolutePath() + " as configuration file. A file IO exception occured when reading it: " + exception);
                }
            }
        }
		display = new GUIDisplay();
		roomContext = new RoomContext((config.get("RoomHigh") + config.get("RoomLow")) / 2);
		fridge = new CoolerContext(
		        display, // Associate our display.
                roomContext, // Associate our room context.
                roomContext.getRoomTemp(), // Set the fridge's current temperature to the current room temperature (which is probably right when you're first turning it on)
                (config.get("FridgeHigh") + config.get("FridgeLow")) / 2, // Set the fridge's default target temperature to the average of it's maximum and minimum (which seems reasonable)
                config.get("FridgeCompressorStartDiff"), // Set the compressor's diff for activating.
                config.get("FridgeCoolRate"), // Set the cooling rate.
                config.get("FridgeRateLossDoorOpen"), // Set the loss rate, door open.
                config.get("FridgeRateLossDoorClosed") // Set the loss rate, door closed.
        );
		freezer = new CoolerContext(display, roomContext, roomContext.getRoomTemp(), (config.get("FreezerH" +
                "igh") + config.get("FreezerLow")) / 2, config.get("FreezerCompressorStartDiff"), config.get("FreezerCoolRate"), config.get("FreezerRateLossDoorOpen"), config.get("FreezerRateLossDoorClosed"));

		display.initialize();
	}

	public void initialize() {
        this.addObserver(fridge);
        this.addObserver(freezer);
    }
}