package service.dbservice;

import model.UserNote;
import java.sql.SQLException;
import java.util.List;

public interface DBService {

    String getMetaData();

    <T extends UserNote> List<T> getAllNotes(Class<T> clazz) throws SQLException;

    <T extends UserNote> void save(T note);

    <T extends UserNote> T get(int id, Class<T> clazz);

    <T extends UserNote> boolean delete(int id, Class<T> clazz);
}
