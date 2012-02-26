package updater;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Muhammad
 */
public class DatabaseUpdater {

    private String server, port, database, username, password;
    private static String driver = "org.postgresql.Driver";
    private static String provider = "postgresql";
    private static String mainDb = "postgres";
    private ProgressListener pListener;
    private Connection masterConnection, dbConnection;

    public DatabaseUpdater(
            String server, String port, String database,
            String username, String password, ProgressListener pl) {
        this.server = server;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
        this.pListener = pl;
    }

    public final Connection getConnection(String dbName) throws SQLException, ClassNotFoundException {

        Class.forName(driver);

        String url = "jdbc:" + provider + "://" + server + ":" + port + "/" + dbName;
        Connection con = null;
        con = DriverManager.getConnection(url, username, password);
        con.setAutoCommit(true);
        return con;
    }

//    public int ExecuteUpdate(String statement) throws SQLException, ClassNotFoundException {
//        //Connection con = GetConnection(database);
//        return ExecuteUpdate(dbConnection, statement);
//    }
    public int executeUpdate(Connection connection, String statement) throws SQLException {
        Statement stm = connection.createStatement();
        pListener.executingQuery(statement);
        int r = stm.executeUpdate(statement);
        stm.close();
        return r;
    }

    public int executeUpdate(Connection connection, String statement, Object... params) throws SQLException {
        pListener.executingQuery(statement, params);
        PreparedStatement p = connection.prepareStatement(statement);
        for (int i = 0; i < params.length; i++) {
            p.setObject(i + 1, params[i]);
        }
        int r = p.executeUpdate();
        return r;
    }

    public ResultSet executeQuery(Connection connection, String statement) throws SQLException {
        Statement stm = connection.createStatement();
        pListener.executingQuery(statement);
        ResultSet r = stm.executeQuery(statement);
        return r;
    }

    public ResultSet executeQuery(Connection connection, String statement, Object... params) throws SQLException {
        pListener.executingQuery(statement, params);
        PreparedStatement p = connection.prepareStatement(statement);
        for (int i = 0; i < params.length; i++) {
            p.setObject(i + 1, params[i]);
        }
        ResultSet r = p.executeQuery();
        return r;
    }

    public Object executeScalar(Connection connection, String statement) throws SQLException {
        return executeScalar(connection, statement, new Object[0]);
    }

    public Object executeScalar(Connection connection, String statement, Object... params) throws SQLException {
        ResultSet r = executeQuery(connection, statement, params);
        try {
            if (r.next()) {
                return r.getObject(1);
            }
            return null;
        } finally {
            r.close();
        }
    }

    private boolean databaseExists(String dbName) throws SQLException {
        return (Boolean) executeScalar(masterConnection,
                "select exists(select * from pg_catalog.pg_database where datname=?)",
                dbName);
    }

    private boolean tableExists(String tableName) throws SQLException {
        return (Boolean) executeScalar(dbConnection,
                "select exists(select * from information_schema.tables where table_name=?)",
                tableName.toLowerCase());
    }

    private void checkAndCreateTable(String t) throws IOException, SQLException {
        if (tableExists(t)) {
            logInfo("Table '" + t + "' exists.");
        } else {
            logInfo("Creating table '" + t + "'");
            executeResourceFile(dbConnection, "scripts/tbl" + t + ".sql");
        }
    }

    private boolean columnExists(String tableName, String columnName) throws SQLException {
        return (Boolean) executeScalar(dbConnection,
                "select exists(select * from information_schema.columns where table_name=? and column_name=?)",
                tableName, columnName);
    }

    private void createDatabase(String dbName) throws FileNotFoundException, SQLException, IOException {
        InputStream stream = this.getClass().getResourceAsStream("scripts/CreateDb.sql");
        executeUpdate(masterConnection, IOUtils.toString(stream).replace("?", dbName));
    }

    private void executeResourceFile(Connection connection, String path) throws IOException, SQLException {
        InputStream stream = this.getClass().getResourceAsStream(path);
        executeUpdate(connection, IOUtils.toString(stream));
    }

    private String getSequenceNameForTable(String tableName) {
        return tableName + "_" + tableName + "_id_seq";
    }

    private long getLastId(String tableName) throws SQLException {
        return (Long) executeScalar(dbConnection,
                "SELECT last_value FROM " + getSequenceNameForTable(tableName));
    }

    private void setLastId(String tableName, int id) throws SQLException {
        executeQuery(dbConnection,
                "SELECT setval('" + getSequenceNameForTable(tableName) + "'," + id + ")");
    }

    private boolean sqlExists(Connection connection, String query, Object... params) throws SQLException {
        return (Boolean) executeScalar(connection, "SELECT EXISTS(" + query + ")", params);
    }

    private boolean foreignKeyExists(String fkName) throws SQLException {
        String query = "SELECT * FROM pg_catalog.pg_constraint WHERE conname=?";
        return sqlExists(dbConnection, query, fkName);
    }

