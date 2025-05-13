package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.io.IOException;
import java.nio.file.Files;

public class BaseTest {

    protected WebDriver driver;

    @BeforeEach
    public void setUp() throws IOException{
        // Setup ChromeDriver using WebDriverManager
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        // Run in headless mode only in CI
        if (System.getenv("CI") != null && System.getenv("CI").equalsIgnoreCase("true")) {
            // Running in GitHub Actions or another CI environment
            String tempUserDataDir = Files.createTempDirectory("chrome-user-data").toString();
            options.addArguments("--headless=new");  // or "--headless" for older versions
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--remote-debugging-port=9222");
            options.addArguments("--window-size=1920,1080");

            // Avoid user data conflicts in CI
            options.addArguments("--user-data-dir=/tmp/chrome-user-data");
        }

        // Initialize WebDriver
        driver = new ChromeDriver();

        // Maximize the browser window
        driver.manage().window().maximize();

        // Set implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Navigate to the Product Store website
        driver.get("https://www.demoblaze.com/index.html");
    }

    @AfterEach
    public void tearDown() {
        // Close browser after each test
        if (driver != null) {
            driver.quit();
        }
    }
}
