import com.limpygnome.ExtendedHttpServlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/hello_world/*"})
public class Home extends ExtendedHttpServlet
{
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        templateSettings.setTemplatePageContent("home");
        templateSettings.setTemplatePageTitle("Home");
    }
}
