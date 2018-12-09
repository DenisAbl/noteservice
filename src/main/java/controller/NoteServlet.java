package controller;

import model.UserNote;
import service.dbservice.DBService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class NoteServlet extends HttpServlet {

    private DBService dbService;
    private TemplateProcessor templateProcessor;

    private static final String NOTE_PAGE_TEMPLATE = "note.html";
    private static final String ERROR_PAGE_TEMPLATE = "error.html";

    public NoteServlet(DBService dbService, TemplateProcessor templateProcessor){
        this.dbService = dbService;
        this.templateProcessor = templateProcessor;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String,Object> pageVariables = createPageVariablesMap(request,response,dbService);
        String page;
        if (pageVariables == null) {
            pageVariables = new HashMap<>();
            pageVariables.put("errors", "Incorrect id format");
            page = templateProcessor.getPage(ERROR_PAGE_TEMPLATE, pageVariables);
            response.getWriter().print(page);
            return;
        }
        page = templateProcessor.getPage(NOTE_PAGE_TEMPLATE, pageVariables);
        response.getWriter().print(page);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private static Map<String, Object> createPageVariablesMap(HttpServletRequest request, HttpServletResponse response, DBService service) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        int noteId;
        try {
            noteId = Integer.parseInt(request.getParameter("id"));}
        catch (NumberFormatException e) {
            return null;
        }

        UserNote note = service.get(noteId, UserNote.class);
        if (note == null){
            return null;
        }
        pageVariables.put("noteName",note.getName());
        pageVariables.put("noteContent",note.getContent());
        return pageVariables;
    }
}
