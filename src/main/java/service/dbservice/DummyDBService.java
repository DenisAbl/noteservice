package service.dbservice;

import model.UserNote;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DummyDBService implements DBService {

    private static int id;

    private final List<UserNote> NOTE_LIST;

    public DummyDBService(){
        NOTE_LIST = new ArrayList<>();
    }

    @Override
    public void prepareTable() throws SQLException {
        System.out.println("Table is ready");
    }

    @Override
    public String getMetaData() {
        return "No DataBase, just a dummy ArrayList";
    }

    @Override
    public <T extends UserNote> List<T> getAllNotes(Class<T> clazz) throws SQLException {
        List<T> allNotesList = (List<T>) NOTE_LIST;
        return allNotesList;
    }

    @Override
    public <T extends UserNote> void save(T note) {
        note.setId(++id);
        NOTE_LIST.add(note);
    }

    @Override
    public <T extends UserNote> T get(int id, Class<T> clazz) {
        UserNote userNote = null;
        for (UserNote note: NOTE_LIST){
            if (note.getId() == id)  userNote = note;
        }
        return (T) userNote;
    }

    @Override
    public boolean delete(int id) {
        int size = NOTE_LIST.size();
        NOTE_LIST.removeIf(note -> note.getId() == id);
        return size > NOTE_LIST.size();
    }
}
