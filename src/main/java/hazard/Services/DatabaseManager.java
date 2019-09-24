/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hazard.Services;

import hazard.HazardAnalysis.DataBase.CreateDataBase;
import hazard.HazardAnalysis.DataBase.DataBaseConnection;
import hazard.HazardClasses.Play;
import hazard.Helpers.UIHelper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.time.LocalDate;
import java.util.Optional;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author kmoothandas
 */
public class DatabaseManager {

    public static void NewDatabaseSession(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("New database");
        fileChooser.setInitialFileName(".db");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("DB", "*.db"));
        File file = fileChooser.showSaveDialog(stage);
        if (file != null && file.getName().contains(".db")) {
            DataBaseConnection.setDatabase(file.getPath());
            CreateDataBase.setDatabase(file.getPath());
            CreateDataBase.createNewDatabase();
            CreateDataBase.createNewTable();
        }
    }

    public static void LoadDatabaseSession(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load database");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("DB", "*.db"));
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            DataBaseConnection.setDatabase(file.getPath());
            try {
                Files.copy(Paths.get(file.getPath()),
                        Paths.get(file.getPath().replace(".db", "-" + LocalDate.now() + "-backup.db")),
                        REPLACE_EXISTING);
            } catch (IOException e1) {
               System.err.println(e1);
            }
        }
    }

    public static <T> void AddKindorRole(TableView<T> tb, ObservableList<T> list, String type) {
        
        Optional<String> newEntry = UIHelper.CreateAddDialog(type);
        
        if (newEntry.isPresent() && !newEntry.get().isEmpty() && newEntry.get().trim().length()>0) {
            DataBaseConnection.insertRoleOrKind(type.toLowerCase(), newEntry.get(), false, false, false);
            list.clear();
            DataBaseConnection.selectAll(type.toLowerCase(), list);
        }
        //newRole.ifPresent(role -> System.out.println("Your name: " + role));
    }

    public static <T> void RemoveKindOrRole(TableView<T> tb, String type) {
        if (!tb.getItems().isEmpty()) {
            int index = tb.getSelectionModel().selectedIndexProperty().get();
            if (index != -1) {
                Play o = (Play) tb.getItems().remove(index);
                DataBaseConnection.delete(type, o.getId());
            }
        }
    }

}