    public boolean Update() {
        try {
            masterConnection = getConnection(mainDb);

            if (!databaseExists(database)) {
                logInfo("Creating database");
                createDatabase(database);
            } else {
                logInfo("Database exists.");
            }
            dbConnection = getConnection(database);

            String[] tables = new String[]{
                "SUser",
                "Role",
                "SUser_Role",
                "University",
                "EducationGroup",
                "EducationField",
                "Course",
                "Topic",
                "Grade",
                "SanjeshAgent",
                "UniversityAgent",
                "Designer",
                "Question",
            };

            for (String t : tables) {
                checkAndCreateTable(t);
            }

            if (!columnExists("educationgroup", "code")) {
                pListener.logEvent(LogCategory.INFO, "Adding column 'code' to table 'educationgroup'");
                executeUpdate(dbConnection, "ALTER TABLE educationgroup ADD COLUMN code character varying(50)");
                ResultSet r = executeQuery(dbConnection, "SELECT educationgroup_id FROM educationgroup");
                ArrayList<Integer> ids = new ArrayList<Integer>();
                while (r.next()) {
                    ids.add((Integer) r.getObject(1));
                }
                r.close();
                int i = 1;
                for (Integer id : ids) {
                    executeUpdate(dbConnection, "UPDATE educationgroup SET code=? WHERE educationgroup_id=?", i++, id);
                }
                executeUpdate(dbConnection, "ALTER TABLE educationgroup ALTER COLUMN code SET NOT NULL");
            }

            if (!columnExists("sanjeshagent", "suser_ref")) {
                if (sqlExists(dbConnection, "SELECT * FROM sanjeshagent")) {
                    if (JOptionPane.showConfirmDialog(null,
                            "All of the sanjesh agents you have already defined will be deleted. Press OK to continue.",
                            "Database Updater", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {

                        executeUpdate(dbConnection, "DELETE FROM sanjeshagent");
                        executeUpdate(dbConnection, "ALTER TABLE sanjeshagent ADD COLUMN suser_ref integer NOT NULL");
                    }
                    else{
                        return false;
                    }
                }
            }

            if (!foreignKeyExists("fkey_sanjeshagent_suser_ref")) {
                executeUpdate(dbConnection, "ALTER TABLE sanjeshagent \n"
                        + "ADD CONSTRAINT fkey_sanjeshagent_suser_ref FOREIGN KEY (suser_ref) \n"
                        + "REFERENCES suser (suser_id) MATCH SIMPLE \n"
                        + "ON UPDATE NO ACTION ON DELETE NO ACTION");
            }

            if (!columnExists("universityagent", "suser_ref")) {
                if (sqlExists(dbConnection, "SELECT * FROM universityagent")) {
                    if (JOptionPane.showConfirmDialog(null,
                            "All of the university agents you have already defined will be deleted. Press OK to continue.",
                            "Database Updater", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {

                        executeUpdate(dbConnection, "DELETE FROM universityagent");
                        executeUpdate(dbConnection, "ALTER TABLE universityagent ADD COLUMN suser_ref integer NOT NULL");
                    } else {
                        return false;
                    }
                }
            }

            if (!foreignKeyExists("fkey_universityagent_suser_ref")) {
                executeUpdate(dbConnection, "ALTER TABLE universityagent \n"
                        + "ADD CONSTRAINT fkey_universityagent_suser_ref FOREIGN KEY (suser_ref) \n"
                        + "REFERENCES suser (suser_id) MATCH SIMPLE \n"
                        + "ON UPDATE NO ACTION ON DELETE NO ACTION");
            }
            
            if(!columnExists("sanjeshagent", "isdesignerexpert")){
                executeUpdate(dbConnection, "ALTER TABLE sanjeshagent ADD COLUMN isdesignerexpert boolean");
            }
            
            if(!columnExists("sanjeshagent", "isdataexpert")){
                executeUpdate(dbConnection, "ALTER TABLE sanjeshagent ADD COLUMN isdataexpert boolean");
            }

            // Static data
            AddAdminUserIfNotExists();
            AddDefaultRoles();

            logInfo("Done.");

        } catch (Throwable ex) {
            //logError(ex.toString());
            logError(Utils.getStackTrace(ex));
            return false;
        }

        return true;
    }

    private void AddDefaultRoles() throws SQLException {

        String role = "نماینده سازمان سنجش";
        AddRoleIfNotExists(1, role);
        role = "نماینده دانشگاه";
        AddRoleIfNotExists(2, role);
        role = "طراح سؤال";
        AddRoleIfNotExists(3, role);

        if (getLastId("role") < 3) {
            setLastId("role", 3);
        }
    }

    private void AddRoleIfNotExists(int id, String name) throws SQLException {
        if (!sqlExists(dbConnection, "SELECT * FROM role WHERE role_id=?", id)) {
            logInfo("Adding default role : " + name);
            executeUpdate(dbConnection, "INSERT INTO role (role_id, name, fixed) VALUES (?,?,true)", id, name);
        }
    }
    
    public void AddAdminUserIfNotExists() throws SQLException{
        if(!sqlExists(dbConnection, "SELECT * FROM suser WHERE suser_id=1")){
            logInfo("Adding admin user.");
            executeUpdate(dbConnection,
                    "INSERT INTO suser (suser_id, username, password, fullname) VALUES(1, 'admin', 'admin', 'سرپرست')");
            if(getLastId("suser") < 1){
                setLastId("suser", 1);
            }
        }
    }

    private void logInfo(String message) {
        pListener.logEvent(LogCategory.INFO, message);
    }

    private void logError(String message) {
        pListener.logEvent(LogCategory.ERROR, message);
    }
}
