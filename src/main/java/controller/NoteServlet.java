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

    private static final String NOTE_PAGE_TEMPLATE = "notePage.html";

    public NoteServlet(DBService dbService, TemplateProcessor templateProcessor){
        this.dbService = dbService;
        this.templateProcessor = templateProcessor;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String,Object> pageVariables = createPageVariablesMap(request,response,dbService);
        String page = templateProcessor.getPage(NOTE_PAGE_TEMPLATE, pageVariables);
        response.getWriter().print(page);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private static Map<String, Object> createPageVariablesMap(HttpServletRequest request, HttpServletResponse response, DBService service) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        int noteId;
        try {
            noteId = Integer.parseInt(request.getParameter("id"));}
        catch (NumberFormatException e) {
            response.getWriter().print("Incorrect id format");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        UserNote note = service.get(noteId, UserNote.class);
        pageVariables.put("noteName",note.getName());
        pageVariables.put("noteContent",note.getContent());
        return pageVariables;
    }
}
