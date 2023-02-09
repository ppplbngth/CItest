package group22;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.IOException;

import group22.utils.*;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.nio.SelectChannelConnector;

import org.json.simple.JSONObject;



/** 
 Skeleton of a ContinuousIntegrationServer which acts as webhook
 See the Jetty documentation for API documentation of those classes.
*/
public class CIServer extends AbstractHandler
{
    public static final String localRepoPath = "./repo";

    @Override
    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response) 
        throws IOException, ServletException
    {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);


        String method = request.getMethod();
        String cloneUrl = null;
        String branch = null;
        boolean testRsl = false;
        String localPath = "./repo";

        JSONObject jsonObject = new JSONObject();
        // here you do all the continuous integration tasks
        // for example
        // 1st clone your repository
        System.out.println("Method:" + method);
        if ("POST".equals(method)) {
            try {
                jsonObject = Helpers.convertBody(request);
                response.getWriter().println("fetched jsonObject");
                cloneUrl = Helpers.getCloneUrl(jsonObject);
                branch = Helpers.getBranch(jsonObject);
                CloneRepository.cloneRepository(cloneUrl, localPath, branch);
                response.getWriter().println("Cloned repository");
                CompileProject.compileProject(localPath);
                response.getWriter().println("Built repository");
                testRsl = AutomatedTestProject.testBranch(localPath);
                if (!testRsl) {
                    response.setStatus(400);
                    response.getWriter().println("test failed");
                } else {
                    response.getWriter().println("test passed");
                }
            } catch (Exception e) {
                System.out.println("Error:" + e.getMessage());
            }
        }
        
        // 1st clone your repository
        // 2nd compile the code
        // 3d  run all the tests
        
        response.getWriter().println("CI job done");

    }
 
    // used to start the CI server in command line
    public static void main(String[] args) throws Exception
    {
        Server server = new Server();
        SelectChannelConnector connector = new SelectChannelConnector();
        connector.setPort(8080);
        connector.setMaxIdleTime(12000000); // 60 seconds
        server.addConnector(connector);
        server.setHandler(new CIServer());
        server.start();
        server.join();

    }
}
