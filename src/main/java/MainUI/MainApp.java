package MainUI;

import java.nio.file.Paths;

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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
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
		// start musik
		music();

//		Window size
		window = primaryStage;
		window.setMaxHeight(580);
		window.setMaxWidth(440);
		window.setMinHeight(580);
		window.setMinWidth(440);

//		Calculator logic call
		calculator = new calculator.CalculatorImpl();

//		Calculator icon
		Image gigachad = new Image("/images/gigachad.png");
		primaryStage.getIcons().add(gigachad);

//		Root element
		VBox root = new VBox(15);
		root.setAlignment(Pos.CENTER);
		root.setStyle("-fx-background-color: #f2f2f2;");

//		Input Field HBox
		HBox inputFieldHbox = new HBox();
		inputFieldHbox.setPadding(new Insets(15, 12, 15, 12));
		inputFieldHbox.setAlignment(Pos.CENTER);
		inputFieldHbox.setMaxWidth(Double.MAX_VALUE);
		inputFieldHbox.setStyle("-fx-background-color: #1C2541; -fx-font-size: 16pt;");

//		Main Formula Input Field
		TextField inputField = new TextField();
		inputField.setPromptText("Type a formula");
		inputField.setMinSize(375, 50);
		inputField.setMaxSize(Double.MAX_VALUE, 50);
		inputField.setFocusTraversable(false);

		inputFieldHbox.getChildren().addAll(inputField);

//		Grid - Number Buttons  
		GridPane numbersGrid = new GridPane();
		numbersGrid.setPadding(new Insets(10, 10, 10, 10));
		numbersGrid.setVgap(10);
		numbersGrid.setHgap(10);

//		Number Buttons
		int buttonIndex = 1;
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				Button button = new Button(Integer.toString(buttonIndex));
				button.setMinSize(60, 50);
				numbersGrid.add(button, col, row);
				addButtonTextToInputField(button, inputField);
				buttonIndex++;
			}
		}

		Button zeroButton = new Button("0");
		zeroButton.setMinSize(60, 50);
		addButtonTextToInputField(zeroButton, inputField);
		numbersGrid.add(zeroButton, 1, 3);

//		Remove button
		Button removeButton = new Button();
		removeButton.setMinSize(60, 50);
		removeButton.getStyleClass().add("backspace-button");
		Image removeImage = new Image("/images/backspace.png");
		ImageView imageView = new ImageView(removeImage);
		imageView.setFitWidth(25);
		imageView.setFitHeight(25);
		removeButton.setGraphic(imageView);
		removeButtonClick(removeButton, inputField);
		numbersGrid.add(removeButton, 2, 3);

//		All clear Button
		Button ACButton = new Button("AC");
		ACButton.setMinSize(60, 50);
		ACButton.getStyleClass().add("ac-button");
		ACButtonClick(ACButton, inputField);
		numbersGrid.add(ACButton, 0, 3);

//		Grid - Operators Button
		GridPane operatorsGrid = new GridPane();
		operatorsGrid.setPadding(new Insets(10, 10, 10, 10));
		operatorsGrid.setVgap(10);
		operatorsGrid.setHgap(10);

//		Operator Buttons
		String[] operators = { "+", "-", "/", "*", "(", ")" };
		int operatorButtonIndex = 0;
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 2; col++) {
				if (operatorButtonIndex < operators.length) {
					Button operatorButton = new Button(operators[operatorButtonIndex]);
					operatorButton.setMinSize(60, 50);
					operatorButton.getStyleClass().add("operator-button");
					operatorsGrid.add(operatorButton, col, row);
					addButtonTextToInputField(operatorButton, inputField);
					operatorButtonIndex++;
				}
			}
		}

//		Operators and Numbers HBox
		HBox numbersAndOperatorsGrid = new HBox(25);
		numbersAndOperatorsGrid.setAlignment(Pos.CENTER);
		numbersAndOperatorsGrid.getChildren().addAll(numbersGrid, operatorsGrid);

//		Calculate Button
		Button calculateButton = new Button("CALCULATE");
		calculateButton.getStyleClass().add("calculate-button");

		calculateButton.setMinSize(380, 50);

		VBox resultVbox = new VBox();
		resultVbox.setPadding(new Insets(15, 0, 20, 0));
		resultVbox.setAlignment(Pos.CENTER);

		Label resultTitle = new Label("RESULT");
		resultTitle.getStyleClass().add("result-title");

		Label resultLabel = new Label();
		resultLabel.getStyleClass().add("result");

		calculateButton.setOnAction(e -> {
			String formula = inputField.getText();
			try {
				double result = calculator.calculate(formula);
				resultLabel.setStyle("-fx-text-fill: white;");
				if (result % 1 == 0) {
					resultLabel.setText(String.format("%.0f", result));
				} else {
					resultLabel.setText(String.valueOf(result));
				}
			} catch (Exception ex) {
				resultLabel.setStyle("-fx-text-fill: red;");
				resultLabel.setText("Error: " + ex.getMessage());
			}
		});

//		Result VBox
		resultVbox.getChildren().addAll(resultTitle, resultLabel);
		resultVbox.setStyle("-fx-background-color: #1C2541;");

		root.getChildren().addAll(inputFieldHbox, numbersAndOperatorsGrid, calculateButton, resultVbox);

//		Main Window and Scene Title call
		scene1 = new Scene(root);
		scene1.getStylesheets().add("./styles.css");
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

//	All Clear button handler
	private void ACButtonClick(Button button, TextField inputField) {
		button.setOnAction(e -> {
			inputField.setText("");
		});
	}

	// Muisk
	MediaPlayer mediaPlayer;

	public void music() {
		String s = "gigachad.mp3";
		Media h = new Media(Paths.get(s).toUri().toString());
		mediaPlayer = new MediaPlayer(h);
		mediaPlayer.play();

	}

}