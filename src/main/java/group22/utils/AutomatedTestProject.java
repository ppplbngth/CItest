package group22.utils;

import org.apache.maven.shared.invoker.*;

import java.io.File;
import java.util.Collections;

public class AutomatedTestProject {
    /**
     * automated test
     *
     * @param localPath
     * @return if test results are all correct
     */
    public static Boolean testBranch(String localPath){


        InvocationRequest request = new DefaultInvocationRequest();
        request.setPomFile(new File(localPath + "/pom.xml"));
        request.setGoals(Collections.singletonList("test"));
        Invoker invoker = new DefaultInvoker();
        invoker.setMavenHome(new File("D:\\apache-maven-3.8.2"));

        try {
            InvocationResult result = invoker.execute(request);
            int exitCode = result.getExitCode();
            System.out.println("Exitcode: "+exitCode);
            if (0==exitCode){
                System.out.println("Test passed");
                return true;
            }else{
                System.out.println("Test failed");
            }
        } catch (MavenInvocationException e) {
            e.printStackTrace();
        }

        return false;
    }
}
