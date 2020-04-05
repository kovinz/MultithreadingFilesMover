package config;

public class DirectoryConfig {
    private String predicateToDirectory;
    private String predicateFromDirectory;


    public String getPredicateToDirectory() {
        return predicateToDirectory;
    }

    public void setPredicateToDirectory(String predicateToDirectory) {
        this.predicateToDirectory = predicateToDirectory;
    }

    public String getPredicateFromDirectory() {
        return predicateFromDirectory;
    }

    public void setPredicateFromDirectory(String predicateFromDirectory) {
        this.predicateFromDirectory = predicateFromDirectory;
    }
}
