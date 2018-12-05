package service.dbservice;

import model.UserNote;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import service.dbservice.dao.UserNoteDao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.function.Function;

//SET GLOBAL time_zone = '+3:00'

public class DBServiceHibernateImpl implements DBService {

    private final SessionFactory sessionFactory;
    private Properties properties;
    private final String CONFIG_PATH = "/hibernate.properties";
    private final Configuration configuration;


    public DBServiceHibernateImpl() {
        configuration = new Configuration();
        properties = new Properties();

        try {
            properties.load(DBServiceHibernateImpl.class.getResourceAsStream(CONFIG_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
        configuration.addProperties(properties);
        configuration.addAnnotatedClass(UserNote.class);

        sessionFactory = createSessionFactory(configuration);
    }

    private static SessionFactory createSessionFactory(Configuration configuration){
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        StandardServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    @Override
    public void prepareTable() throws SQLException {

    }

    @Override
    public String getMetaData() {
        try {
            return "Connected to: " + configuration.getProperty("hibernate.connection.url") + "\n" +
                    "Dialect: " + configuration.getProperty("hibernate.dialect") + "\n" +
                    "JDBC driver: " + configuration.getProperty("hibernate.connection.driver_class") + "\n" +
                    "hbm2ddl.auto: " + configuration.getProperty("hibernate.hbm2ddl.auto");
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

//    public void saveUsers(String... names) throws SQLException {
//        runTransaction(session -> {
//            UserNoteDao dao = new UserNoteDao(session);
//            UserDataSet user;
//            for (String name: names){
//                user = new UserDataSet();
//                user.setName(name);
//                user.setLogin(name+user.hashCode());
//                dao.save(user);
//            }
//            return true;
//        });
//    }

    @Override
    public <T extends UserNote> String getUserName(int id, Class<T> clazz) throws SQLException {
        return runTransaction(session -> {
            UserNoteDao dao = new UserNoteDao(session);
            return dao.readUserNameById(id,clazz);
        });
    }

    @Override
    public <T extends UserNote> List<String> getAllNames(Class<T> clazz) throws SQLException {
        return runTransaction(session -> {
            UserNoteDao dao = new UserNoteDao(session);
            return dao.readAllNames(clazz);
        });
    }

    @Override
    public <T extends UserNote> void save(T user) {
        runTransaction(session -> {
            UserNoteDao dao = new UserNoteDao(session);
            dao.save(user);
            return true;
        });
    }

    @Override
    public <T extends UserNote> T load(long id, Class<T> clazz) {
        return runTransaction(session -> {
            UserNoteDao dao = new UserNoteDao(session);
            return dao.read(id,clazz);
        });
    }

    @Override
    public <T extends UserNote> List<T> getAllUsers(Class<T> clazz) {
        return runTransaction(session -> {
            UserNoteDao dao = new UserNoteDao(session);
            return dao.readAllUsers(clazz);
        });
    }

    public <T extends UserNote> long getUsersNumber(Class<T> clazz) {
        return runTransaction(session -> {
            UserNoteDao dao = new UserNoteDao(session);
            return dao.getUsersNumber(clazz);
        });
    }

    public <T extends UserNote> boolean existLogin(String login, Class<T> clazz){
        return runTransaction(session -> {
            UserNoteDao dao = new UserNoteDao(session);
           List<String> logins = dao.getAllLogins(clazz);
           return logins.contains(login);
        });
    }

    @Override
    public <T extends UserNote> T getUserByLogin(String login, Class<T> clazz) {
        return runTransaction(session -> {
            UserNoteDao dao = new UserNoteDao(session);
            return dao.readUserByLogin(login,clazz);
        });
    }

    private <T> T runTransaction(Function<Session,T> function){
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            T result = function.apply(session);
            transaction.commit();
            return result;
        }
    }

    public void shutdown() {
        sessionFactory.close();
    }
}
