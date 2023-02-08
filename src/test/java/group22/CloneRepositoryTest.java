package group22;

import group22.utils.CloneRepository;
import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.Assert.assertTrue;

public class CloneRepositoryTest {

    /**
     * Clones the main repository in the "test-repository".
     * @throws Exception if the repository could not be cloned.
     */
    @Test
    public void testPositiveTrueCloneRepository() throws Exception {
        String repositoryURL = "https://github.com/ppplbngth/CI-server.git";
        String localPath = "test-repository-clone";
        CloneRepository.cloneRepository(repositoryURL, localPath);

        File localRepository = new File(localPath);
        
        assertTrue("Repository was not cloned to the specified location", localRepository.exists());
    }
}
