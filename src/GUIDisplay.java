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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

/**
 * A GUI interface to simulate two coolers, a refridgerator and freezer.
 *
 * Key Design Notes:
 ** We are using JavaFX's listeners (using Properties) instead of custom listener classes. This is probably a good idea, because those methods exist for a reason and are becoming the default choice for GUIs in the Java world. They work much like the Observer pattern.
 *
 * @author Eric Fulwiler, Daniel Johnson, Joseph T. Parsons, Cory Stadther
 * @version 2.0
 * @since   2017-August-05
 */
public class GUIDisplay extends Application {
    /*################################
     * Primary Properties
     *###############################*/

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
    static StrongMap<String, Integer> config = new StrongMap<String, Integer>(
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
            File file = new File(args[0]);

            System.out.println("Attempting to use " + file.getAbsolutePath() + " as configuration file.");
            if (!file.exists() || !file.canRead()) {
                System.err.println("Could not use " + file.getAbsolutePath() + " as configuration file. The file does not exist.");
            }
            else {
                try {
                    Files.lines(file.toPath()).forEach((line) -> {
                        if (line.length() == 0) {
                            // Do nothing for empty lines.
                        }
                        else if (line.startsWith(";")) {
                            // Do nothing for comment lines.
                        }
                        else if (line.startsWith("#")) {
                            // Do nothing for comment lines.
                        }
                        else if (line.contains("=")) {
                            String[] lineParts = line.split("=");
                            try {
                                config.put(lineParts[0].trim(), Integer.parseInt(lineParts[1].trim()));
                            } catch (NoKeyException exception) {
                                System.err.println("Config value is not recognised: " + lineParts[0]);
                            } catch (Exception exceptiopn) {
                                System.err.println("Could not parse config value: " + lineParts[0] + "=" + lineParts[1]);
                            }
                        }
                        else {
                            System.err.println("Line does not contain '=' seperator. Skipping.");
                        }
                    });
                } catch (IOException exception) {
                    System.err.println("Could not use " + file.getAbsolutePath() + " as configuration file. A file IO exception occurred when reading it: " + exception);
                }
            }
        }


