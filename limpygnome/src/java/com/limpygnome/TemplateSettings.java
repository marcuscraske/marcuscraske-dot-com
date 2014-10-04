package com.limpygnome;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author limpygnome
 */
public class TemplateSettings
{
    static
    {
        // Setup configuration for rendering templates
        Configuration config  = new Configuration();
        config.setClassForTemplateLoading(TemplateSettings.class, "/");
        
        // Set actual value
        templateConfiguration = config;
    }
    
    private static  Configuration   templateConfiguration;
    
    private String                  templatePage;
    private String                  templatePageTitle;
    private String                  templatePageContent;
    
    public TemplateSettings()
    {
        // Set default config
        templatePageTitle = "Untitled Page";
        templatePage = "layout";
        templatePageContent = "404";
    }
    
    public void render(HashMap<String,Object> templateData, HttpServletResponse response) throws IOException
    {
        // Set layout areas
        templateData.put("title", templatePageTitle);
        templateData.put("content", templatePageContent);
        
        // Fetch template
        Template t = templateConfiguration.getTemplate("/com/limpygnome/views/"+templatePage+".ftl");
        
        // Fetch printwriter for output
        PrintWriter out = response.getWriter();
        
        // Render template to writer
        try
        {
            t.process(templateData, out);
        }
        catch(TemplateException ex)
        {
            ex.printStackTrace(System.err);
        }
        finally
        {
            // Finish writer
            out.flush();
            out.close();
        }
    }

    public String getTemplatePage()
    {
        return templatePage;
    }
    public void setTemplatePage(String templatePage)
    {
        this.templatePage = templatePage;
    }

    public String getTemplatePageTitle()
    {
        return templatePageTitle;
    }
    public void setTemplatePageTitle(String templatePageTitle)
    {
        this.templatePageTitle = templatePageTitle;
    }

    public String getTemplatePageContent()
    {
        return templatePageContent;
    }
    public void setTemplatePageContent(String templatePageContent)
    {
        this.templatePageContent = templatePageContent;
    }
}
