/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hazard.Helpers;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author kmoothandas
 */
public class UIHelper {

    public static void getAllButtonsToggle(AnchorPane pane) {
        ObservableList<Node> it2 = pane.getChildrenUnmodifiable();
        it2.forEach((node) -> {
            if (node instanceof ToggleButton) {
                setButtonToggles((ToggleButton) node);
            }
        });
    }

    private static void setButtonToggles(ToggleButton button) {
        button.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue) {
                button.setStyle(
                        "-fx-background-color: red;"
                        + "-fx-text-fill: white");
            } else {
                button.setStyle("-fx-background-color: transparent;");
            }
        }));

    }
}
