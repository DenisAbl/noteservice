package service.dbservice;

import model.UserNote;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DummyDBService implements DBService {

    private final List<UserNote> NOTE_LIST;

    public DummyDBService(){
        NOTE_LIST = new ArrayList<>();
    }

    @Override
    public void prepareTable() throws SQLException {

    }

    @Override
    public String getMetaData() {
        return "No DataBase, just a dummy ArrayList";
    }

    @Override
    public <T extends UserNote> List<String> getAllNotes(Class<T> clazz) throws SQLException {
        List<String> allNotesList = new ArrayList<>();
        String name;
        for (UserNote note : NOTE_LIST){
           name = note.getName();
           if (name != null && !name.isEmpty()) allNotesList.add(name);
           else {
               name = note.getContent().substring(0,24) + "...";
               allNotesList.add(name);
           }
        }
        return allNotesList;
    }

    @Override
    public <T extends UserNote> void save(T note) {
        NOTE_LIST.add(note);
    }

    @Override
    public <T extends UserNote> T get(int id, Class<T> clazz) {
        return (T)NOTE_LIST.get(id);
    }

    @Override
    public void delete(int id) {
        NOTE_LIST.remove(id);
    }
}
