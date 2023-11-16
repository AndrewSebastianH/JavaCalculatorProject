package MainUI;

import calculator.Calculator;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainApp extends Application {

	private Calculator calculator;

	Stage window;
	Scene scene1, scene2;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
//		Music Call
		music();

//		Window size
		window = primaryStage;
		window.setMaxHeight(700);
		window.setMaxWidth(480);
		window.setMinHeight(600);
		window.setMinWidth(480);

//		Calculator logic call
		calculator = new calculator.CalculatorImpl();

//		Calculator icon
		Image gigachad = new Image("/images/gigachad.png");
		primaryStage.getIcons().add(gigachad);

//		Root element
		VBox root = new VBox();
		root.setAlignment(Pos.CENTER);
		root.setStyle("-fx-background-color: #f2f2f2;");

//		Input Field HBox
		HBox inputFieldHbox = new HBox();
		inputFieldHbox.setPadding(new Insets(15, 12, 15, 12));
		inputFieldHbox.setAlignment(Pos.CENTER);
		inputFieldHbox.setMaxWidth(Double.MAX_VALUE);
		inputFieldHbox.setStyle(
				"-fx-background-color: radial-gradient(center 80% 20%, radius 75%, #FFD60A, #FF9F1C); -fx-font-size: 16pt;");

//		Main Formula Input Field
		TextField inputField = new TextField();
		inputField.setPromptText("Type a formula");
		inputField.setMinSize(440, 50);
		inputField.setMaxSize(Double.MAX_VALUE, 50);
		inputField.setFocusTraversable(false);

		inputFieldHbox.getChildren().addAll(inputField);

//		Grid - Number Buttons  
		GridPane numbersGrid = new GridPane();
		numbersGrid.setPadding(new Insets(25, 10, 25, 10));
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
		operatorsGrid.setPadding(new Insets(25, 10, 25, 10));
		operatorsGrid.setVgap(10);
		operatorsGrid.setHgap(10);

//		Operator Buttons
		String[] operators = { "+", "-", "/", "*", "(", ")", ".", "√", "^" };
		int operatorButtonIndex = 0;
		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 3; col++) {
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
		calculateButton.setPadding(new Insets(15, 0, 20, 0));
		calculateButton.setMinSize(445, 50);

		VBox resultVbox = new VBox();
		resultVbox.setPadding(new Insets(-20, 0, -10, 0));
		resultVbox.setAlignment(Pos.CENTER);
		resultVbox.setMinHeight(120);

		Label resultLabel = new Label("0");
		resultLabel.getStyleClass().add("result-label");
		resultLabel.setStyle("-fx-text-fill: #ff8c00");

// 		Error Label
		Label errorLabel = new Label("");
		errorLabel.getStyleClass().add("result-label");
		errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14pt;");

//		StackPane to stack the labels
		StackPane labelsStackPane = new StackPane();
		labelsStackPane.setAlignment(Pos.CENTER);
		labelsStackPane.getChildren().addAll(resultLabel, errorLabel);

		calculateButton.setOnAction(e -> {
			String formula = inputField.getText();
			try {
				double result = calculator.calculate(formula);
				resultLabel.setStyle("-fx-text-fill: #ff8c00");
				resultLabel.getStyleClass().add("result-label");
				if (result % 1 == 0) {
					resultLabel.setText(String.format("%.0f", result));
				} else {
					resultLabel.setText(String.valueOf(result));
				}
				playSuccessSfx();
				addScaleAnimation(resultLabel);

				// Clear error label
				errorLabel.setText("");
				errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14pt;");
			} catch (Exception ex) {
				resultLabel.setText("");
				playErrorSfx();
				errorLabel.setText("" + ex.getMessage());
				addErrorAnimation(errorLabel);
			}
		});

//		Result VBox
		resultVbox.getChildren().addAll(labelsStackPane);
		resultVbox.setStyle("-fx-background-color: #f2f2f2;");

		root.getChildren().addAll(inputFieldHbox, resultVbox, numbersAndOperatorsGrid, calculateButton);

//		History HBox

//		Main Window and Scene Title call
		scene1 = new Scene(root);
		scene1.getStylesheets().add("./styles.css");
		window.setTitle("Gigachad Calculator App - Andrew & Owen");
		window.setScene(scene1);
		window.show();

	}

