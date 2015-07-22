package com.limpygnome.servlet;

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
        config.setDefaultEncoding("UTF-8");
        config.setURLEscapingCharset("UTF-8");
        
        // Set actual value
        templateConfiguration = config;
    }
    
    private static  Configuration   templateConfiguration;
    
    private String                  templatePage;
    private boolean                 templatePageFill;
    private String                  templatePageTitle;
    private String                  templatePageContent;
    
    public TemplateSettings()
    {
        // Set default config
        templatePageTitle = "Untitled Page";
        templatePage = "#layout";
        templatePageFill = true;
        templatePageContent = "404";
    }
    
    public void render(HashMap<String,Object> templateData, HttpServletResponse response) throws IOException
    {
        // Set layout areas
        templateData.put("title", templatePageTitle);
        templateData.put("content", templatePageContent);
        
        if(templatePageFill)
            templateData.put("page_fill", true);
        
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
            // Make sure all content has been written
            out.flush();
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

    public boolean isTemplatePageFill()
    {
        return templatePageFill;
    }
    public void setTemplatePageFill(boolean templatePageFill)
    {
        this.templatePageFill = templatePageFill;
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
