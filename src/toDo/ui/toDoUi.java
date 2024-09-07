package toDo.ui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

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
import javafx.scene.layout.BorderPane;
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
	private VBox main = new VBox();
	private HBox top = new HBox();
	private Region spacer = new Region();
	ListView<Task> list = new ListView<Task>();
	ObservableList<Task> items =FXCollections.observableArrayList();
	private final String filePath = "tasks.txt";
	private CheckBox setAlwaysOnTop = new CheckBox("Always On Top");
	boolean changeAlwaysOnTop = false;

	
	public void start(Stage primaryStage) throws Exception{
		loadTasksFromFile();
		setAlwaysOnTop.setStyle("-fx-text-fill: #ffffff");
		toDo.setTextFill(Color.WHITE);
		toDo.setFont(Font.font("System", FontWeight.BOLD, 14));
		newTask.setStyle("-fx-background-color: #3285a8; -fx-text-fill: #ffffff");
		top.setHgrow(spacer, Priority.ALWAYS);
		top.getChildren().addAll(toDo, spacer, newTask);
		top.setPadding(new Insets(10, 15, 15, 15));
		
		newTask.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.getDialogPane().setStyle("-fx-background-color: #000000");
            dialog.setTitle("Add Task");
            dialog.setHeaderText(null);
            dialog.setGraphic(null);
            dialog.setContentText("New Task:");
            dialog.getDialogPane().lookupAll(".label").forEach(node -> {
                ((Label) node).setStyle("-fx-text-fill: #ffffff;");
            });
            primaryStage.setAlwaysOnTop(false);
            dialog.showAndWait().ifPresent(input -> {
                if (!input.trim().isEmpty()) {
                    items.add(new Task(input));
                    saveTasksToFile();
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
            	
            	delete.setOnAction(event -> {
            	getListView().getItems().remove(getItem());
            	saveTasksToFile();
            	});
            	
            	checkBox.setOnAction(e -> {
            		Task task = getItem();
            		if (task != null) {
            			task.setCompleted(checkBox.isSelected());
            		}
            	});
            }
            @Override
            protected void updateItem(Task item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    checkBox.setText(item.getName());
                    checkBox.setSelected(item.isCompleted());
                    setGraphic(hbox);
                }
            }
        });
        
        setAlwaysOnTop.setOnAction(e -> {
        	if (changeAlwaysOnTop == false) {
        		changeAlwaysOnTop = true;
        		primaryStage.setAlwaysOnTop(changeAlwaysOnTop);
        	} else {
        		changeAlwaysOnTop = false;
        		primaryStage.setAlwaysOnTop(changeAlwaysOnTop);
        	}
        });
        
		main.getChildren().addAll(top, list, setAlwaysOnTop);
		main.setPadding(new Insets(0, 0, 5, 5));
		main.setBackground(null);
		primaryStage.setTitle("toDo");
		Scene scene = new Scene(main, 600, 400);
		scene.setFill(Color.BLACK);
		primaryStage.getIcons().add(new Image("/icon/logo.png"));
		primaryStage.setScene(scene);
		primaryStage.setAlwaysOnTop(changeAlwaysOnTop);
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
	
	void saveTasksToFile() {
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
			for (Task task : items) {
				writer.write(task.getName() + ";" + task.isCompleted());
				writer.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	void loadTasksFromFile() {
		if (Files.exists(Paths.get(filePath))) {
			try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
				String line;
				while ((line = reader.readLine()) != null) {
					String[] parts = line.split(";");
					if (parts.length == 2) {
						String name = parts[0];
						boolean completed = Boolean.parseBoolean(parts[1]);
						Task task = new Task(name);
						task.setCompleted(completed);
						items.add(task);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
