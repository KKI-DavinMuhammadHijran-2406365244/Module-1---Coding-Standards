package id.ac.ui.cs.advprog.eshop;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class EshopApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void mainShouldRunWithoutThrowing() {
		String[] args = new String[0];
		assertDoesNotThrow(() -> EshopApplication.main(args));
	}


}
