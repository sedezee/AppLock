import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class GUI extends Application {
    List<VBox> applicationBoxes = new ArrayList<>();
    GridPane rootPane;

    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     *
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     * </p>
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages.
     * @throws Exception if something goes wrong
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        createRootPane();
        Scene scene = new Scene(rootPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("AppLock");
        primaryStage.show();
    }

    private GridPane createRootPane() {
        rootPane = new GridPane();

        // style choices
        rootPane.setVgap(10);
        rootPane.setPrefSize(900, 600);
        rootPane.add(applicationListPane(), 0, 0);

        return rootPane;
    }

    /**
     * This generates the list of applications.
     * @return
     */
    private BorderPane applicationListPane() {
        BorderPane applicationPane = new BorderPane();
        // set the width of this application to 1/3rd of the screen
        applicationPane.prefWidthProperty().bind(rootPane.widthProperty().divide(3));
        applicationPane.prefHeightProperty().bind(rootPane.heightProperty());

        VBox applications = new VBox();
        applications.prefHeightProperty().bind(rootPane.heightProperty().multiply(7).divide(8));

        // this button is in charge of generating the new application screen
        Button addAppBtn = new Button (" + Add App");
        addAppBtn.setOnAction(new EventHandler<ActionEvent>(){
            /**
             * Invoked when a specific event of the type for which this handler is
             * registered happens.
             *
             * @param event the event which occurred
             */
            @Override
            public void handle(ActionEvent event) {
                rootPane.add(newAppPane(), 1, 0);
            }
        });

        // create a vbox containing the button, set the correct width and height
        VBox addAppBox = new VBox(addAppBtn);
        addAppBox.prefHeightProperty().bind(rootPane.heightProperty().divide(8));
        addAppBtn.prefWidthProperty().bind(applicationPane.widthProperty());
        addAppBtn.prefHeightProperty().bind(applicationPane.heightProperty());

        HELP_HIGHLIGHT_PANE(addAppBox, Color.BLACK);

        // add the list of applications and the button
        applicationPane.setTop(applications);
        applicationPane.setBottom(addAppBox);
        return applicationPane;
    }

    private HBox workPanel() {
        HBox workPanel = new HBox();

        return workPanel;
    }

    private BorderPane newAppPane() {
        BorderPane newAppPane = new BorderPane();
        newAppPane.prefHeightProperty().bind(rootPane.heightProperty());
        newAppPane.prefWidthProperty().bind(rootPane.widthProperty().multiply(2).divide(3));

        // app name configuration
        TextField appName = new TextField("APP NAME");
        appName.setAlignment(Pos.CENTER);
        appName.prefWidthProperty().bind(newAppPane.widthProperty());
        newAppPane.setTop(appName);

        HELP_HIGHLIGHT_PANE(newAppPane, Color.GREEN);

        // figure out schedule things in a moment
        newAppPane.setRight(generateSchedulePane());


        TextField appDescription = new TextField("APP DESCRIPTION");
        Button discard = new Button("Discard");
        Button save = new Button("Save");

        return newAppPane;
    }

    private BorderPane generateSchedulePane() {
        BorderPane schedulePane = new BorderPane();
        schedulePane.setPadding(new Insets(10, 10, 10, 10));

        schedulePane.prefWidthProperty().bind(rootPane.widthProperty().multiply(2).divide(3));
        schedulePane.maxHeightProperty().bind(rootPane.heightProperty().divide(3));

        HELP_HIGHLIGHT_PANE(schedulePane, Color.RED);

        TextField scheduleTxt = new TextField("SCHEDULE");
        scheduleTxt.setEditable(false);
        scheduleTxt.setAlignment(Pos.CENTER);
        scheduleTxt.setBackground(Background.EMPTY);

        schedulePane.setTop(scheduleTxt);

        HashSet<HBox> schedulePairs = new HashSet<>();
        schedulePairs.add(scheduleItem(schedulePairs, null));

        // TODO scroll pane has a visible outline, fix
        VBox scheduleList = scheduleList(schedulePairs);
        ScrollPane scrollPane = new ScrollPane(scheduleList);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        Button addScheduleItem = new Button("Add time");
        addScheduleItem.prefWidthProperty().bind(schedulePane.widthProperty());
        addScheduleItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                HBox scheduleItem = scheduleItem(schedulePairs, scheduleList);
                schedulePairs.add(scheduleItem);
                scheduleList.getChildren().add(scheduleItem);
            }
        });

        VBox scrollButtonContainer = new VBox(scrollPane, addScheduleItem);
        scrollButtonContainer.minHeightProperty().bind(rootPane.heightProperty().divide(3));

        HELP_HIGHLIGHT_PANE(scrollButtonContainer, Color.YELLOW);

        schedulePane.setCenter(scrollButtonContainer);

        TextField appDescription = new TextField("APP DESCRIPTION");
        appDescription.prefHeightProperty().bind(rootPane.heightProperty().divide(3));

        schedulePane.setBottom(appDescription);

        return schedulePane;
    }

    private VBox scheduleList(HashSet<HBox> schedulePairs) {
        VBox schedule = new VBox();
        schedulePairs.stream().forEach(p -> schedule.getChildren().add(p));
        return schedule;
    }

    private HBox scheduleItem(HashSet<HBox> schedulePairs, VBox scheduleList) {
        HBox item = new HBox();

        item.setPadding(new Insets(5, 50, 5, 50));
        item.setSpacing(5);
        ObservableList<String> time =
                FXCollections.observableArrayList(
                        "AM",
                            "PM"
                );

        // fix the spacing with the AmPms
        TextField timeOne = new TextField("12:00");
        ComboBox amPmOne = new ComboBox(time);
        TextField timeTwo = new TextField("12:00");
        ComboBox amPmTwo = new ComboBox(time);
        Button x = new Button("X");

        item.getChildren().addAll(timeOne, amPmOne, timeTwo, amPmTwo, x);

        x.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                scheduleList.getChildren().remove(item);
                schedulePairs.remove(item);
            }
        });

        return item;
    }

    private void addApp() {


    }

    public static void go(String... args) {
        launch();
    }

    private void HELP_HIGHLIGHT_PANE(Pane element, Color color) {
        element.setBorder(
                    new Border(
                            new BorderStroke(
                                    color,
                                    BorderStrokeStyle.SOLID,
                                    CornerRadii.EMPTY,
                                    BorderWidths.DEFAULT)
                    )
        );

    }

}
