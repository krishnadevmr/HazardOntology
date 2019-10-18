/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hazard.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author kmoothandas
 */
public class InitializableWithLoad implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void LoadController(Initializable controller, String url, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(url));
            loader.setController(controller);
            AnchorPane pane = loader.load();

            Stage stage = new Stage();
            //stage.initModality(Modality.APPLICATION_MODAL);
            //stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(pane));
            stage.show();
        } catch (IOException | NullPointerException ex) {
            System.err.println(ex);
        }
    }
    
        public void LoadController(String url, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(url));
            AnchorPane pane = loader.load();

            Stage stage = new Stage();
            //stage.initModality(Modality.APPLICATION_MODAL);
            //stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(pane));
            stage.show();
        } catch (IOException | NullPointerException ex) {
            System.err.println(ex);
        }
    }

}
