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
                System.out.println("here");
                jsonObject = Helpers.convertBody(request);
                System.out.println("jsonObject:");
                cloneUrl = Helpers.getCloneUrl(jsonObject);
                System.out.println("cloneUrl:" + cloneUrl);
                branch = Helpers.getBranch(jsonObject);
                System.out.println("branch:" + branch);
                CloneRepository.cloneRepository(cloneUrl, localPath, branch);
                System.out.println("ehm");
                CompileProject.compileProject(localPath);
                testRsl = AutomatedTestProject.testBranch(localPath);
                if (!testRsl) {
                    response.setStatus(400);
                    response.getWriter().println("test failed");
                } else {
                    response.getWriter().println("test passed");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
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
        Server server = new Server(8080);
        server.setHandler(new CIServer());
    
    // get the first Connector
        Connector connector = server.getConnectors()[0];
    
    // set the timeout for requests to 60 seconds
        connector.setMaxIdleTime(60 * 1000);

        server.start();
        server.join();
        //Server server = new Server(8080);
        //SelectChannelConnector connector = new SelectChannelConnector();
        //connector.setMaxIdleTime(60000); // set idle timeout to 60 seconds
        /* 
        Server server = new Server(8080);
        server.setHandler(new CIServer());
        //((QueuedThreadPool) server.getThreadPool()).setMaxIdleTime(60000);
        server.start();
        server.join();
        */

    }
}
