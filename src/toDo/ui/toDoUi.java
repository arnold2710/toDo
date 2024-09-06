package toDo.ui;

import java.net.URL;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
	ListView<String> list = new ListView<String>();
	ObservableList<String> items =FXCollections.observableArrayList("test", "putzen");

	
	public void start(Stage primaryStage) throws Exception{
		toDo.setTextFill(Color.WHITE);
		toDo.setFont(Font.font("System", FontWeight.BOLD, 14));
		newTask.setStyle("-fx-background-color: #3285a8; -fx-text-fill: #ffffff");
		top.setHgrow(spacer, Priority.ALWAYS);
		top.getChildren().addAll(toDo, spacer, newTask);
		top.setPadding(new Insets(10, 15, 15, 15));
		
		newTask.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Add Task");
            dialog.setHeaderText(null);
            dialog.setGraphic(null);
            dialog.setContentText("New Task:");
            dialog.getDialogPane().setStyle("-fx-background-color: #000000; -fx-text-fill: #ffffff");
            primaryStage.setAlwaysOnTop(false);
            dialog.showAndWait().ifPresent(input -> {
                if (!input.trim().isEmpty()) {
                    items.add(input);
                }
            });
            primaryStage.setAlwaysOnTop(true);
		});
		

		list.setItems(items);
		list.setStyle("-fx-text-fill: #ffffff; -fx-background-color: transparent");

		
        list.setCellFactory(lv -> new ListCell<>() {
        	private final Button delete = createButton("trash.png");
            private final CheckBox checkBox = new CheckBox();
            private final HBox hbox = new HBox(delete, checkBox);
            
            {
            	hbox.setSpacing(5);
            	hbox.setAlignment(Pos.CENTER_LEFT);
            	hbox.setStyle("-fx-background-color: #000000");
            	checkBox.setStyle("-fx-background-color: #000000; -fx-text-fill: #ffffff");
            	setStyle("-fx-background-color: #000000");
            	
            	delete.setOnAction(event -> getListView().getItems().remove(getItem()));
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
        
	       
		main.getChildren().addAll(top, list);
		main.setBackground(null);
		primaryStage.setTitle("toDo");
		Scene scene = new Scene(main, 600, 400);
		scene.setFill(Color.BLACK);
		primaryStage.setScene(scene);
		primaryStage.setAlwaysOnTop(true);
		primaryStage.show();
	}
	
	private Button createButton(String iconfile) {
		Button button = null;
		try {
		URL url = getClass().getResource("/icon/" + iconfile);
		Image icon = new Image(url.toString());
		ImageView imageView = new ImageView(icon);
		imageView.setFitHeight(15);
		imageView.setFitWidth(15);
		button = new Button("", imageView);
		button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		button.setStyle("-fx-background-color: transparent");
		} catch (Exception e) {
		System.out.println("Image " + "icon/"
		+ iconfile + " not found!");
		System.exit(-1);
		}
		return button;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
