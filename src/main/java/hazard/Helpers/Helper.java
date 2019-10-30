/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hazard.Helpers;

import hazard.HazardAnalysis.DataBase.DataBaseConnection;
import hazard.HazardClasses.Hazard2;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

/**
 *
 * @author kmoothandas
 */
public class Helper {

    public static void FilterHazardByCategory(ObservableList<Hazard2> hazardList, ObservableList<Hazard2> subHazardList, Integer category) {
        subHazardList.clear();
        hazardList.forEach((Hazard2 h) -> {
            if (h.getCategoryId() == category && h.getIsExpanded() == 1) {
                subHazardList.add(h);
            }
        });
    }

    public static void PopulateHazardTable(ObservableList<Hazard2> hazardList, TableView<Hazard2> hazardTable) {
        DataBaseConnection.selectAll("hazard2", hazardList);
        hazardTable.setItems(hazardList);
    }

}
