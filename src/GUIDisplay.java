import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class GUIDisplay extends Application implements Display {
    /*################################
     * Primary Properties
     *###############################*/

    /**
     * A reference to ourself.
     */
    static Display display;

    /**
     * A reference to the room our fridge and freezer are in.
     */
    static RoomContext roomContext;

    /**
     * A reference to our fridge cooler.
     */
    static CoolerContext fridge;

    /**
     * A reference to our freezer cooler.
     */
    static CoolerContext freezer;


    /**
     * Our configuration data. Defaults will be overridden in {@link GUIDisplay#main(String[])} if possible.
     */
    static ConfigurationMap<String, Integer> config = new ConfigurationMap<String, Integer>(
            new String[]{"FridgeLow", "FridgeHigh", "FreezerLow", "FreezerHigh", "RoomLow", "RoomHigh",
                    "FridgeRateLossDoorClosed", "FridgeRateLossDoorOpen", "FreezerRateLossDoorClosed", "FreezerRateLossDoorOpen",
                    "FridgeCompressorStartDiff", "FreezerCompressorStartDiff", "FridgeCoolRate", "FreezerCoolRate"},
            new Integer[]{37, 41, -9, 0, 50, 75,
                    30, 2, 10, 1,
                    2, 1, 5, 4}
    );




    /*################################
     * Program Entry-Point
     *###############################*/

    /**
     * Entry-point for program. Opens config from file in first argument and then launches JavaFX.
     *
     * @param args Command-line arguments. The first one will be used as a configuration file, if available.
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




    /*################################
     * JavaFX Properties
     *###############################*/

    private static Button bSetRoomTemp;
    private static Button bSetFridgeTemp;
    private static Button bSetFreezerTemp;
    private static Button bFridgeDoorToggle;
    private static Button bFreezerDoorToggle;
    private static Label lFridgeTemp;
    private static Label lFreezerTemp;
    private static Label lStatus;
    private static Label lFridgeLightStatus;
    private static Label lFreezerLightStatus;
    private static Label lFridgeTempStatus;
    private static Label lFreezerTempStatus;
    private static Label lRoomTemp;
    private static Label lFridgeCoolingStatus;
    private static Label lFreezerCoolingStatus;
    private static TextField tRoomTemp;
    private static TextField tFridgeTemp;
    private static TextField tFreezerTemp;




    /*################################
     * JavaFX Entry-Point
     *###############################*/

    /**
     * Entry-point for JavaFX.
     *
     * @param primaryStage Set by JavaFx.
     */
	public void start(Stage primaryStage) {
	    /* Initialise all static display elements. */
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
        lFridgeTempStatus = new Label();
        lFreezerTempStatus = new Label();
        lRoomTemp = new Label();
        lFridgeCoolingStatus = new Label("Fridge cooling: ");
        lFreezerCoolingStatus = new Label("Freezer cooling: ");
        tRoomTemp = new TextField();
        tFridgeTemp = new TextField();
        tFreezerTemp = new TextField();
        
        

        /* Build Our Interface */
        // Stores the overall frame, composed of the main frame and button frame.
        VBox overallFrame = new VBox(25);


        // Stores the main frame, composed of the status and config frames.
        HBox mainFrame = new HBox(5);

        // Stores the config frame, composed of the labels and buttons for setting certain configuration at run-time.
        VBox configFrame = new VBox(10);
        configFrame.getChildren().add((new HBox(5, tRoomTemp, bSetRoomTemp)));
        configFrame.getChildren().add((new HBox(5, tFridgeTemp, bSetFridgeTemp)));
        configFrame.getChildren().add((new HBox(5, tFreezerTemp, bSetFreezerTemp)));

        // Stores the status frame, composed of status labels for temperature, etc.
        VBox statusFrame = new VBox(10);
        statusFrame.getChildren().addAll(lFridgeCoolingStatus, lFridgeTempStatus, lFreezerCoolingStatus, lFreezerTempStatus, lRoomTemp);
        statusFrame.setMinWidth(200); // Should keep things long enough to prevent resizes when "Idle" switches to "Active"

        // Add the status, config frames to main frame.
        mainFrame.getChildren().addAll(statusFrame, configFrame);


        // Stores general button actions for opening/closing fridge and freezer
        HBox buttonFrame = new HBox(25);
        buttonFrame.getChildren().addAll(bFreezerDoorToggle, bFridgeDoorToggle);


        // Add main frame, button frame to overall frame.
        overallFrame.getChildren().addAll(mainFrame, buttonFrame);


        // Center stuff.
        overallFrame.setAlignment(Pos.CENTER);
        configFrame.setAlignment(Pos.CENTER);
        mainFrame.setAlignment(Pos.CENTER);
        buttonFrame.setAlignment(Pos.CENTER);


        // Add the overall frame to a scene, add the scene to the primary stage, then display the stage.
        Scene scene = new Scene(overallFrame);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Coolers Interface");
        primaryStage.show();



        /* Action Listeners */
        // Room Temperature Change
        bSetRoomTemp.setOnAction((event) -> {
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
        });

        // Freezer Target Temperature Change
        bSetFreezerTemp.setOnAction((event) -> {
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
        });

        // Fridge Target Temperature Change
        bSetFridgeTemp.setOnAction((event) -> {
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
        });

        // Open/Close Door
        bFridgeDoorToggle.setOnAction(new ButtonHandler());
        bFreezerDoorToggle.setOnAction(new ButtonHandler());



        /* Initialise Our Objects */
        startSimulation();
    }




    /*################################
     * Event Handlers
     *###############################*/

    /**
     * Handles all general button presses in the program.
     * (Specific button press handlers defined elsewhere.)
     */
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




    /*################################
     * JavaFX Helpers
     *###############################*/

    /**
     * Display a generic error-like alert message.
     *
     * @param text The text to display.
     */
    public void alert(String text) {
        Label label = new Label(text);
        label.setWrapText(true);

        Alert dialog = new Alert(Alert.AlertType.ERROR);
        dialog.setHeaderText("Error");
        dialog.getDialogPane().setContent(label);
        dialog.showAndWait();
    }




    /*################################
     * Display Functions for Two Coolers
     *###############################*/

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




    /*################################
     * Display Functions for Arbitrary Coolers, from Display Interface
     * NOTE: the use of "==" instead of "equals()" is correct here. We are comparing references, not values.
     *###############################*/

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

    /**
     * Unused; dynamic binding on {@link CoolerContext#coolerTempProperty()} is used instead.
     */
    public void displayTemp(CoolerContext context) {
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



    /*################################
     * Entry-Point for Stimulation
     *###############################*/

    /**
     * Constructs the fridge, freezer, room, and display references.
     */
	public static void startSimulation() {
        display = new GUIDisplay();

        // Initialise Room Context
        roomContext = new RoomContext((config.get("RoomHigh") + config.get("RoomLow")) / 2);
        lRoomTemp.setText("Room temperature: " + roomContext.getRoomTemp());

        // Initialise Fridge
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

        // Initialise Freezer
        freezer = new CoolerContext(display, roomContext, roomContext.getRoomTemp(), (config.get("FreezerH" +
                "igh") + config.get("FreezerLow")) / 2, config.get("FreezerCompressorStartDiff"), config.get("FreezerCoolRate"), config.get("FreezerRateLossDoorOpen"), config.get("FreezerRateLossDoorClosed"));

        // Listeners for room, fridge, and freezer temp changes
        roomContext.roomTempProperty().addListener((obs, oldValue, newValue) ->
                Platform.runLater(() -> lRoomTemp.setText("Room temperature: " + newValue)));

        fridge.coolerTempProperty().addListener((obs, oldValue, newValue) ->
            Platform.runLater(() -> {
                lFridgeTempStatus.setText("Fridge temperature: " + newValue);
                lFridgeTempStatus.setTextFill((int) newValue > config.get("FridgeHigh") ? Color.RED : Color.BLACK);
            })
        );

        freezer.coolerTempProperty().addListener((obs, oldValue, newValue) ->
            Platform.runLater(() -> {
                lFreezerTempStatus.setText("Freezer temperature: " + newValue);
                lFreezerTempStatus.setTextFill((int) newValue > config.get("FreezerHigh") ? Color.RED : Color.BLACK);
            })
        );
    }
}