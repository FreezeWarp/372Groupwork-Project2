import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class GUIDisplay extends Application implements Display {
    static Display display;
    static RoomContext roomContext;
    static CoolerContext fridge;
    static CoolerContext freezer;


    static ConfigurationMap<String, Integer> config = new ConfigurationMap<String, Integer>(
            new String[]{"FridgeLow", "FridgeHigh", "FreezerLow", "FreezerHigh", "RoomLow", "RoomHigh",
                    "FridgeRateLossDoorClosed", "FridgeRateLossDoorOpen", "FreezerRateLossDoorClosed", "FreezerRateLossDoorOpen",
                    "FridgeCompressorStartDiff", "FreezerCompressorStartDiff", "FridgeCoolRate", "FreezerCoolRate"},
            new Integer[]{37, 41, -9, 0, 50, 75,
                    30, 2, 10, 1,
                    2, 1, 5, 4}
    );



    private static Button bSetRoomTemp ;
    private static Button bSetFridgeTemp ;
    private static Button bSetFreezerTemp ;
    private static Button bFridgeDoorToggle ;
    private static Button bFreezerDoorToggle ;
    private static Label lFridgeTemp ;
    private static Label lFreezerTemp ;
    private static Label lStatus ;
    private static Label lFridgeLightStatus ;
    private static Label lFreezerLightStatus ;
    private static Label lFridgeTempStatus ;
    private static Label lFreezerTempStatus ;
    private static Label lRoomTemp ;
    private static Label lFridgeCoolingStatus ;
    private static Label lFreezerCoolingStatus ;
    private static Label lPlaceHolder ;
    private static Label lPlaceHolder2 ;
    private static Label lPlaceHolder3 ;
    private static Label lPlaceHolder4 ;
    private static Label lPlaceHolder5 ;
    private static Label lPlaceHolder6 ;
    private static TextField tRoomTemp ;
    private static TextField tFridgeTemp ;
    private static TextField tFreezerTemp ;

    

	public void start(Stage primaryStage) {
        bSetRoomTemp = new Button("Set room temp");
        bSetFridgeTemp = new Button("Set desired fridge temp");
        bSetFreezerTemp = new Button("Set desired freezer temp");
        bFridgeDoorToggle = new Button("Open fridge door");
        bFreezerDoorToggle = new Button("Open freezer door");
        lFridgeTemp = new Label("Desired fridge temp");
        lFreezerTemp = new Label("Desired freezer temp");
        lStatus = new Label("Status");
        lFridgeLightStatus = new Label("Fridge light: Off");
        lFreezerLightStatus = new Label("Freezer light: Off");
        lFridgeTempStatus = new Label("Fridge temp: ");
        lFreezerTempStatus = new Label("Freezer temp: ");
        lRoomTemp = new Label("Room temp: ");
        lFridgeCoolingStatus = new Label("Fridge cooling: ");
        lFreezerCoolingStatus = new Label("Freezer cooling: ");
        lPlaceHolder = new Label();
        lPlaceHolder2 = new Label();
        lPlaceHolder3 = new Label();
        lPlaceHolder4 = new Label();
        lPlaceHolder5 = new Label();
        lPlaceHolder6 = new Label();
        tRoomTemp = new TextField();
        tFridgeTemp = new TextField();
        tFreezerTemp = new TextField();
        
        
        
        VBox overallFrame = new VBox(5);

        HBox mainFrame = new HBox(5);

        VBox configFrame = new VBox(10);
        configFrame.getChildren().add((new HBox(5, tRoomTemp, bSetRoomTemp)));
        configFrame.getChildren().add((new HBox(5, tFridgeTemp, bSetFridgeTemp)));
        configFrame.getChildren().add((new HBox(5, tFreezerTemp, bSetFreezerTemp)));

        VBox statusFrame = new VBox(10);
        statusFrame.getChildren().addAll(lFridgeCoolingStatus, lFridgeTempStatus, lFreezerCoolingStatus, lFreezerTempStatus, lRoomTemp);
        statusFrame.setMinWidth(150);

        mainFrame.getChildren().addAll(statusFrame, configFrame);

        HBox buttonFrame = new HBox(25);
        buttonFrame.getChildren().addAll(bFreezerDoorToggle, bFridgeDoorToggle);

        overallFrame.getChildren().addAll(mainFrame, buttonFrame);

        overallFrame.setAlignment(Pos.CENTER);
        configFrame.setAlignment(Pos.CENTER);
        mainFrame.setAlignment(Pos.CENTER);
        buttonFrame.setAlignment(Pos.CENTER);

        // add the layout to scene
        Scene scene = new Scene(overallFrame);

        // add scene to the stage
        primaryStage.setScene(scene);

        // Title and show
        primaryStage.setTitle("Coolers Interface");
        primaryStage.show();

        bSetRoomTemp.setOnAction(new EventHandler<ActionEvent> () {
            public void handle(ActionEvent e) {
                int value = Integer.parseInt(tRoomTemp.getText());

                if (value > config.get("RoomHigh")) {
                    alert("The room cannot be that warm. Current maximum is " + config.get("RoomHigh") + ".");
                }
                else if (value < config.get("RoomLow")) {
                    alert("The room cannot be that cool. Current minimum is " + config.get("RoomLow") + ".");
                }
                else {
                    roomContext.setRoomTemp(value);
                }
            }
        });
        
        bSetFreezerTemp.setOnAction(new EventHandler<ActionEvent> () {
            public void handle(ActionEvent e) {
                int value = Integer.parseInt(tFreezerTemp.getText());

                if (value > config.get("FreezerHigh")) {
                    alert("The freezer cannot be that warm. Current maximum is " + config.get("FreezerHigh") + ".");
                }
                else if (value < config.get("FreezerLow")) {
                    alert("The freezer cannot be that cool. Current minimum is " + config.get("FreezerLow") + ".");
                }
                else {
                    freezer.setDesiredCoolerTemp(value);
                }
            }
        });

        bSetFridgeTemp.setOnAction(new EventHandler<ActionEvent> () {
            public void handle(ActionEvent e) {
                int value = Integer.parseInt(tFridgeTemp.getText());

                if (value > config.get("FridgeHigh")) {
                    alert("The fridge cannot be that warm. Current maximum is " + config.get("FridgeHigh") + ".");
                }
                else if (value < config.get("FridgeLow")) {
                    alert("The fridge cannot be that cool. Current minimum is " + config.get("FridgeLow") + ".");
                }
                else {
                    fridge.setDesiredCoolerTemp(value);
                }
            }
        });

        bFridgeDoorToggle.setOnAction(new ButtonHandler());
        bFreezerDoorToggle.setOnAction(new ButtonHandler());

        startSimulation();
    }


    class ButtonHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent event) {
            if (event.getSource().equals(bFridgeDoorToggle)) {
                fridge.processEvent(CoolerContext.Events.DOOR_TOGGLE_EVENT);
            }
            else if (event.getSource().equals(bFreezerDoorToggle)) {
                freezer.processEvent(CoolerContext.Events.DOOR_TOGGLE_EVENT);
            }
        }
    }

    public void alert(String text) {
        Label label = new Label(text);
        label.setWrapText(true);

        Alert dialog = new Alert(Alert.AlertType.ERROR);
        dialog.setHeaderText("Error");
        dialog.getDialogPane().setContent(label);
        dialog.showAndWait();
    }

	/**
	 * Indicate that the light is on
	 */
	private void fridgeOpened() {
        Platform.runLater(new Runnable() {
            public void run() {
                bFridgeDoorToggle.setText("Close fridge door");
                lFridgeLightStatus.setText("Fridge light: On");
            }
        });
	}

	/**
	 * Indicate that the light is off
	 */
	private void fridgeClosed() {
        Platform.runLater(new Runnable() {
            public void run() {
                bFridgeDoorToggle.setText("Open fridge door");
                lFridgeLightStatus.setText("Fridge light: Off");
            }
        });
	}
	
	/**
	 * Indicate that the light is on
	 */
	private void freezerOpened() {
        Platform.runLater(new Runnable() {
            public void run() {
                bFreezerDoorToggle.setText("Close freezer door");
                lFreezerLightStatus.setText("Freezer light: On");
            }
        });
	}

	/**
	 * Indicate that the light is off
	 */
	private void freezerClosed() {
        Platform.runLater(new Runnable() {
            public void run() {
                bFreezerDoorToggle.setText("Open freezer door");
                lFreezerLightStatus.setText("Freezer light: Off");
            }
        });
	}
	
	/**
	 * Indicate that the cooling is on
	 */
	private void turnFridgeCoolingOn() {
        Platform.runLater(new Runnable() {
            public void run() {
                lFridgeCoolingStatus.setText("Fridge cooling: Active");
            }
        });
	}

	/**
	 * Indicate that the cooling is off
	 */
	private void turnFridgeCoolingOff() {
        Platform.runLater(new Runnable() {
            public void run() {
                lFridgeCoolingStatus.setText("Fridge cooling: Idle");
            }
        });
	}
	
	/**
	 * Indicate that the cooling is on
	 */
	private void turnFreezerCoolingOn() {
        Platform.runLater(new Runnable() {
            public void run() {
                lFreezerCoolingStatus.setText("Freezer cooling: Active");
            }
        });
	}

	/**
	 * Indicate that the cooling is off
	 */
	private void turnFreezerCoolingOff() {
        Platform.runLater(new Runnable() {
            public void run() {
                lFreezerCoolingStatus.setText("Freezer cooling: Idle");
            }
        });
	}

	/**
	 * display the fridge temperature
	 * 
	 * @param value the current temp
	 */
	public void displayFridgeTemp(int value) {
        Platform.runLater(new Runnable() {
            public void run() {
                lFridgeTempStatus.setText("Fridge temp: " + value);
            }
        });
	}
	
	/**
	 * display the freezer temperature
	 * 
	 * @param value the current temp
	 */
	public void displayFreezerTemp(int value) {
        Platform.runLater(new Runnable() {
            public void run() {
                lFreezerTempStatus.setText("Freezer temp: " + value);
            }
        });
	}



    /* Contextual handlers.
     * NOTE: the use of "==" instead of "equals()" is correct here. We are comparing references, not values. */

	public void coolerOpened(CoolerContext context) {
	    if (context == fridge) {
	        fridgeOpened();
        }
        else if (context == freezer) {
	        freezerOpened();
        }
        else {
	        // TODO: error
        }
    }

    public void coolerClosed(CoolerContext context) {
        if (context == fridge) {
            fridgeClosed();
        }
        else if (context == freezer) {
            freezerClosed();
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


        Application.launch(args);
	}


	public static void startSimulation() {
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
    }


	public void initialize() {
        //this.addObserver(fridge);
        //this.addObserver(freezer);
    }
}