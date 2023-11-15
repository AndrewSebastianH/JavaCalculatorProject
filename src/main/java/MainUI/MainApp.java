package MainUI;

import calculator.Calculator;
import javafx.application.Application;
import javafx.geometry.Insets;
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
		window = primaryStage;
		calculator = new calculator.CalculatorImpl();

		TextField inputField = new TextField();
		inputField.setPromptText("Type a formula");
		inputField.setFocusTraversable(false);

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

//		BorderPane border = new BorderPane();
//		StackPane stackPane1 = new StackPane();
//		HBox hbox1 = new HBox(10);
		VBox root = new VBox(15);
		GridPane grid = new GridPane();
		grid.setMinSize(400, 200);
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(8);
		grid.setHgap(10);

//		grid.getChildren().addAll(inputField, calculateButton, resultLabel);
		root.getChildren().addAll(inputField, calculateButton, resultLabel);

		scene1 = new Scene(root, 600, 400);
		window.setTitle("Gigachad Calculator App - Andrew & Owen");
		window.setScene(scene1);
		window.show();

	}

}