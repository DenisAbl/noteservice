package service.dbservice;

import model.UserNote;
import java.sql.SQLException;
import java.util.List;

public interface DBService {

    void prepareTable() throws SQLException;

    String getMetaData();

    <T extends UserNote> List<String> getAllNotes(Class<T> clazz) throws SQLException;

    <T extends UserNote> void save(T note);

    <T extends UserNote> T get(int id, Class<T> clazz);

    void delete(int id);
}
