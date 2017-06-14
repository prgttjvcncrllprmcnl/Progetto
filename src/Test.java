import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
/**
 * Created by Daniel on 14/05/2017.
 */
public class Test extends Application {
    static Stage finestra = new Stage();
    static Pane secondaryPane = new Pane();

    public static void main(String[] args) {
        launch(args);
    }                  // termina. Può essere invocato una sola volta

    @Override
    public void start(Stage primaryStage) throws Exception {
        finestra = primaryStage;
        Pane mainPane = FXMLLoader.load(Test.class.getResource("Input.fxml"));
        secondaryPane = FXMLLoader.load(Test.class.getResource("Aggiunta.fxml"));
        primaryStage.setTitle("");
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.show();
    }
}