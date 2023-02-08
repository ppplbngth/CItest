package group22.utils;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;

public class CloneRepository {
    /**
     * Clones a Git repository.
     * @param repositoryURL the url to the repository.
     * @param localPath the specified path to the local repository being cloned.
     */
    public static void cloneRepository(String repositoryURL, String localPath) {
        
        try {
            Git.cloneRepository()
                .setURI(repositoryURL)
                .setDirectory(new File(localPath))
                .call();
            System.out.println("Repository cloned successfully to " + localPath);
        } catch (Exception e) {
            System.out.println("Error cloning repository: " + e.getMessage());
        }
    }

}