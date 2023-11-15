package MainUI;

import calculator.Calculator;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {

	private Calculator calculator;

	Stage window;
	Scene scene1, scene2;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
//		Window size
		window = primaryStage;
		window.setMaxHeight(600);
		window.setMaxWidth(500);
		window.setMinHeight(515);
		window.setMinWidth(420);

//		Calculator logic call
		calculator = new calculator.CalculatorImpl();

//		Root element
		VBox root = new VBox(15);
		root.setAlignment(Pos.CENTER);

//		Main Formula Input Field
		TextField inputField = new TextField();
		inputField.setPromptText("Type a formula");
		inputField.setMinSize(200, 50);
		inputField.setMaxSize(360, 50);
		inputField.setFocusTraversable(false);

//		Number Buttons grid
		GridPane grid = new GridPane();
		grid.setMinSize(400, 200);
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(10);
		grid.setHgap(10);

//		Number Buttons
		int buttonIndex = 1;
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				Button button = new Button(Integer.toString(buttonIndex));
				button.setMinSize(50, 50); // Adjust the size as needed
				grid.add(button, col, row);
				buttonIndex++;
			}
		}
		Button zeroButton = new Button("0");
		zeroButton.setMinSize(50, 50);
		grid.add(zeroButton, 1, 3);

//		Calculate Button
		Button calculateButton = new Button("Calculate");
		calculateButton.setMinSize(100, 50);
		Label resultLabel = new Label();

		calculateButton.setOnAction(e -> {
			String formula = inputField.getText();
			try {
				double result = calculator.calculate(formula);
				resultLabel.setText("Result: " + result);
			} catch (Exception ex) {
				resultLabel.setText("Error: " + ex.getMessage());
			}
		});

		root.getChildren().addAll(inputField, grid, calculateButton, resultLabel);

		scene1 = new Scene(root);
		window.setTitle("Gigachad Calculator App - Andrew & Owen");
		window.setScene(scene1);
		window.show();

	}

}