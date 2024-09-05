package toDo.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class toDoUi extends Application {
	private Label toDo = new Label("To Do");
	private Button newTask = new Button("New Task");
	private Label all = new Label("ALL");
	private Label inProgress = new Label("IN PROGRESS");
	private Label completed = new Label("COMPLETED");
	private VBox main = new VBox();
	private HBox top = new HBox();
	private Region spacer = new Region();
	
	public void start(Stage primaryStage) throws Exception{
		toDo.setTextFill(Color.WHITE);
		newTask.setStyle("-fx-background-color: #3285a8; -fx-text-fill: #ffffff");
		top.setHgrow(spacer, Priority.ALWAYS);
		top.getChildren().addAll(toDo, spacer, newTask);
		top.setPadding(new Insets(10, 15, 15, 15));;

		main.getChildren().addAll(top);
		main.setBackground(null);
		primaryStage.setTitle("toDo");
		Scene scene = new Scene(main, 600, 400);
		scene.setFill(Color.BLACK);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