//	Numbers or Operator Button Function handler
	private void addButtonTextToInputField(Button button, TextField inputField) {
		button.setOnAction(e -> {
			inputField.appendText(button.getText() + " ");
			playButtonSfx();
		});
	}

//	Remove button handler
	private void removeButtonClick(Button button, TextField inputField) {
		button.setOnAction(e -> {
			String currentText = inputField.getText();
			inputField.setText(currentText.length() >= 2 ? currentText.substring(0, currentText.length() - 2) : "");
			playButtonSfx2();
		});
	}

//	All Clear button handler
	private void ACButtonClick(Button button, TextField inputField) {
		button.setOnAction(e -> {
			inputField.setText("");
			playButtonSfx2();
		});
	}

//  Music
	MediaPlayer gigachadMusic;

	public void music() {
		String source = "/sounds/gigachad.mp3";
		Media music = new Media(getClass().getResource(source).toExternalForm());
		gigachadMusic = new MediaPlayer(music);
		gigachadMusic.setVolume(0.05);
		gigachadMusic.play();

	}

	MediaPlayer sfxPlayer;

//	Play button SFX
	public void playButtonSfx() {
		String source = "/sounds/button-click.mp3";
		Media buttonClick = new Media(getClass().getResource(source).toExternalForm());
		sfxPlayer = new MediaPlayer(buttonClick);
		sfxPlayer.play();
	}

	public void playButtonSfx2() {
		String source = "/sounds/button-click2.mp3";
		Media buttonClick = new Media(getClass().getResource(source).toExternalForm());
		sfxPlayer = new MediaPlayer(buttonClick);
		sfxPlayer.play();
	}

	public void playErrorSfx() {
		String source = "/sounds/errorfart.mp3";
		Media buttonClick = new Media(getClass().getResource(source).toExternalForm());
		sfxPlayer = new MediaPlayer(buttonClick);
		sfxPlayer.play();
	}

	public void playSuccessSfx() {
		String source = "/sounds/success.mp3";
		Media buttonClick = new Media(getClass().getResource(source).toExternalForm());
		sfxPlayer = new MediaPlayer(buttonClick);
		sfxPlayer.setVolume(0.5);
		sfxPlayer.play();
	}

//	Result Scale Animation
	public void addScaleAnimation(Label label) {
		ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(2), label);
//		RotateTransition rotateTransition = new RotateTransition(Duration.seconds(2), label);
		scaleTransition.setFromX(1);
		scaleTransition.setToX(1.4);
		scaleTransition.setFromY(1);
		scaleTransition.setToY(1.4);
		scaleTransition.setAutoReverse(true);
		scaleTransition.setCycleCount(ScaleTransition.INDEFINITE);

		scaleTransition.setOnFinished(event -> scaleTransition.stop());
		scaleTransition.play();

	}

//	Error Animation
	public void addErrorAnimation(Label label) {
		ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(1), label);
		RotateTransition rotateTransition = new RotateTransition(Duration.seconds(3), label);
		scaleTransition.setFromX(1);
		scaleTransition.setToX(1.4);
		scaleTransition.setFromY(1);
		scaleTransition.setToY(1.4);
		scaleTransition.setAutoReverse(true);
		scaleTransition.setCycleCount(ScaleTransition.INDEFINITE);

		rotateTransition.setByAngle(360);
		rotateTransition.setAxis(Rotate.Y_AXIS);
		rotateTransition.setCycleCount(ScaleTransition.INDEFINITE);

		scaleTransition.setOnFinished(event -> scaleTransition.stop());
		rotateTransition.setOnFinished(event -> rotateTransition.stop());

		scaleTransition.play();
		rotateTransition.play();

	}

}