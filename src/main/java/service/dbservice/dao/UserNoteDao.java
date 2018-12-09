package service.dbservice.dao;

import model.UserNote;
import org.hibernate.Session;

import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class UserNoteDao {

    private Session session;

    public UserNoteDao(Session session) {
        this.session = session;
    }

    public <T extends UserNote> void save(T user){
        session.save(user);
    }

    public <T extends UserNote> T get(long id, Class<T> clazz) {
           return session.load(clazz,id);
    }

    public <T extends UserNote> List<T> getAllNotes(Class<T> clazz) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(clazz);
        query.from(clazz);
        return session.createQuery(query).list();
    }

    public <T extends UserNote> boolean delete(long id, Class<T> clazz) {
        try{
        session.delete(get(id,clazz));}
        catch (EntityNotFoundException e){
            return false;
        }
        return true;
    }


}
