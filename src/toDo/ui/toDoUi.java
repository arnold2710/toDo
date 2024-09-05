package toDo.ui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
		toDo.setFont(Font.font("System", FontWeight.BOLD, 14));
		newTask.setStyle("-fx-background-color: #3285a8; -fx-text-fill: #ffffff");
		top.setHgrow(spacer, Priority.ALWAYS);
		top.getChildren().addAll(toDo, spacer, newTask);
		top.setPadding(new Insets(10, 15, 15, 15));;
		
		ListView<String> list = new ListView<String>();
		ObservableList<String> items =FXCollections.observableArrayList("test");
		list.setItems(items);
		list.setStyle("-fx-text-fill: #ffffff; -fx-background-color: transparent");

		
        list.setCellFactory(lv -> new ListCell<>() {
            private final CheckBox checkBox = new CheckBox();
            private final HBox hbox = new HBox(checkBox);
            {
            	hbox.setStyle("-fx-background-color: #000000");
            	checkBox.setStyle("-fx-background-color: #000000; -fx-text-fill: #ffffff");
            	setStyle("-fx-background-color: #000000");
            }
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    checkBox.setText(item);
                    checkBox.setSelected(false);
                    setGraphic(hbox);
                }
            }
        });

        // Mache die gesamte ListView transparent
        
	       
		main.getChildren().addAll(top, list);
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
