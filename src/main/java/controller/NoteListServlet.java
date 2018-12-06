package controller;

import model.UserNote;
import service.dbservice.DBService;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class NoteListServlet extends HttpServlet {

    private static final String NOTE_LIST_PAGE_TEMPLATE = "noteListPage.html";

    private DBService dbService;
    private TemplateProcessor templateProcessor;

    public NoteListServlet(DBService dbService, TemplateProcessor templateProcessor){
        this.dbService = dbService;
        this.templateProcessor = templateProcessor;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
            Map<String, Object> pageVariables = createPageVariablesMap(dbService);
            String page = templateProcessor.getPage(NOTE_LIST_PAGE_TEMPLATE, pageVariables);
            response.getWriter().println(page);
            response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }

    private static Map<String, Object> createPageVariablesMap(DBService service) {
        Map<String, Object> pageVariables = new HashMap<>();
        try {
            pageVariables.put("noteList", service.getAllNotes(UserNote.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pageVariables;
    }
}
