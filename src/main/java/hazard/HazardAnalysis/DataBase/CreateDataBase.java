package hazard.HazardAnalysis.DataBase;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author chjunchi
 *
 */
public class CreateDataBase {

    private static String database;

    public static void createNewDatabase() {
        String url = "jdbc:sqlite:" + database;
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createNewTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:" + database;
        String sql1 = "CREATE TABLE IF NOT EXISTS kind (id integer PRIMARY KEY, kind text NOT NULL, start bit NOT NULL, runtime bit NOT NULL, shutdown bit NOT NULL,unique(kind));";
        String sql2 = "CREATE TABLE IF NOT EXISTS role (id integer PRIMARY KEY, role text NOT NULL, start bit NOT NULL, runtime bit NOT NULL, shutdown bit NOT NULL,unique(role));";
        String sql3 = "CREATE TABLE IF NOT EXISTS roletoplay (id integer PRIMARY KEY,kind text NOT NULL, kindid integer NOT NULL, role text NOT NULL, roleid integer NOT NULL);";
        String sql4 = "CREATE TABLE IF NOT EXISTS relator (id integer PRIMARY KEY,relator text NOT NULL,unique(relator));";
        String sql5 = "CREATE TABLE IF NOT EXISTS relatortorole (id integer PRIMARY KEY, relator text NOT NULL, relatorid integer NOT NULL, role TEXT NOT NULL, roleid INTEGER NOT NULL);";
        String sql6 = "CREATE TABLE IF NOT EXISTS hazard (id INTEGER PRIMARY KEY, hazard TEXT NOT NULL, harm TEXT NOT NULL);";
        String sql7 = "CREATE TABLE IF NOT EXISTS cause (id INTEGER PRIMARY KEY, cause TEXT NOT NULL, mitigation TEXT, hazardid INTEGER NOT NULL, severity REAL, probability REAL, riskevaluation REAL, risk BIT, postseverity REAL, postprobability REAL, postriskevaluation REAL, postrisk BIT);";
        String sql8 = "CREATE TABLE IF NOT EXISTS mishapvictim (id INTEGER PRIMARY KEY, kind TEXT NOT NULL,role TEXT NOT NULL,relator TEXT NOT NULL, kindid INTEGER NOT NULL,roleid INTEGER NOT NULL,relatorid INTEGER NOT NULL);";
        String sql9 = "CREATE TABLE IF NOT EXISTS mishapvictim2 (id INTEGER PRIMARY KEY, role TEXT NOT NULL, roleid INTEGER NOT NULL , possibleharm TEXT);";
        String sql10 = "CREATE TABLE IF NOT EXISTS hazard2 (id INTEGER PRIMARY KEY, mishapvictim TEXT NOT NULL, victimroleid INTEGER NOT NULL, exposure TEXT NOT NULL, exposurerelatorid INTEGER NOT NULL, hazardelement TEXT NOT NULL, hazardroleid INTEGER NOT NULL, harmtruthmaker TEXT NOT NULL, hazarddescription TEXT NOT NULL, victimenvobject TEXT,hazardenvobject TEXT, categoryid INTEGER, category TEXT, isExpanded INTEGER) ";
        String sql11 = "CREATE TABLE IF NOT EXISTS hazardcategory (categoryid INTEGER PRIMARY KEY, category TEXT NOT NULL)";
        String sql12 = "CREATE TABLE hazardexpansion (id INTEGER PRIMARY KEY,\n"
                + "hazardid INTEGER NOT NULL, \n"
                + "rootkindid INTEGER NOT NULL ,\n"
                + "rootkind TEXT NOT NULL,\n"
                + "rootroleid INTEGER NOT NULL,\n"
                + "rootrole TEXT NOT NULL,\n"
                + "relatorid INTEGER ,\n"
                + "relator TEXT,\n"
                + "linkedroleid INTEGER,\n"
                + "linkedrole TEXT,\n"
                + "linkedkindid INTEGER,\n"
                + "linkedkind TEXT,\n"
                + "CONSTRAINT fk_kind\n"
                + "	FOREIGN KEY (rootkindid)\n"
                + "	REFERENCES kind(id),\n"
                + "	FOREIGN KEY (linkedkindid)\n"
                + "	REFERENCES kind(id),\n"
                + "CONSTRAINT fk_role\n"
                + "	FOREIGN KEY (rootroleid)\n"
                + "	REFERENCES role(id),\n"
                + "	FOREIGN KEY (linkedroleid)\n"
                + "	REFERENCES role(id),\n"
                + "CONSTRAINT fk_relator\n"
                + "	FOREIGN KEY (relatorid)\n"
                + "	REFERENCES relator(id),\n"
                + "CONSTRAINT uq_edelements\n"
                + "	UNIQUE (hazardid, rootkindid, rootroleid, relatorid, linkedroleid, linkedkindid)\n"
                + ")";
        try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql1);
            stmt.execute(sql2);
            stmt.execute(sql3);
            stmt.execute(sql4);
            stmt.execute(sql5);
            stmt.execute(sql6);
            stmt.execute(sql7);
            stmt.execute(sql8);
            stmt.execute(sql9);
            stmt.execute(sql10);
            stmt.execute(sql11);
            stmt.execute(sql12);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static String getDatabase() {
        return database;
    }

    public static void setDatabase(String database) {
        CreateDataBase.database = database;
    }
}
