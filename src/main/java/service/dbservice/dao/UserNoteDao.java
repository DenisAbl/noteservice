package service.dbservice.dao;

import model.UserNote;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserNoteDao {

    private Session session;

    public UserNoteDao(Session session) {
        this.session = session;
    }

    public <T extends UserNote> void save(T user){
        session.save(user);
    }

    public <T extends UserNote> T read(long id, Class<T> clazz) {
           return session.load(clazz,id);
    }

    public <T extends UserNote> List<T> readAllNames(Class<T> clazz) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(clazz);
        query.from(clazz);
        return session.createQuery(query).list();
    }
}
