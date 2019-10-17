/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hazard.Helpers;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author kmoothandas
 */
public class ViewLoaders {

    public void LoadController(String url, AnchorPane parent) {
        try {
            FXMLLoader loader;
            loader = new FXMLLoader(getClass().getResource(url));
            AnchorPane pane = loader.load();
            //pane.set
            parent.getChildren().clear();
            parent.getChildren().add(pane);
            AnchorPane.setRightAnchor(pane, 5.0);
            AnchorPane.setLeftAnchor(pane, 5.0);
            AnchorPane.setTopAnchor(pane, 0.0);
            AnchorPane.setBottomAnchor(pane, 15.0);

        } catch (IOException | NullPointerException ex) {
            System.err.println(ex);
        }
    }

}
