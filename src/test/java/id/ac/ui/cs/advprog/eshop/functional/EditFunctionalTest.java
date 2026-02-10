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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class EditFunctionalTest {

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
    void editProduct_success(ChromeDriver driver) {

        // 1. Create product FIRST
        driver.get(baseUrl + "/product/create");

        driver.findElement(By.name("productName")).sendKeys("Test Product");
        driver.findElement(By.name("productQuantity")).sendKeys("10");
        driver.findElement(By.tagName("button")).click();

        // 2. Go to list page
        driver.get(baseUrl + "/product/list");

        // 3. Click Edit
        driver.findElement(By.linkText("Edit")).click();

        // 4. Edit product
        driver.findElement(By.name("productName")).clear();
        driver.findElement(By.name("productName")).sendKeys("Updated Product");

        driver.findElement(By.tagName("button")).click();

        // 5. Verify
        String updatedName =
                driver.findElement(By.xpath("//table/tbody/tr[1]/td[1]")).getText();

        assertEquals("Updated Product", updatedName);
    }

}
