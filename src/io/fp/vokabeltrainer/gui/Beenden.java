package io.fp.vokabeltrainer.gui;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.Scene;

public class Beenden extends Application{

	private Label label;
	private Button ja;
	private Button nein;
	private Button abbrechen;
	private Parent root;
	
	@Override
	public void init() throws Exception {
		label = new Label("Wollen sie wirlich beenden?");
		ja = new Button("Ja");
		nein = new Button("Nein");
		abbrechen = new Button("Abbrechen");
		root = greatSceneGraph();
	}
	public Parent greatSceneGraph() {
		VBox box = new VBox();
		HBox box2 = new HBox();
		box2.getChildren().addAll(ja,nein,abbrechen);
		box.getChildren().addAll(label, box2);
		return box;
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Beenden");
		Scene scene = new Scene(root, 300, 300);
		stage.setScene(scene);
		stage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
