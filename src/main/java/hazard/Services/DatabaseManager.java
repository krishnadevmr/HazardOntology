/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hazard.Services;

import hazard.HazardAnalysis.DataBase.CreateDataBase;
import hazard.HazardAnalysis.DataBase.DataBaseConnection;
import hazard.HazardAnalysis.DataBase.ExportDataToExcel;
import hazard.HazardClasses.Hazard2;
import hazard.HazardClasses.HazardCategory;
import hazard.HazardClasses.HazardExpansion;
import hazard.HazardClasses.Kind;
import hazard.HazardClasses.Play;
import hazard.HazardClasses.PossibleHazardRelator;
import hazard.HazardClasses.Relator;
import hazard.HazardClasses.Role;
import hazard.Helpers.UIHelper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ProgressIndicator;
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
            InsertReferenceTables();
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

        if (newEntry.isPresent() && !newEntry.get().isEmpty() && newEntry.get().trim().length() > 0) {
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

    public static void AddRelator(TableView<Relator> tb, ObservableList<Relator> list, String type) {

        Optional<String> newEntry = UIHelper.CreateAddDialog(type);

        if (newEntry.isPresent() && !newEntry.get().isEmpty() && newEntry.get().trim().length() > 0) {
            //DataBaseConnection.insertRoleOrKind(type.toLowerCase(), newEntry.get(), false, false, false);
            DataBaseConnection.insertRelator(type.toLowerCase(), newEntry.get());
            list.clear();
            DataBaseConnection.selectAll(type.toLowerCase(), list);
        }
        //newRole.ifPresent(role -> System.out.println("Your name: " + role));
    }

    public static void ExportToExcel(Stage stage, ProgressIndicator p1) {
        p1.setProgress(-1D);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("New Excel");
        fileChooser.setInitialFileName(".xlsx");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("xlsx", "*.xlsx"));
        File file = fileChooser.showSaveDialog(stage);
        Thread t = new ExportDataToExcel(file, p1);
        t.start();
    }

    public static ObservableList<PossibleHazardRelator> GetPossibleHazardRelators(String sql, ObservableList<PossibleHazardRelator> list) {
        ResultSet rs = DataBaseConnection.sql(sql);
        try {
            while (rs.next()) {
                list.add(new PossibleHazardRelator(rs.getInt("relatorid"), rs.getString("relator"), rs.getInt("roleid"), rs.getString("role")));
            }
        } catch (SQLException | NullPointerException e) {
            System.err.println(e);
        }
        return list;
    }

    /**
     * Method insert a new hazard2 into the database
     *
     * @param hazard new Hazard2 that we want to add to database
     */
    public static void InsertHazard(Hazard2 hazard) {

        String victimKindSql = "select kind, kindid from roletoplay r where roleid =" + hazard.getVictimId();
        String hazardElementKindSql = "select kind, kindid from roletoplay r where roleid =" + hazard.getHazardElementId();

        //ObservableList<String> allKinds = FXCollections.observableArrayList();
        //ObservableList<Kind> victimKinds = getAllKindForRole(victimKindSql);
        //String victimKindString = ConcatenateStrings(victimKinds);
        //ObservableList<Kind> hazardKinds = getAllKindForRole(victimKindSql);
        //String hazardKindString = ConcatenateStrings(hazardKinds);
        ObservableList<ObservableList<Kind>> kindList = FXCollections.observableArrayList();

        kindList = GetHazardKinds(hazard, kindList);
        String victimKindString = ConcatenateStrings(kindList.get(0));
        String hazardKindString = ConcatenateStrings(kindList.get(1));

        StringBuilder strBuild = new StringBuilder();
        strBuild.append("INSERT INTO hazard2 (");
        strBuild.append("mishapvictim, victimroleid, exposure, exposurerelatorid, hazardelement, "
                + "hazardroleid, harmtruthmaker, hazarddescription, victimenvobject, hazardenvobject) ");
        strBuild.append("VALUES('");
        strBuild.append(hazard.getMishapVictim());
        strBuild.append("',");
        strBuild.append(hazard.getVictimId());
        strBuild.append(",'");
        strBuild.append(hazard.getExposure());
        strBuild.append("',");
        strBuild.append(hazard.getExposureId());
        strBuild.append(",'");
        strBuild.append(hazard.getHazardElement());
        strBuild.append("',");
        strBuild.append(hazard.getHazardElementId());
        strBuild.append(",'");
        strBuild.append(hazard.getTruthmaker());
        strBuild.append("','");
        strBuild.append(hazard.getHazardDescription());
        strBuild.append("','");
        strBuild.append(victimKindString);
        strBuild.append("','");
        strBuild.append(hazardKindString);
        strBuild.append("')");

        String sql = strBuild.toString();
        //System.out.println(sql);
        DataBaseConnection.insert(sql);
    }

    public static ObservableList<Kind> getAllKindForRole(String sql) {
        //list = DataBaseConnection.getKindListFromRole(sql, list);
        ObservableList<Kind> Kindlist = FXCollections.observableArrayList();
        Kindlist = DataBaseConnection.getKindListFromRole(sql, Kindlist);

        return Kindlist;
    }

    public static String ConcatenateStrings(ObservableList<Kind> Kindlist) {
        ObservableList<String> list = FXCollections.observableArrayList();

        Kindlist.forEach((kind) -> list.add(kind.getKind()));

        String kinds = "";

        int count = 0;
        for (String str : list) {
            if (count != 0) {
                kinds += ", ";
            }
            kinds = kinds + str;
            count++;
        }
        return kinds;
    }

    public static ObservableList<ObservableList<Kind>> GetHazardKinds(Hazard2 hazard, ObservableList<ObservableList<Kind>> kindList) {
        String victimKindSql = "select kind, kindid from roletoplay r where roleid =" + hazard.getVictimId();
        String hazardElementKindSql = "select kind, kindid from roletoplay r where roleid =" + hazard.getHazardElementId();

        ObservableList<Kind> victimKinds = getAllKindForRole(victimKindSql);
        ObservableList<Kind> hazardKinds = getAllKindForRole(hazardElementKindSql);

        kindList.add(victimKinds);
        kindList.add(hazardKinds);

        return kindList;
    }

    public static ObservableList<HazardCategory> GetHazardCategories(ObservableList<HazardCategory> list) {
        String sql = "select categoryid, category from hazardcategory ";
        ResultSet rs = DataBaseConnection.sql(sql);
        try {
            while (rs.next()) {
                list.add(new HazardCategory(rs.getInt("categoryid"), rs.getString("category")));
            }
        } catch (SQLException | NullPointerException e) {
            System.err.println(e);
        }
        return list;
    }

    public static void UpdateHazardCategory(int categoryId, int hazardId) {
        String sql = MessageFormat.format("Update hazard2 set categoryid = {0}, category = "
                + "(select category from hazardcategory where categoryid = {0}) where id = {1}", categoryId, hazardId);
        DataBaseConnection.sqlUpdate(sql);
    }

    public static void InsertReferenceTables() {
        String sql = "insert into hazardcategory values (1,'Hazard'),(2,'Initiating Condition'),(3,'Initiating Event'),(4,'Mishap')";
        DataBaseConnection.insert(sql);
    }

    public static ObservableList<Hazard2> GetHazardByCategory(int hazardCategory, ObservableList<Hazard2> list) {
        //String categoryIds;
        //categoryIds = isHazardorIc ? "1, 2" : "3, 4";

        String sql = MessageFormat.format("Select * from hazard2 where categoryid in( {0})", hazardCategory);

        DataBaseConnection.sql(sql, "hazard2", list);

        return list;
    }

    public static ObservableList<Relator> GetRelatorsFromRole(Integer roleId, ObservableList<Relator> list) {
        String sql = "select * from relatortorole where roleid = " + roleId;
        DataBaseConnection.sql(sql, "relatortorole", list);
        return list;
    }

    public static ObservableList<Role> GetRoleFromRelator(Integer relatorId, ObservableList<Role> list) {
        String sql = "select * from relatortorole where relatorid = " + relatorId;
        DataBaseConnection.sql(sql, "relatortorole", list);
        return list;
    }

    public static <T> ObservableList<T> GetRelatorsOrRole(Integer Id, String source, String tableString, ObservableList<T> list) {
        //String sql = "select * from relatortorole where roleid = " + roleId;
        String sql = MessageFormat.format("select * from relatortorole where {0} = {1}", source, Id);
        DataBaseConnection.sql(sql, tableString, list);
        return list;
    }
    
    public static void InsertIntoHazardExpansion(HazardExpansion expansion){
        DataBaseConnection.insertHazardExpansion(expansion.getHazardId(),
                expansion.getRootKind().getId(), expansion.getRootKind().getKind(),
                expansion.getRootRole().getId(), expansion.getRootRole().getRole(),
                expansion.getRelator().getId(), expansion.getRelator().getRelator(),
                expansion.getLinkedRole().getId(), expansion.getLinkedRole().getRole(),
                expansion.getLinkedKind().getId(), expansion.getLinkedKind().getKind());
    }
}
