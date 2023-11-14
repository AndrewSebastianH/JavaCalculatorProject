package MainUI;

import calculator.Calculator;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {

	private Calculator calculator;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		calculator = new calculator.CalculatorImpl();

		TextField inputField = new TextField();
		Button calculateButton = new Button("Calculate");
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
		VBox root = new VBox(10);
		root.getChildren().addAll(inputField, calculateButton, resultLabel);

		Scene scene = new Scene(root, 600, 400);
		primaryStage.setTitle("Gigachad Calculator App - Andrew & Owen");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

}