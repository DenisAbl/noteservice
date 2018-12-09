package controller;

import model.UserNote;
import service.dbservice.DBService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TemplateHelper {

    public static Map<String, Object> createPageVariablesMapForList(DBService service) {
        Map<String, Object> pageVariables = new HashMap<>();
        List<String> noteList = new ArrayList<>();
        try {
            service.getAllNotes(UserNote.class).forEach(note -> {
                String name = note.getName();
                String content = note.getContent();
                if (name != null && !name.isEmpty() && name.trim().length() > 0){
                    noteList.add("id= " + note.getId() + "; " + name);}
                else if (note.getContent().length() < 24) noteList.add("id= " + note.getId() + "; " + content);
                else noteList.add("id= " + note.getId() + "; " + content.substring(0,24) + "...");
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
        pageVariables.put("noteList", noteList);
        return pageVariables;
    }

    public static Map<String, Object> createPageVariablesMapForSearchList(DBService service, String searchRequest) {
        Map<String, Object> pageVariables = new HashMap<>();
        List<String> noteList = new ArrayList<>();
        try {
            service.getAllNotes(UserNote.class).stream()
                .filter(note -> note.getName().contains(searchRequest) || note.getContent().contains(searchRequest))
                .collect(Collectors.toList())
                .forEach(note -> {
                String name = note.getName();
                String content = note.getContent();
                if (name != null && !name.isEmpty() && name.trim().length() > 0){
                    noteList.add("id= " + note.getId() + "; " + name);}
                else if (note.getContent().length() < 24) noteList.add("id= " + note.getId() + "; " + content);
                else noteList.add("id= " + note.getId() + "; " + content.substring(0,24) + "...");
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
        pageVariables.put("noteList", noteList);
        pageVariables.put("search_request", searchRequest);
        return pageVariables;
    }
}
