
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
        servletContext.addServlet(new ServletHolder(new NoteListServlet(dbService, templateProcessor)),"/noteListPage");
        servletContext.addServlet(new ServletHolder(new AddNoteServlet(dbService, templateProcessor)),"/addNotePage");
        servletContext.addServlet(new ServletHolder(new NoteServlet(dbService, templateProcessor)),"/notePage");

        Server server = new Server(PORT);
        server.setHandler(new HandlerList(resourceHandler,servletContext));

        server.start();
        server.join();

    }
}
