package group22;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
 
import java.io.IOException;
 
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import group22.utils.CloneRepository;
import group22.utils.CompileProject;
import group22.utils.Helpers;


/** 
 Skeleton of a ContinuousIntegrationServer which acts as webhook
 See the Jetty documentation for API documentation of those classes.
*/
public class CIServer extends AbstractHandler
{
    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response) 
        throws IOException, ServletException
    {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);

        System.out.println(target);
        String method = request.getMethod();
        System.out.println(method);
        System.out.println(request);
        JSONObject jsonObject = new JSONObject();
        // here you do all the continuous integration tasks
        // for example
        // 1st clone your repository
        if ("POST".equals(method))
            try{
                System.out.println("Goes here");
                jsonObject = Helpers.convertBody(baseRequest);
            } catch (Exception e){
                System.out.print("Exception?");
                System.out.println(e.getMessage());
            }
        
        String localPath = "./repo";
        String cloneUrl = Helpers.getCloneUrl(jsonObject);
        // 1st clone your repository
        CloneRepository.cloneRepository(cloneUrl, localPath);
        // 2nd compile the code
        CompileProject.compileProject(localPath);
        // 3d  run all the tests
        
        response.getWriter().println("CI job done");
    }
 
    // used to start the CI server in command line
    public static void main(String[] args) throws Exception
    {
        Server server = new Server(8080);
        server.setHandler(new CIServer()); 
        server.start();
        server.join();
    }
}
