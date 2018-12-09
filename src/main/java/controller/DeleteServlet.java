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

public class DeleteServlet extends HttpServlet {

    private static final String NOTE_LIST_PAGE_TEMPLATE = "noteList.html";
    private static final String ERROR_PAGE_TEMPLATE = "error.html";

    private DBService dbService;
    private TemplateProcessor templateProcessor;

    public DeleteServlet(DBService dbService, TemplateProcessor templateProcessor){
        this.dbService = dbService;
        this.templateProcessor = templateProcessor;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> pageVariables;
        String page;
        int noteId;
        try {
            noteId = Integer.parseInt(request.getParameter("id"));}
        catch (NumberFormatException e) {
            pageVariables = new HashMap<>();
            pageVariables.put("errors", "Incorrect id format");
            page = templateProcessor.getPage(ERROR_PAGE_TEMPLATE, pageVariables);
            response.getWriter().print(page);
            return;
        }
        if (dbService.delete(noteId,UserNote.class)){
            pageVariables = TemplateHelper.createPageVariablesMapForList(dbService);
            page = templateProcessor.getPage(NOTE_LIST_PAGE_TEMPLATE, pageVariables);
            response.getWriter().println(page);
            response.setStatus(HttpServletResponse.SC_OK);}
        else {
            pageVariables = new HashMap<>();
            pageVariables.put("errors", "No such id");
            page = templateProcessor.getPage(ERROR_PAGE_TEMPLATE, pageVariables);
            response.getWriter().print(page);
        }
    }
}
