package controller;

import service.dbservice.DBService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class NoteListServlet extends HttpServlet {

    private static final String NOTE_LIST_PAGE_TEMPLATE = "noteList.html";
    private static final String ERROR_PAGE_TEMPLATE = "error.html";

    private DBService dbService;
    private TemplateProcessor templateProcessor;

    public NoteListServlet(DBService dbService, TemplateProcessor templateProcessor){
        this.dbService = dbService;
        this.templateProcessor = templateProcessor;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> pageVariables = TemplateHelper.createPageVariablesMapForList(dbService);
        String page = templateProcessor.getPage(NOTE_LIST_PAGE_TEMPLATE, pageVariables);
        response.getWriter().println(page);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
