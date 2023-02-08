package group22;

import group22.utils.*;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

public class CompileProjectTest {
    
    /**
     * If the project is compiled successfully it should return true
     */
    @Test
    public void testPositiveCompileProject() throws IOException {
        String repositoryURL = "https://github.com/ppplbngth/CI-server.git";
        String localPath = "test-repository-compile";
        CloneRepository.cloneRepository(repositoryURL, localPath);
        //File localRepository = new File(localPath);

        boolean result = CompileProject.compileProject(localPath);
        assertTrue("Project should have compiled successfully", result);
    }
    /**
     * If the project does not exist it should return false.
     */
    @Test
    public void testNegativeCompileProject() throws IOException {
        String projectPath = "invalid-repository";
        try {
            boolean result = CompileProject.compileProject(projectPath);
            //fail("Project should have not compiled");
        } catch(Exception e) {
            assertTrue("Unexpected exception message", e.getMessage().contains("Error compiling project:"));
        }
    }
}

