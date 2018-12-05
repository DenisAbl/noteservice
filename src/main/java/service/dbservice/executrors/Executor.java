package service.dbservice.executrors;

import service.dbservice.handlers.ResultSetHandler;
import service.dbservice.handlers.StatementHandler;

import java.sql.SQLException;


public interface Executor {

    <T> T execQuery(String query, ResultSetHandler<T> resultSetHandler) throws SQLException;

    void execUpdate(String query, StatementHandler statement) throws SQLException;
}
