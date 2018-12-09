package controller;

import model.UserNote;
import service.dbservice.DBService;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AddNoteServlet extends HttpServlet {

    private DBService dbService;
    private TemplateProcessor templateProcessor;

    public AddNoteServlet(DBService dbService, TemplateProcessor templateProcessor){
        this.dbService = dbService;
        this.templateProcessor = templateProcessor;
    }

    private static final String ADD_NOTE_PAGE_TEMPLATE = "addNote.html";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> pageVariables = createPageVariablesMap(request, response, dbService);
        String page = templateProcessor.getPage(ADD_NOTE_PAGE_TEMPLATE, pageVariables);
        response.getWriter().println(page);
        response.setStatus(HttpServletResponse.SC_OK);
    }
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
         String name = request.getParameter("noteName");
         String content = request.getParameter("noteContent");
         dbService.save(new UserNote(name, content));
         response.setStatus(HttpServletResponse.SC_OK);
         response.sendRedirect("/noteList");
    }

    private static Map<String, Object> createPageVariablesMap(HttpServletRequest request, HttpServletResponse response, DBService service) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        return pageVariables;
    }

}
