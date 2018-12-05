package service.dbservice;

import model.UserNote;

import java.sql.SQLException;
import java.util.List;

public interface DBService {

    void prepareTable() throws SQLException;

    String getMetaData();

    public <T extends UserNote> String getUserName(int id, Class<T> clazz) throws SQLException;

    <T extends UserNote> List<String> getAllNames(Class<T> clazz) throws SQLException;

    <T extends UserNote> void save(T user);

    <T extends UserNote> T load(long id, Class<T> clazz);

    <T extends UserNote> List<T> getAllUsers(Class<T> clazz) throws SQLException;

    <T extends UserNote> long getUsersNumber(Class<T> clazz);

    <T extends UserNote> boolean existLogin(String login, Class<T> clazz);

    <T extends UserNote> T getUserByLogin(String login, Class<T> clazz);
}
