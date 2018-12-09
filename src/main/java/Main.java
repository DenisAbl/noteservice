
import controller.*;
import service.dbservice.DBService;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import service.dbservice.DummyDBService;

public class Main {

    private static final int PORT = 8080;
    private static final String HTML_PATH = "src/main/java/view";

    public static void main(String[] args) throws Exception {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(HTML_PATH);
        resourceHandler.setWelcomeFiles(new String[]{"index.html"});

        DBService dbService = new DummyDBService();
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
