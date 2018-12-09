
import controller.*;
import org.eclipse.jetty.util.resource.Resource;
import service.dbservice.DBService;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import service.dbservice.DBServiceHibernateImpl;
import service.dbservice.DummyDBService;

public class Main {

    private static final int PORT = 8080;
    private static final String HTML_PATH = "/static";

    public static void main(String[] args) throws Exception {
        ResourceHandler resourceHandler = new ResourceHandler();
        Resource resource = Resource.newClassPathResource(HTML_PATH);
        resourceHandler.setBaseResource(resource);
        resourceHandler.setWelcomeFiles(new String[]{"index.html"});

        DBService dbService = new DBServiceHibernateImpl();
        TemplateProcessor templateProcessor = new TemplateProcessor();

        ServletContextHandler servletContext = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContext.addServlet(new ServletHolder(new NoteListServlet(dbService, templateProcessor)),"/noteList");
        servletContext.addServlet(new ServletHolder(new AddNoteServlet(dbService, templateProcessor)),"/addNote");
        servletContext.addServlet(new ServletHolder(new NoteServlet(dbService, templateProcessor)),"/note");
        servletContext.addServlet(new ServletHolder(new DeleteServlet(dbService, templateProcessor)),"/deleteNote");
        servletContext.addServlet(new ServletHolder(new SearchServlet(dbService, templateProcessor)),"/searchNote");

        Server server = new Server(PORT);
        server.setHandler(new HandlerList(resourceHandler,servletContext));

        server.start();
        server.join();

    }
}
