package applicationFiles.app_manager.test_base;

import applicationFiles.app_manager.ApplicationManager;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;

public class TestBase {

    protected final ApplicationManager app = new ApplicationManager();

    @BeforeClass
    public void setup_Application() {
        app.init();
    }

    @AfterTest
    public void tearDown() throws InterruptedException {
        app.stop();
    }
}
