/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hazard.Controllers.Views;

import hazard.Controllers.MainPageController;
import hazard.Controllers.MainPageController;
import hazard.Controllers.NavigationController;
import hazard.Controllers.NavigationController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author kmoothandas
 */
public class SDF2Controller implements Initializable, NavigationController {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    //@Override
    public AnchorPane LoadPane() {
        AnchorPane pane = new AnchorPane();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SDF2.fxml"));
            pane = loader.load();
        } catch (IOException ex) {
        }
        return pane;
    }

    //@Override
    public AnchorPane LoadPane(MainPageController controller, NavigationController secondaryController) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
