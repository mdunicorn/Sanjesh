package updater;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Muhammad
 */
public class ResultSetWrapper {
    private ResultSet resultSet;
    private Statement statement;
    
    public ResultSetWrapper(Statement statement, ResultSet resultSet){
        this.statement = statement;
        this.resultSet = resultSet;
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

    public Statement getStatement() {
        return statement;
    }
    
    public void close() throws SQLException{
        resultSet.close();
        statement.close();
    }
}
