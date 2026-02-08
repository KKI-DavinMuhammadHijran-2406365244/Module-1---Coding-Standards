package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductTest {

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71afaf643bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
    }

    @Test
    void shouldReturnCorrectProductId() {
        assertEquals("eb558e9f-1c39-460e-8860-71afaf643bd6", product.getProductId());
    }

    @Test
    void shouldReturnCorrectProductName() {
        assertEquals("Sampo Cap Bambang", product.getProductName());
    }

    @Test
    void shouldReturnCorrectProductQuantity() {
        assertEquals(100, product.getProductQuantity());
    }
}