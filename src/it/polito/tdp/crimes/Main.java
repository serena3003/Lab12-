package it.polito.tdp.crimes;
	
import it.polito.tdp.model.Model;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			System.out.println("DENTRO");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Crimes.fxml"));
			BorderPane root = loader.load();
			System.out.println("yku");
			CrimesController controller = loader.getController();
			System.out.println("fvd");
			Model model = new Model();
			System.out.println("123");
			controller.setModel(model);
			System.out.println("0");
			Scene scene = new Scene(root);
			System.out.println("1");
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			System.out.println("2");
			primaryStage.show();
			System.out.println("FUORI");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
