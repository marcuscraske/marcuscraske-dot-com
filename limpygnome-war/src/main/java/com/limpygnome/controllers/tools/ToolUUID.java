package com.limpygnome.controllers.tools;

import com.limpygnome.servlet.ExtendedHttpServlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@WebServlet(urlPatterns = {"/tools/uuid"})
public class ToolUUID extends ExtendedHttpServlet
{

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        int uuidCount = 5;
        
        // Check request params
        String rawRequestedUuids = request.getParameter("count");
        if(rawRequestedUuids != null && rawRequestedUuids.length() > 0)
        {
            try
            {
                int requestedUuids = Integer.parseInt(rawRequestedUuids);
                if(requestedUuids > 0 && requestedUuids < 20)
                {
                    uuidCount = requestedUuids;
                }
            }
            catch(NumberFormatException ex) {}
        }
        
        // Generate new UUID(s)
        UUID[] uuids = new UUID[uuidCount];
        for(int i = 0; i < uuidCount; i++)
        {
            uuids[i] = UUID.randomUUID();
        }
        
        // Setup template
        templateSettings.setTemplatePageContent("tools/#uuid");
        templateData.put("uuids", uuids);
        templateData.put("uuid_count", uuidCount);
    }
}
