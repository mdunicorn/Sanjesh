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
import java.util.Date;
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
    private int defaultSqlDateType = java.sql.Types.TIMESTAMP;
    private static final String charVar255 = "character varying(255)";
    private static final String charVar500 = "character varying(500)";
    private static final String charVar4000 = "character varying(4000)";

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
            if( params[i] instanceof Date){
                p.setObject(i+1, params[i], defaultSqlDateType);
            }
            else{
                p.setObject(i + 1, params[i]);
            }
        }
        int r = p.executeUpdate();
        p.close();
        return r;
    }

    public ResultSetWrapper executeQuery(Connection connection, String statement) throws SQLException {
        Statement stm = connection.createStatement();
        pListener.executingQuery(statement);
        ResultSet r = stm.executeQuery(statement);
        return new ResultSetWrapper(stm, r);
    }

    public ResultSetWrapper executeQuery(Connection connection, String statement, Object... params) throws SQLException {
        pListener.executingQuery(statement, params);
        PreparedStatement p = connection.prepareStatement(statement);
        for (int i = 0; i < params.length; i++) {
            p.setObject(i + 1, params[i]);
        }
        ResultSet r = p.executeQuery();
        return new ResultSetWrapper(p, r);
    }

    public Object executeScalar(Connection connection, String statement) throws SQLException {
        return executeScalar(connection, statement, new Object[0]);
    }

    public Object executeScalar(Connection connection, String statement, Object... params) throws SQLException {
        ResultSetWrapper rw = executeQuery(connection, statement, params);
        ResultSet r = rw.getResultSet();
        try {
            if (r.next()) {
                return r.getObject(1);
            }
            return null;
        } finally {
            rw.close();
        }
    }

    private boolean databaseExists(String dbName) throws SQLException {
        return (Boolean) executeScalar(masterConnection,
                "select exists(select * from pg_catalog.pg_database where datname=?)",
                dbName);
    }

    private void createDatabase(String dbName) throws FileNotFoundException, SQLException, IOException {
        InputStream stream = this.getClass().getResourceAsStream("scripts/CreateDb.sql");
        executeUpdate(masterConnection, IOUtils.toString(stream).replace("?", dbName));
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
                tableName.toLowerCase(), columnName.toLowerCase());
    }
    
    private boolean columnExists(String tableName, String columnName, String dataType) throws SQLException {
        return (Boolean) executeScalar(dbConnection,
                "select exists(select * from information_schema.columns where table_name=? and column_name=? and data_type=?)",
                tableName.toLowerCase(), columnName.toLowerCase(), dataType.toLowerCase());
    }
    
    private boolean charVarColumnExists(String tableName, String columnName, int maxLen) throws SQLException {
        return (Boolean) executeScalar(dbConnection,
                "select exists(select * from information_schema.columns where table_name=? and column_name=? and data_type='character varying' and character_maximum_length=?)",
                tableName.toLowerCase(), columnName.toLowerCase(), maxLen);
    }
    
    private int getColumnCharacterMaxLen(String tableName, String columnName) throws SQLException {
       return (Integer)executeScalar(dbConnection,
               "select character_maximum_length from information_schema.columns where table_name=? and column_name=?",
               tableName.toLowerCase(), columnName.toLowerCase());
    }

    private void createColumn(String tableName, String columnName, String type) throws SQLException{
        createColumn(tableName, columnName, type, true, null);
    }
    
    private void createColumn(String tableName, String columnName, String type,
            boolean nullable, String defaultExpression) throws SQLException {
        logInfo("Adding column '" + columnName + "' to table '" + tableName + "'.");
        String stmt = "ALTER TABLE " + tableName + " ADD COLUMN " + columnName + " " + type;
        if (!nullable) {
            stmt += " NOT NULL";
        }
        if (defaultExpression != null) {
            stmt += " DEFAULT " + defaultExpression;
        }
        executeUpdate(dbConnection, stmt);
    }

    private void createColumnIfNotExists(String tableName, String columnName, String type) throws SQLException{
        createColumnIfNotExists(tableName, columnName, type, true, null);
    }

    private void createColumnIfNotExists(String tableName, String columnName, String type,
            boolean nullable, String defaultExpression) throws SQLException {
        if (!columnExists(tableName, columnName)) {
            createColumn(tableName, columnName, type, nullable, defaultExpression);
        }
    }
    
    private void createColumnIfNotExistsWithAud(String tableName, String columnName, String type,
            boolean nullable, String defaultExpression) throws SQLException {
        createColumnIfNotExists(tableName, columnName, type, nullable, defaultExpression);
        createColumnIfNotExists(tableName + "_aud", columnName, type, nullable, defaultExpression);
    }
    
    private void dropColumn(String tableName, String columnName) throws SQLException {
        String sql = "ALTER TABLE " + tableName + " DROP COLUMN " + columnName;
        executeUpdate(dbConnection, sql);
    }
    
    private void dropColumnIfExists(String tableName, String columnName) throws SQLException {
        if (columnExists(tableName, columnName))
            dropColumn(tableName, columnName);
    }
    
    private void dropColumnIfExistsWithAud(String tableName, String columnName) throws SQLException {
        dropColumnIfExists(tableName, columnName);
        dropColumnIfExists(tableName + "_aud", columnName);
    }
    
    private void setColumnNotNull(String tableName, String columnName) throws SQLException{
        executeUpdate(dbConnection, "ALTER TABLE " + tableName + " ALTER COLUMN " + columnName + " SET NOT NULL");
    }
    
    private void changeColumnType(String tableName, String columnName, String newType, String conversionExpression) throws SQLException {
        String sql = "ALTER TABLE " + tableName + " ALTER COLUMN " + columnName + " TYPE " + newType;
        if( null != conversionExpression && !"".equals(conversionExpression)) {
            sql += " USING " + conversionExpression;
        }
        executeUpdate(dbConnection, sql);
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
    
    private int getNextId(String tableName) throws SQLException {
        return (Integer)executeScalar(dbConnection,
                "SELECT nextval('" + getSequenceNameForTable(tableName) + "')");
    }

    private boolean sqlExists(Connection connection, String query, Object... params) throws SQLException {
        return (Boolean) executeScalar(connection, "SELECT EXISTS(" + query + ")", params);
    }

    private boolean constraintExists(String fkName) throws SQLException {
        String query = "SELECT * FROM pg_catalog.pg_constraint WHERE conname=?";
        return sqlExists(dbConnection, query, fkName);
    }
    
    private void createForeignKey(String keyName, String childTableName, String childColumnName,
            String parentTableName, String parentColumnName, boolean cascadeOnUpdate, boolean cascadeOnDelete) throws SQLException {

        String updateAction, deleteAction;
        if (cascadeOnUpdate) {
            updateAction = "ON UPDATE CASCADE";
        } else {
            updateAction = "ON UPDATE NO ACTION";
        }

        if (cascadeOnDelete) {
            deleteAction = "ON DELETE CASCADE";
        } else {
            deleteAction = "ON DELETE RESTRICT";
        }

        String stmt = "ALTER TABLE " + childTableName + "\n"
                + "ADD CONSTRAINT " + keyName + " FOREIGN KEY (" + childColumnName + ")\n"
                + "REFERENCES " + parentTableName + " (" + parentColumnName + ") \n"
                + updateAction + " " + deleteAction;
        executeUpdate(dbConnection, stmt);
    }
    
    public void createForeignKeyIfNotExists(String keyName, String childTableName, String childColumnName,
            String parentTableName, String parentColumnName, boolean cascadeOnUpdate, boolean cascadeOnDelete) throws SQLException {
        if(!constraintExists(keyName))
            createForeignKey(keyName, childTableName, childColumnName, parentTableName, parentColumnName,
                    cascadeOnUpdate, cascadeOnDelete);
    }
    
    public void dropConstraint(String tableName, String conName) throws SQLException{
        String stmt = "ALTER TABLE " + tableName + "\n"
                + "DROP CONSTRAINT " + conName;
        executeUpdate(dbConnection, stmt);
    }
    
    public void dropConstraintIfExists(String tableName, String conName) throws SQLException {
        if (constraintExists(conName))
            dropConstraint(tableName, conName);
    }
    
    public void createPrimaryKey(String tableName, String keyName, String... columns) throws SQLException {
        String stmt = "ALTER TABLE " + tableName + " ADD CONSTRAINT " + keyName
                + " PRIMARY KEY (";
        boolean first = true;
        for (String c : columns) {
            if (!first)
                stmt += ",";
            first = false;
            stmt += c;
        }
        stmt += ")";
        executeUpdate(dbConnection, stmt);
    }
    
    public DBVersion getLastDBVersion() throws SQLException{
        ResultSetWrapper rw = executeQuery(dbConnection, "SELECT * FROM dbversion ORDER BY insertdate DESC LIMIT 1");
        ResultSet r = rw.getResultSet();
        if(r.next()){
            DBVersion v = new DBVersion();
            v.setMajor(r.getInt("major"));
            v.setMinor(r.getInt("minor"));
            v.setInsertDate(r.getDate("insertdate"));
            return v;
        }
        return null;
    }
    
    public void insertDBVersion(int major, int minor, Date date) throws SQLException{
        defaultSqlDateType = java.sql.Types.TIMESTAMP;
        executeUpdate(dbConnection, "INSERT INTO dbversion (major, minor, insertdate) VALUES (?,?,?)",
                major, minor, date);
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

            checkAndCreateTable("DBVersion");
            
            DBVersion dbVersion = getLastDBVersion();
            
            String[] normalTables = new String[]{
                "SUser",
                "Role",
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
                "Designer_ExpertInCourses",
                "Arbiter",
                "Question_Evaluation"
            };
            
            String[] joinTables = new String[]{
                "SUser_Role",
                "Designer_ExpertInCoursesQuestions",
            };
            

            String[] allTables = Utils.joinArrays(normalTables, joinTables);
            
            for (String t : allTables) {
                checkAndCreateTable(t);
            }
            
            checkAndCreateTable("RevInfo");
            
            for (String t : allTables) {
                checkAndCreateTable(t + "_AUD");
            }
            
            // Adding version column
            for (String t : normalTables){
                createColumnIfNotExistsWithAud(t.toLowerCase(), "version", "integer", false, "0");
            }


            String tableName = "educationgroup";
            String colName = "code";
            
            if (!columnExists(tableName, colName)) {
                
                createColumn(tableName, colName, charVar255);
                
                ResultSetWrapper rw = executeQuery(dbConnection, "SELECT educationgroup_id FROM educationgroup");
                ResultSet r = rw.getResultSet();
                ArrayList<Integer> ids = new ArrayList<Integer>();
                while (r.next()) {
                    ids.add((Integer) r.getObject(1));
                }
                rw.close();
                int i = 1;
                for (Integer id : ids) {
                    executeUpdate(dbConnection, "UPDATE educationgroup SET code=? WHERE educationgroup_id=?", i++, id);
                }
                setColumnNotNull(tableName, colName);
            }

            tableName = "sanjeshagent";
            colName = "suser_ref";
            if (!columnExists(tableName, colName)) {
                if (sqlExists(dbConnection, "SELECT * FROM " + tableName)) {
                    if (JOptionPane.showConfirmDialog(null,
                            "All of the sanjesh agents you have already defined will be deleted. Press OK to continue.",
                            "Database Updater", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {

                        executeUpdate(dbConnection, "DELETE FROM " + tableName);
                    }
                    else{
                        return false;
                    }
                }
                createColumn(tableName, colName, "integer", false, null);
            }

            if (!constraintExists("fkey_sanjeshagent_suser_ref")) {
                createForeignKey("fkey_sanjeshagent_suser_ref", tableName, colName,
                        "suser", "suser_id", false, false);
            }

            tableName = "universityagent";
            colName = "suser_ref";
            if (!columnExists(tableName, colName)) {
                if (sqlExists(dbConnection, "SELECT * FROM " + tableName)) {
                    if (JOptionPane.showConfirmDialog(null,
                            "All of the university agents you have already defined will be deleted. Press OK to continue.",
                            "Database Updater", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {

                        executeUpdate(dbConnection, "DELETE FROM " + tableName);
                    } else {
                        return false;
                    }
                }
                createColumn(tableName, colName, "integer", false, null);
            }

            if (!constraintExists("fkey_universityagent_suser_ref")) {
                createForeignKey("fkey_universityagent_suser_ref", tableName, colName,
                        "suser", "suser_id", false, false);
            }
            
            createColumnIfNotExists("sanjeshagent", "isdesignerexpert", "boolean", true, null);
            createColumnIfNotExists("sanjeshagent", "isdataexpert", "boolean", true, null);

            tableName = "designer";
            colName = "suser_ref";
            if (!columnExists(tableName, colName)){
                if (sqlExists(dbConnection, "SELECT * FROM " + tableName)) {
                    if (JOptionPane.showConfirmDialog(null,
                            "All of the designers you have already defined will be deleted. Press OK to continue.",
                            "Database Updater", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {

                        executeUpdate(dbConnection, "DELETE FROM " + tableName);
                    } else {
                        return false;
                    }
                }
                createColumn(tableName, colName, "integer", false, null);
            }
            
            tableName += "_aud";
            if (!columnExists(tableName, colName)){
                executeUpdate(dbConnection, "DELETE FROM " + tableName);
                createColumn(tableName, colName, "integer", false, null);
            }    
            
            createForeignKeyIfNotExists("fkey_designer_suser_ref", "designer", "suser_ref", "suser", "suser_id", false, false);
            
            createColumnIfNotExistsWithAud("suser", "isactive", "boolean", false, "true");
            
            if (dbVersion == null || dbVersion.isLessThan(0, 6)) { // less than v0.5
                ResultSetWrapper rw = executeQuery(dbConnection,
                        "SELECT suser_id \n"
                        + "FROM suser u \n"
                        + "  LEFT JOIN universityagent ua ON u.suser_id=ua.suser_ref \n"
                        + "  LEFT JOIN sanjeshagent sa ON u.suser_id=sa.suser_ref \n"
                        + "  LEFT JOIN designer d ON u.suser_id=d.suser_ref \n"
                        + "WHERE suser_id > 1 AND \n"
                        + "  ua.universityagent_id IS NULL AND sa.sanjeshagent_id IS NULL AND d.designer_id is NULL");
                ResultSet r = rw.getResultSet();
                ArrayList<Integer> ids = new ArrayList<Integer>();
                while(r.next()){
                    ids.add(r.getInt(1));
                }
                rw.close();
                for( int id : ids){
                    executeUpdate(dbConnection, "DELETE FROM suser WHERE suser_id=?", id);
                }
            }
            
            // changing length of the topic.name column to 4000
            tableName = "topic";
            if( getColumnCharacterMaxLen(tableName, "name") < 4000 ) {
                changeColumnType(tableName, "name", charVar4000, null);
            }
            tableName += "_aud";
            if( getColumnCharacterMaxLen(tableName, "name") < 4000 ) {
                changeColumnType(tableName, "name", charVar4000, null);
            }
            
            tableName = "designer";
            
            // column is not required
            dropColumnIfExistsWithAud(tableName, "educationgroup_ref");
            dropColumnIfExistsWithAud(tableName, "educationgroup_other");
            dropConstraintIfExists(tableName, "fkey_designer_educationgroup_ref");
            
            // adding new column to 'designer' table
            createColumnIfNotExistsWithAud(tableName, "id_number", charVar255, true, null);
            createColumnIfNotExistsWithAud(tableName, "id_issue_location", charVar255, true, null);
            createColumnIfNotExistsWithAud(tableName, "national_code", charVar255, true, null);
            createColumnIfNotExistsWithAud(tableName, "phone_home", charVar255, true, null);
            createColumnIfNotExistsWithAud(tableName, "phone_cell", charVar255, true, null);
            createColumnIfNotExistsWithAud(tableName, "educationfield_ref", "integer", true, null);
            createColumnIfNotExistsWithAud(tableName, "educationfield_other", charVar500, true, null);
            createColumnIfNotExistsWithAud(tableName, "last_degree", charVar255, true, null);
            createColumnIfNotExistsWithAud(tableName, "degree_university_ref", "integer", true, null);
            createColumnIfNotExistsWithAud(tableName, "degree_university_other", charVar255, true, null);
            createColumnIfNotExistsWithAud(tableName, "home_address", charVar4000, true, null);
            createColumnIfNotExistsWithAud(tableName, "zip_code", charVar255, true, null);
            
            createColumnIfNotExistsWithAud(tableName, "work_university_ref", "integer", true, null);
            createColumnIfNotExistsWithAud(tableName, "work_university_other", charVar255, true, null);
            createColumnIfNotExistsWithAud(tableName, "faculty", charVar255, true, null);
            createColumnIfNotExistsWithAud(tableName, "work_educationfield_ref", "integer", true, null);
            createColumnIfNotExistsWithAud(tableName, "work_educationfield_other", charVar255, true, null);
            createColumnIfNotExistsWithAud(tableName, "work_startdate", "date", true, null);
            createColumnIfNotExistsWithAud(tableName, "phone_work", charVar255, true, null);
            createColumnIfNotExistsWithAud(tableName, "fax_work", charVar255, true, null);
            createColumnIfNotExistsWithAud(tableName, "work_position", charVar255, true, null);
            createColumnIfNotExistsWithAud(tableName, "position_startdate", "date", true, null);
            createColumnIfNotExistsWithAud(tableName, "position_enddate", "date", true, null);
            
            createForeignKeyIfNotExists("fkey_designer_educationfield_ref", tableName, "educationfield_ref",
                    "educationfield", "educationfield_id", false, false);

            createForeignKeyIfNotExists("fkey_designer_degree_university_ref", tableName, "degree_university_ref",
                    "university", "university_id", false, false);

            createForeignKeyIfNotExists("fkey_designer_work_university_ref", tableName, "work_university_ref",
                    "university", "university_id", false, false);

            createForeignKeyIfNotExists("fkey_designer_work_educationfield_ref", tableName, "work_educationfield_ref",
                    "educationfield", "educationfield_id", false, false);

            tableName = "designer_expertincourses";
            colName = "designer_expertincourses_id";
            String keyName = "designer_expertincourses_pkey";
            if (!columnExists(tableName, colName)) {
                dropConstraint(tableName, keyName);
                createColumn(tableName, colName, "serial", true, null);
                executeUpdate(dbConnection, "UPDATE " + tableName + " SET " + colName +
                        "=nextval('"+ getSequenceNameForTable(tableName) + "') WHERE "+ colName + " IS NULL");
                setColumnNotNull(tableName, colName);
            }
            if(!constraintExists(keyName))
                createPrimaryKey(tableName, keyName, colName);

            tableName += "_aud";
            keyName = "designer_expertincourses_aud_pkey";
            if (!columnExists(tableName, colName)) {
                executeUpdate(dbConnection, "DELETE from " + tableName);
                dropConstraint(tableName, keyName);
                createColumn(tableName, colName, "integer", false, null);
            }
            if(!constraintExists(keyName))
                createPrimaryKey(tableName, keyName, colName, "rev");
            
            tableName = "designer_expertincourses";
            createColumnIfNotExistsWithAud(tableName, "start_date", "date", true, null);
            createColumnIfNotExistsWithAud(tableName, "end_date", "date", true, null);
            
            tableName = "question";
            createColumnIfNotExistsWithAud(tableName, "question_image_filename", charVar255, true, null);
            createColumnIfNotExistsWithAud(tableName, "answer_image_filename", charVar255, true, null);
            createColumnIfNotExistsWithAud(tableName, "incorrect_option1_image_filename", charVar255, true, null);
            createColumnIfNotExistsWithAud(tableName, "incorrect_option2_image_filename", charVar255, true, null);
            createColumnIfNotExistsWithAud(tableName, "incorrect_option3_image_filename", charVar255, true, null);
            
            // Function to fix farsi characters
            executeUpdate(dbConnection,
                    "CREATE OR REPLACE FUNCTION fixPersianChars() RETURNS void AS $$\n"
                    + "DECLARE\n"
                    + "  r RECORD;\n"
                    + "BEGIN\n"
                    + "FOR r IN\n"
                    + "	SELECT table_name, column_name\n"
                    + "	FROM information_schema.columns\n"
                    + "	WHERE table_schema='public' AND data_type='character varying'\n"
                    + "LOOP\n"
                    + "	--INSERT INTO inserts VALUES('UPDATE '  || quote_ident(r.table_name) || ' SET '|| quote_ident(r.column_name) || '=replace(replace(' || quote_ident(r.column_name) || E', ''\\u064A'', ''\\u06CC''), ''\\u0643'', ''\\u06A9'')');\n"
                    + "	EXECUTE 'UPDATE '  || quote_ident(r.table_name) || ' SET '|| quote_ident(r.column_name) || '=replace(replace(' || quote_ident(r.column_name) || E', ''\\u064A'', ''\\u06CC''), ''\\u0643'', ''\\u06A9'')';\n"
                    + "END LOOP;\n"
                    + "END;\n"
                    + "$$ LANGUAGE plpgsql;");
            
            executeQuery(dbConnection, "select fixPersianChars()");
            logInfo("Farsi charater fixed.");
            
            // Static data
            AddAdminUserIfNotExists();
            if (dbVersion == null || dbVersion.isLessThan(0,5))
                removeInvalidRoles();
            AddDefaultRoles();
            
//            colName = "modifier_suser_ref";
//            for( String t : normalTables){
//                createColumnIfNotExists(t, colName, "integer", true, null);
//                // Update existing rows to 'admin' user.
//                executeUpdate(dbConnection, "UPDATE " + t + " SET " + colName + "=1 WHERE " + colName + " IS NULL");
//                setColumnNotNull(t,colName);
//            }
            
            
            int major = 0;
            int minor = 10;
            if (dbVersion == null || dbVersion.isLessThan(major, minor)) {
                logInfo("Updating version...");
                insertDBVersion(major, minor, new Date());
                logInfo("v" + major + "." + minor);
            }

            logInfo("Done.");

        } catch (Throwable ex) {
            //logError(ex.toString());
            logError(Utils.getStackTrace(ex));
            return false;
        }

        return true;
    }
    
    private void removeInvalidRoles() throws SQLException {

        String invalidRoles[] = new String[] // with id 1, 2, 3
        {
            "نماینده سازمان سنجش",
            "نماینده دانشگاه",
            "طراح سؤال"
        };
        
        for( int i = 0; i < invalidRoles.length; i++ ){
            RemoveRoleIfExists(i+1, invalidRoles[i]);
        }

    }
    
    private void AddDefaultRoles() throws SQLException {
        
        String role = "کارشناس سؤال (سازمان سنجش)";
        AddRoleIfNotExists(1, role);
        role = "کارشناس طراح (سازمان سنجش)";
        AddRoleIfNotExists(2, role);
        role = "کارشناس داوری سؤال (سازمان سنجش)";
        AddRoleIfNotExists(3, role);
        role = "کارشناس داده (سازمان سنجش)";
        AddRoleIfNotExists(4, role);
        
        role = "نماینده دانشگاه";
        AddRoleIfNotExists(10, role);
        role = "طراح سؤال";
        AddRoleIfNotExists(11, role);
        role = "داور سؤال";
        AddRoleIfNotExists(12, role);

        if (getLastId("role") < 100) {
            setLastId("role", 100); // reserve space for all the possible roles
        }
    }
    
    private void RemoveRoleIfExists(int id, String name) throws SQLException{
        executeUpdate(dbConnection, "DELETE FROM role WHERE role_id=? and name=?", id, name);
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
            String fullName = "سرپرست";
            executeUpdate(dbConnection,
                    "INSERT INTO suser (suser_id, username, password, fullname) VALUES(1, 'admin', 'admin', ?)",
                    fullName);
            }
            if(getLastId("suser") < 10){
                setLastId("suser", 10);
        }
    }

    private void logInfo(String message) {
        pListener.logEvent(LogCategory.INFO, message);
    }

    private void logError(String message) {
        pListener.logEvent(LogCategory.ERROR, message);
    }
    }
