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
    public int executeUpdate(Connection connection, String statement) throws SQLException, ClassNotFoundException {
        Statement stm = connection.createStatement();
        pListener.executingQuery(statement);
        int r = stm.executeUpdate(statement);
        stm.close();
        return r;
    }
    
    public int executeUpdate(Connection connection, String statement, Object... params) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
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

    private boolean columnExists(String tableName, String columnName) throws SQLException {
        return (Boolean) executeScalar(dbConnection,
                "select exists(select * from information_schema.columns where table_name=? and column_name=?)",
                tableName, columnName);
    }

    private void createDatabase(String dbName) throws FileNotFoundException, SQLException, ClassNotFoundException, IOException {
        InputStream stream = this.getClass().getResourceAsStream("scripts/CreateDb.sql");
        executeUpdate(masterConnection, IOUtils.toString(stream).replace("?", dbName));
    }

    private void executeResourceFile(Connection connection, String path) throws IOException, SQLException, ClassNotFoundException {
        InputStream stream = this.getClass().getResourceAsStream(path);
        executeUpdate(connection, IOUtils.toString(stream));
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
                "SUser"
            };

            for (String t : tables) {
                if (tableExists(t)) {
                    logInfo("Table '" + t + "' exists.");
                } else {
                    logInfo("Creating table '" + t + "'");
                    executeResourceFile(dbConnection, "scripts/tbl" + t + ".sql");
                }
            }
            
            if(!columnExists("educationgroup", "code")){
                pListener.logEvent(LogCategory.INFO, "Adding column 'code' to table 'educationgroup'");
                executeUpdate(dbConnection, "ALTER TABLE educationgroup ADD COLUMN code character varying(50)");
                ResultSet r = executeQuery(dbConnection, "SELECT educationgroup_id FROM educationgroup");
                ArrayList<Integer> ids = new ArrayList<Integer>();
                while( r.next()){
                    ids.add((Integer)r.getObject(1));
                }
                r.close();
                int i = 1;
                for( Integer id : ids){
                    executeUpdate(dbConnection, "UPDATE educationgroup SET code=? WHERE educationgroup_id=?", i++, id);
                }
                executeUpdate(dbConnection, "ALTER TABLE educationgroup ALTER COLUMN code SET NOT NULL");
            }
            
            
            logInfo("Done.");

        } catch (Throwable ex) {
            //logError(ex.toString());
            logError(Utils.getStackTrace(ex));
            return false;
        }


        return true;
    }

    private void logInfo(String message) {
        pListener.logEvent(LogCategory.INFO, message);
    }

    private void logError(String message) {
        pListener.logEvent(LogCategory.ERROR, message);
    }
}