        Application.launch(args);
    }




    /*################################
     * JavaFX Properties
     *###############################*/
    private static StrongMap<String, Label> labels;
    private static StrongMap<String, Button> buttons;
    private static StrongMap<String, TextField> textfields;



    /*################################
     * JavaFX Entry-Point
     *###############################*/

    /**
     * Entry-point for JavaFX.
     *
     * @param primaryStage Set by JavaFx.
     */
	public void start(Stage primaryStage) {
	    labels = new StrongMap<>(
                new String[] {
                        "lFridgeTemp",
                        "lFreezerTemp",
                        "lFridgeLightStatus",
                        "lFreezerLightStatus",
                        "lFridgeTempStatus",
                        "lFreezerTempStatus",
                        "lFridgeCoolingStatus",
                        "lFreezerCoolingStatus",
                        "lRoomTemp"
                },
                new Label[] {
                        new Label("Desired fridge temp"),
                        new Label("Desired freezer temp"),
                        new Label("Fridge light: "),
                        new Label("Freezer light: "),
                        new Label("Fridge temp: : "),
                        new Label("Freezer temp: "),
                        new Label("Fridge status: : "),
                        new Label("Freezer status: "),
                        new Label("Room temp: "),
                }
        );

	    buttons = new StrongMap<>(
	            new String[] {
	                    "bSetRoomTemp",
                        "bSetFridgeTemp",
                        "bSetFreezerTemp",
                        "bFridgeDoorToggle",
                        "bFreezerDoorToggle"
                },
                new Button[] {
                        new Button("Set room temp"),
                        new Button("Set desired fridge temp"),
                        new Button("Set desired freezer temp"),
                        new Button("Open fridge door"),
                        new Button("Open freezer door")
                }
        );

        textfields = new StrongMap<>(
                new String[] {
                        "tRoomTemp",
                        "tFridgeTemp",
                        "tFreezerTemp",
                },
                new TextField[] {
                        new TextField(),
                        new TextField(),
                        new TextField()
                }
        );
        
        

        /* Build Our Interface */
        // Stores the overall frame, composed of the main frame and button frame.
        VBox overallFrame = new VBox(25);


        // Stores the main frame, composed of the status and config frames.
        HBox mainFrame = new HBox(5);

        // Stores the config frame, composed of the labels and buttons for setting certain configuration at run-time.
        VBox configFrame = new VBox(10);
        configFrame.getChildren().add((new HBox(5, textfields.get("tRoomTemp"), buttons.get("bSetRoomTemp"))));
        configFrame.getChildren().add((new HBox(5, textfields.get("tFridgeTemp"), buttons.get("bSetFridgeTemp"))));
        configFrame.getChildren().add((new HBox(5, textfields.get("tFreezerTemp"), buttons.get("bSetFreezerTemp"))));

        // Stores the status frame, composed of status labels for temperature, etc.
        VBox statusFrame = new VBox(10);
        statusFrame.getChildren().addAll(
                labels.get("lFridgeCoolingStatus"),
                labels.get("lFridgeTempStatus"),
                labels.get("lFridgeLightStatus"),
                labels.get("lFreezerCoolingStatus"),
                labels.get("lFreezerTempStatus"),
                labels.get("lFreezerLightStatus"),
                labels.get("lRoomTemp")
        );
        statusFrame.setMinWidth(200); // Should keep things long enough to prevent resizes when "Idle" switches to "Active"

        // Add the status, config frames to main frame.
        mainFrame.getChildren().addAll(statusFrame, configFrame);


        // Stores general button actions for opening/closing fridge and freezer
        HBox buttonFrame = new HBox(25);
        buttonFrame.getChildren().addAll(buttons.get("bFreezerDoorToggle"), buttons.get("bFridgeDoorToggle"));


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
        buttons.get("bSetRoomTemp").setOnAction((event) -> {
            int value = Integer.parseInt(textfields.get("tRoomTemp").getText());

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
        buttons.get("bSetFreezerTemp").setOnAction((event) -> {
            int value = Integer.parseInt(textfields.get("tFreezerTemp").getText());

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
        buttons.get("bSetFridgeTemp").setOnAction((event) -> {
            int value = Integer.parseInt(textfields.get("tFridgeTemp").getText());

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
        buttons.get("bFridgeDoorToggle").setOnAction(new ButtonHandler());
        buttons.get("bFreezerDoorToggle").setOnAction(new ButtonHandler());



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
            if (event.getSource().equals(buttons.get("bFridgeDoorToggle"))) {
                fridge.processEvent(CoolerContext.Events.DOOR_TOGGLE_EVENT);
            }
            else if (event.getSource().equals(buttons.get("bFreezerDoorToggle"))) {
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
    public static void alert(String text) {
        Label label = new Label(text);
        label.setWrapText(true);

        Alert dialog = new Alert(Alert.AlertType.ERROR);
        dialog.setHeaderText("Error");
        dialog.getDialogPane().setContent(label);
        dialog.showAndWait();
    }



    /*################################
     * Entry-Point for Stimulation
     *###############################*/

    /**
     * Constructs the fridge, freezer, room, and display references.
     */
	public static void startSimulation() {
        // Initialise Room Context
        roomContext = new RoomContext((config.get("RoomHigh") + config.get("RoomLow")) / 2);
        labels.get("lRoomTemp").setText("Room temperature: " + roomContext.getRoomTemp());

        // Initialise Fridge
        fridge = new CoolerContext(
                roomContext, // Associate our room context.
                roomContext.getRoomTemp(), // Set the fridge's current temperature to the current room temperature (which is probably right when you're first turning it on)
                (config.get("FridgeHigh") + config.get("FridgeLow")) / 2, // Set the fridge's default target temperature to the average of it's maximum and minimum (which seems reasonable)
                config.get("FridgeCompressorStartDiff"), // Set the compressor's diff for activating.
                config.get("FridgeCoolRate"), // Set the cooling rate.
                config.get("FridgeRateLossDoorOpen"), // Set the loss rate, door open.
                config.get("FridgeRateLossDoorClosed") // Set the loss rate, door closed.
        );

        // Initialise Freezer
        freezer = new CoolerContext(
        		roomContext,
        		roomContext.getRoomTemp(),
        		(config.get("FreezerHigh") + config.get("FreezerLow")) / 2,
        		config.get("FreezerCompressorStartDiff"),
        		config.get("FreezerCoolRate"),
        		config.get("FreezerRateLossDoorOpen"),
        		config.get("FreezerRateLossDoorClosed")
        );

        // Listeners for room, fridge, and freezer temp changes
        roomContext.roomTempProperty().addListener((obs, oldValue, newValue) ->
                Platform.runLater(() -> labels.get("lRoomTemp").setText("Room temperature: " + newValue)));

        Map<String, CoolerContext> coolerSet = new HashMap<>();
        coolerSet.put("Freezer", freezer);
        coolerSet.put("Fridge", fridge);

        for (Map.Entry<String, CoolerContext> entry : coolerSet.entrySet()) {
            // Observes cooler temperature changes.
            entry.getValue().coolerTempProperty().addListener((obs, oldValue, newValue) ->
                Platform.runLater(() -> {
                    labels.get("l" + entry.getKey() + "TempStatus").setText(entry.getKey() + " temperature: " + newValue.intValue());
                    labels.get("l" + entry.getKey() + "TempStatus").setTextFill(newValue.intValue() > config.get(entry.getKey() + "High") ? Color.RED : Color.BLACK);
                })
            );

            // Observer cooler state changes.
            entry.getValue().currentStateProperty().addListener((obs, oldValue, newValue) ->
                Platform.runLater(() -> {
                    // Update the button text based on the current state.
                    if (newValue.getClass().getName().equals("CoolerDoorOpenedState")) {
                        buttons.get("b" + entry.getKey() + "DoorToggle").setText("Close " + entry.getKey().toLowerCase() + " door");
                    }
                    else if (newValue.getClass().getName().equals("CoolerDoorClosedIdleState") || newValue.getClass().getName().equals("CoolerDoorClosedActiveState")) {
                        buttons.get("b" + entry.getKey() + "DoorToggle").setText("Open " + entry.getKey().toLowerCase() + " door");
                    }

                    // Update the cooling status indicator.
                    labels.get("l" + entry.getKey() + "CoolingStatus").setText(entry.getKey() + " status: " + (newValue.isCooling() ? "Active" : "Idle"));

                    // Update the light status indicator.
                    labels.get("l" + entry.getKey() + "LightStatus").setText(entry.getKey() + " light: " + (newValue.isLightOn() ? "On" : "Off"));
                })
            );
        }
    }
}