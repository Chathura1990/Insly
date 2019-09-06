package applicationFiles.framework.globalParameters;

import com.beust.jcommander.Parameter;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Parameters {

    private static Parameters instance;

    private Path currentRelativePath = Paths.get("");//getting current path
    private String path1 = currentRelativePath.toAbsolutePath().toString();

    @Parameter(names = {"--chromeLin", "-c"}, description = "Path to Google Chrome Driver")
    private String chromeDriverLin = path1+"/src/main/resources/driver/chromedriver";

    @Parameter(names = {"--chromeWin", "-w"}, description = "Path to Google Chrome Driver")
    private String chromeDriverWin = "./src/main/resources/driver/chromedriver.exe";

    @Parameter(names = {"--url", "-u"}, description = "URL")
    private String url = GlobalParameters.WEBSITE_URL;

    @Parameter(names = "--help", help = true, description = "How to use")
    private boolean help;

    @Parameter(names = "--headless", description = "If flag set to 'true' Browser will be started in headless mode (required for running on server)")
    private String headless = "false";

    public static synchronized Parameters instance() {
        if (instance == null) {
            instance = new Parameters();
        }
        return instance;
    }

    public String getUrl() {
        return url;
    }

    public String getChromeDriverWin() {
        return chromeDriverWin;
    }

    public boolean isHelp() {
        return help;
    }

    public String getHeadless() {
        return headless;
    }

}
