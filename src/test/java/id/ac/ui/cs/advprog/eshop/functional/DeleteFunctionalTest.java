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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class DeleteFunctionalTest {

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
    void deleteProduct_success(ChromeDriver driver) {
        // 1. Open create product page
        driver.get(baseUrl + "/product/create");

        // 2. Create product
        driver.findElement(By.name("productName")).sendKeys("Delete Me");
        driver.findElement(By.name("productQuantity")).sendKeys("1000");
        driver.findElement(By.tagName("button")).click();

        // 3. Go to product list
        driver.get(baseUrl + "/product/list");

        // 4. Assert product exists BEFORE delete
        assertTrue(driver.getPageSource().contains("Delete Me"));

        // 5. Click delete button
        driver.findElement(By.xpath(
                "//tr[td[normalize-space()='Delete Me']]//a[normalize-space()='Delete']"
        )).click();

        driver.switchTo().alert().accept();

        // 6. Assert product is gone AFTER delete
        assertFalse(driver.getPageSource().contains("Delete Me"));

        // 7. Ensure redirect
        assertTrue(driver.getCurrentUrl().contains("/product/list"));
    }



    @Test
    void deleteProduct_invalidId_redirectsSafely(ChromeDriver driver) {
        driver.get(baseUrl + "/product/delete/invalid-id-123");
        assertTrue(driver.getCurrentUrl().contains("/product/list"));
    }
}

