import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;


import java.awt.*;


public class Main extends Application {
    Stage mainstage;
    Stage mainstage1;
    Stage mainstage2;
    Stage mainstage3;
    Stage mainstage4;


    public static void main(String[] args) {
        launch(args);
    }


    public void start(Stage primaryStage) {
        mainstage = primaryStage;

        HBox root = new HBox();
        Scene scene = new Scene(root, 400, 400);
        root.getStyleClass().add("background");

        Button btnNormal = new Button("_Start Program");

        btnNormal.setId("myButton"); // Set the ID of the button
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        root.getChildren().addAll(btnNormal);
        btnNormal.setOnAction(e -> {
            startButton();
        });

        root.setAlignment(Pos.CENTER);
        root.setSpacing(10);

        mainstage.setScene(scene);
        mainstage.setTitle("Double Big Harvard combo large arithmetic shifts architecture");
        mainstage.show();
    }

    private void startButton() {

        Processor.runProgram();
        HBox root = new HBox();
        Scene scene = new Scene(root, 750, 750);
        Button trace = new Button("_Trace");
        Button showInstructions = new Button("_Show instructions");
        Button showRegisterFile = new Button("_Show register file");
        Button showDataMemory = new Button("_Show data memory");


        trace.setId("myButton");
        showInstructions.setId("myButton");
        showRegisterFile.setId("myButton");
        showDataMemory.setId("myButton");
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        root.getStyleClass().add("background");

        root.getChildren().addAll(trace, showInstructions, showRegisterFile, showDataMemory);
        trace.setOnAction(e -> {
            showTracing();
        });
        showInstructions.setOnAction(e -> {
            showinstructionButton();
        });
        showRegisterFile.setOnAction(e -> {
            showRegisterButton();
        });
        showDataMemory.setOnAction(e -> {
            showDataMemory();
        });
        root.setAlignment(Pos.CENTER);
        root.setSpacing(10);
//        mainstage.setWidth(1000);
//        mainstage.setHeight(1000);
        mainstage.centerOnScreen();
        mainstage.setScene(scene);
        mainstage.centerOnScreen();
        mainstage.setTitle("Double Big Harvard combo large arithmetic shifts architecture");
        mainstage.show();


    }

    private void showTracing() {
        Processor.pw.flush();
        Processor.pw.close();
        String s = Processor.stringWriter.toString();


        TextArea textArea = new TextArea();


        textArea.setText(s);
        textArea.setEditable(false);
        textArea.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        StackPane stackPane = new StackPane(textArea);

       // textArea.opacityProperty().setValue(0.5);
        textArea.getStyleClass().add("dd");
        stackPane.getStyleClass().add("background");


        Screen screen = Screen.getPrimary();
        double screenWidth = screen.getBounds().getWidth();
        double screenHeight = screen.getBounds().getHeight();


        Scene scene = new Scene(stackPane, screenHeight / 2, screenWidth / 2);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());


        mainstage1 = new Stage();
        mainstage1.setTitle("Tracing");
        mainstage1.setScene(scene);
        //mainstage1.setMaximized(true);
        mainstage1.show();


    }

    public void showinstructionButton() {
        StringBuilder sb = new StringBuilder();
        sb.append("Instructions:" + "\n");
        sb.append("----------------------------" + "\n");
        for (int i = 0; i < InstructionMemory.instructions.length && InstructionMemory.instructions[i] != null; i++) {
            sb.append("Instruction " + (i) + ": " + InstructionMemory.instructions[i].printbinaryInstruction() + "\n");
            sb.append("----------------------------------" + "\n");
        }


        TextArea textArea = new TextArea(sb.toString());

        textArea.setEditable(false);
        textArea.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        StackPane stackPane = new StackPane(textArea);
        textArea.getStyleClass().add("dd");
        stackPane.getStyleClass().add("background");


        //stac vbox = new VBox(textArea);

        Screen screen = Screen.getPrimary();
        double screenWidth = screen.getBounds().getWidth();
        double screenHeight = screen.getBounds().getHeight();


        Scene scene = new Scene(stackPane, screenHeight / 2, screenWidth / 2);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        mainstage2 = new Stage();
        mainstage2.setTitle("Instructions");
        mainstage2.setScene(scene);
        //mainstage2.setMaximized(true);
        mainstage2.show();
        // mainstage2 = new Stage();

    }

    private void showRegisterButton() {
        StringBuilder sb = new StringBuilder();
        sb.append("General-Purpose Registers Content" + "\n");
        sb.append("----------------------------" + "\n");
        for (int i = 0; i < RegisterFile.registers.length; i++) {
            sb.append("Register " + (i) + ": " + RegisterFile.registers[i] + "\n");
            sb.append("----------------------------------" + "\n");
        }
        sb.append("----------------------------------" + "\n");

        sb.append("\n");
        sb.append("PC Register  Content: ");

        sb.append(InstructionMemory.pcRegisterValue + "\n");
        sb.append("----------------------------------" + "\n");
        sb.append("----------------------------------" + "\n");
        sb.append("SREG Register  Content: \n");
        sb.append("----------------------------------" + "\n");
        sb.append("Carry Flag: " + StatusRegister.carryFlag + "\n");
        sb.append("----------------------------------" + "\n");
        sb.append("Overflow Flag: " + StatusRegister.overFlowFlag + "\n");
        sb.append("----------------------------------" + "\n");
        sb.append("Negative Flag: " + StatusRegister.negativeFlag + "\n");
        sb.append("----------------------------------" + "\n");
        sb.append("Zero Flag: " + StatusRegister.zeroFlag + "\n");
        sb.append("----------------------------------" + "\n");
        sb.append("Sign Flag: " + StatusRegister.signFlag + "\n");

        sb.append("----------------------------------" + "\n");
        sb.append(StatusRegister.printstatusreg() + "\n");
        sb.append("----------------------------------" + "\n");
        TextArea textArea = new TextArea(sb.toString());
        textArea.setEditable(false);
        textArea.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        StackPane stackPane = new StackPane(textArea);


        //stac vbox = new VBox(textArea);
        textArea.getStyleClass().add("dd");
        stackPane.getStyleClass().add("background");

        Screen screen = Screen.getPrimary();
        double screenWidth = screen.getBounds().getWidth();
        double screenHeight = screen.getBounds().getHeight();


        Scene scene = new Scene(stackPane, screenHeight / 2, screenWidth / 2);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        mainstage3 = new Stage();
        mainstage3.setTitle("Register File");
        mainstage3.setScene(scene);
        //mainstage3.setMaximized(true);
        mainstage3.show();


    }

    private void showDataMemory() {
        StringBuilder sb = new StringBuilder();
        sb.append("Data Memory Content" + "\n");
        sb.append("----------------------------" + "\n");
        for (int i = 0; i < DataMemory.contents.length; i++) {
            sb.append("Address " + (i) + ": " + DataMemory.contents[i] + "\n");
            sb.append("----------------------------------" + "\n");
        }
        TextArea textArea = new TextArea(sb.toString());
        textArea.setEditable(false);
        textArea.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        StackPane stackPane = new StackPane(textArea);
        Screen screen = Screen.getPrimary();
        double screenWidth = screen.getBounds().getWidth();
        double screenHeight = screen.getBounds().getHeight();

        textArea.getStyleClass().add("dd");
        stackPane.getStyleClass().add("background");

        Scene scene = new Scene(stackPane, screenHeight / 2, screenWidth / 2);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        mainstage4 = new Stage();
        mainstage4.setTitle("Data Memory");
        mainstage4.setScene(scene);
        //mainstage3.setMaximized(true);
        mainstage4.show();


    }
}
