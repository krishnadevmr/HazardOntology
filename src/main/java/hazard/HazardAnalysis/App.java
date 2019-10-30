package hazard.HazardAnalysis;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * @author chjunchi
 *
 */
public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane root = FXMLLoader.load(getClass().getResource("/fxml/MainPage.fxml"));

        primaryStage.setTitle("AN ONTOLOGICAL APPROACH TO SAFETY ANALYSIS OF SAFETY-CRITICAL SYSTEMS");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        //primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                Platform.exit();
                System.exit(0);
            }
        });
        /*
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX(primaryScreenBounds.getMinX());
        primaryStage.setY(primaryScreenBounds.getMinY());
        primaryStage.setWidth(1366);
        primaryStage.setHeight(765);
        primaryStage.setTitle("AN ONTOLOGICAL APPROACH TO SAFETY ANALYSIS OF SAFETY-CRITICAL SYSTEMS");
        BorderPane border = new BorderPane();
        MainView av = new MainView();
        border = av.view(primaryStage);
        Scene scene = new Scene(border);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                Platform.exit();
                System.exit(0);
            }
        }); */
    }
}
