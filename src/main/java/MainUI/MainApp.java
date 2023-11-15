package MainUI;

import calculator.Calculator;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
		root.setStyle("-fx-background-color: #F2F2F2;");

//		Input Field HBox
		HBox inputFieldHbox = new HBox();
		inputFieldHbox.setPadding(new Insets(15, 12, 15, 12));
		inputFieldHbox.setAlignment(Pos.CENTER);
		inputFieldHbox.setMaxWidth(Double.MAX_VALUE);
		inputFieldHbox.setStyle("-fx-background-color: #1C2541;");

//		Main Formula Input Field
		TextField inputField = new TextField();
		inputField.setPromptText("Type a formula");
		inputField.setMinSize(375, 50);
		inputField.setMaxSize(Double.MAX_VALUE, 50);
		inputField.setFocusTraversable(false);

		inputFieldHbox.getChildren().addAll(inputField);

//		Grid - Number Buttons  
		GridPane numbersGrid = new GridPane();
		numbersGrid.setMinSize(400, 200);
		numbersGrid.setPadding(new Insets(10, 10, 10, 10));
		numbersGrid.setVgap(10);
		numbersGrid.setHgap(10);

//		Number Buttons
		int buttonIndex = 1;
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				Button button = new Button(Integer.toString(buttonIndex));
				button.setMinSize(50, 50);
				numbersGrid.add(button, col, row);
				addButtonTextToInputField(button, inputField);
				buttonIndex++;
			}
		}

		Button zeroButton = new Button("0");
		zeroButton.setMinSize(50, 50);
		addButtonTextToInputField(zeroButton, inputField);
		numbersGrid.add(zeroButton, 1, 3);

		Button removeButton = new Button();
		removeButton.setMinSize(50, 50);
		Image removeImage = new Image("/images/backspace.png");
		ImageView imageView = new ImageView(removeImage);
		imageView.setFitWidth(25);
		imageView.setFitHeight(25);
		removeButton.setGraphic(imageView);

		removeButtonClick(removeButton, inputField);
		numbersGrid.add(removeButton, 2, 3);

//		Grid - Operators Button
		GridPane operatorsGrid = new GridPane();
		operatorsGrid.setMinSize(400, 200);
		operatorsGrid.setPadding(new Insets(10, 10, 10, 10));
		operatorsGrid.setVgap(10);
		operatorsGrid.setHgap(10);

//		Operator Buttons

//		Operators and Numbers HBox
		HBox numbersAndOperatorsGrid = new HBox(15);
		numbersAndOperatorsGrid.getChildren().addAll(numbersGrid, operatorsGrid);

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

		root.getChildren().addAll(inputFieldHbox, numbersGrid, calculateButton, resultLabel);

		scene1 = new Scene(root);
		window.setTitle("Gigachad Calculator App - Andrew & Owen");
		window.setScene(scene1);
		window.show();

	}

//	Numbers or Operator Button Function handler
	private void addButtonTextToInputField(Button button, TextField inputField) {
		button.setOnAction(e -> inputField.appendText(button.getText() + " "));
	}

//	Remove button handler
	private void removeButtonClick(Button button, TextField inputField) {
		button.setOnAction(e -> {
			String currentText = inputField.getText();
			inputField.setText(currentText.length() >= 2 ? currentText.substring(0, currentText.length() - 2) : "");
		});
	}

}