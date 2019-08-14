package applicationFiles.framework.mainClass;

import applicationFiles.framework.global_parameters.GlobalParameters;
import com.beust.jcommander.Parameter;

public class Parameters {

    private static Parameters instance;

    @Parameter(names = {"--chromeLin", "-c"}, description = "Path to Google Chrome Driver")
    private String chromeDriverLin = "/src/main/resources/driver/chromedriver";

    @Parameter(names = {"--chromeWin", "-w"}, description = "Path to Google Chrome Driver")
    private String chromeDriverWin = "./src/main/resources/driver/chromedriver.exe";

    @Parameter(names = "--help", help = true, description = "How to use")
    private boolean help;

    @Parameter(names = {"--url", "-u"}, description = "URL")
    private String url = GlobalParameters.websiteURL;

    @Parameter(names = "--headless", description = "If flag set to 'true' Browser will be started in headless mode (required for running on server)")
    private String headless = "false";

    public static synchronized Parameters instance() {
        if (instance == null) {
            instance = new Parameters();
        }
        return instance;
    }

    public String getChromeDriverLin() {
        return chromeDriverLin;
    }

    public String getChromeDriverWin() {
        return chromeDriverWin;
    }

    public boolean isHelp() {
        return help;
    }

    public String getUrl() {
        return url;
    }

    public String getHeadless() {
        return headless;
    }

}
