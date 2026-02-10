package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
public class CreateProductFunctionalTest {

    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setupTest() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    @Test
    void createProduct_success(ChromeDriver driver) {
        // 1. Open create product page
        driver.get(baseUrl + "/product/create");

        // 2. Fill product form
        driver.findElement(By.name("productName")).sendKeys("Functional Product");
        driver.findElement(By.name("productQuantity")).sendKeys("50");

        // 3. Submit form
        driver.findElement(By.tagName("button")).click();

        // 4. Go to product list page
        driver.get(baseUrl + "/product/list");

        // 5. Verify product appears in list
        assertTrue(driver.getPageSource().contains("Functional Product"));
        assertTrue(driver.getPageSource().contains("50"));
    }
}