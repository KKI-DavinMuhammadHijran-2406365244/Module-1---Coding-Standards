package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepository();
    }

    @Test
    void shouldCreateProductSuccessfully() {
        Product product = new Product();
        product.setProductId("test01-123-4bde-8820-7fafaf3dbd3");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(1);

        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());

        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void shouldReturnEmptyWhenNoProductsExist() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void shouldHandleMultipleProductsCorrectly() {
        Product product1 = new Product();
        product1.setProductId("test01-123-4bde-8820-7fafaf3dbd3");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(1);

        Product product2 = new Product();
        product2.setProductId("test02-123-437b-a4ff-d02b1de907b");
        product2.setProductName("Sampo Cap Ibu");
        product2.setProductQuantity(2);

        productRepository.create(product1);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());

        Product savedProduct1 = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct1.getProductId());
        assertTrue(productIterator.hasNext());
    }
}