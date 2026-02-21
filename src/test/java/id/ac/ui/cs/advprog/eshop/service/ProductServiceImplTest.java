package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductServiceImplTest {

    private ProductServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new ProductServiceImpl();
    }

    @Test
    void createShouldAssignNonNullIdAndAddToList() {
        Product p = new Product();
        p.setProductName("Widget");
        p.setProductQuantity(10);

        Product created = service.create(p);

        assertNotNull(created.getProductId(), "create should assign a non-null productId");
        List<Product> all = service.findAll();
        assertTrue(all.contains(created), "created product should be in the product list");
        assertEquals("Widget", created.getProductName());
        assertEquals(10, created.getProductQuantity());
    }

    @Test
    void createNullShouldThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> service.create(null));
    }

    @Test
    void findAllShouldReturnAllCreatedProducts() {
        Product a = new Product();
        a.setProductName("A");
        a.setProductQuantity(1);
        Product b = new Product();
        b.setProductName("B");
        b.setProductQuantity(2);

        service.create(a);
        service.create(b);

        List<Product> all = service.findAll();
        assertEquals(2, all.size());
        assertTrue(all.stream().anyMatch(p -> "A".equals(p.getProductName())));
        assertTrue(all.stream().anyMatch(p -> "B".equals(p.getProductName())));
    }

    @Test
    void findByIdShouldReturnProductWhenExists() {
        Product p = new Product();
        p.setProductName("FindMe");
        p.setProductQuantity(5);

        Product created = service.create(p);
        String id = created.getProductId();

        Product found = service.findById(id);
        assertNotNull(found);
        assertEquals(id, found.getProductId());
        assertEquals("FindMe", found.getProductName());
    }

    @Test
    void findByIdShouldReturnNullWhenNotFound() {
        Product found = service.findById("non-existent-id");
        assertNull(found);
    }

    @Test
    void updateShouldModifyExistingProduct() {
        Product p = new Product();
        p.setProductName("OldName");
        p.setProductQuantity(3);

        Product created = service.create(p);
        String id = created.getProductId();

        Product updated = new Product();
        updated.setProductId(id);
        updated.setProductName("NewName");
        updated.setProductQuantity(99);

        service.update(updated);

        Product after = service.findById(id);
        assertNotNull(after);
        assertEquals("NewName", after.getProductName());
        assertEquals(99, after.getProductQuantity());
    }

    @Test
    void updateWithNonMatchingIdShouldDoNothing() {
        Product p = new Product();
        p.setProductName("Keep");
        p.setProductQuantity(1);
        Product created = service.create(p);

        Product updated = new Product();
        updated.setProductId("no-such-id");
        updated.setProductName("Changed");
        updated.setProductQuantity(50);

        service.update(updated);

        Product after = service.findById(created.getProductId());
        assertNotNull(after);
        assertEquals("Keep", after.getProductName());
        assertEquals(1, after.getProductQuantity());
    }

    @Test
    void updateWithNullIdShouldDoNothing() {
        Product p = new Product();
        p.setProductName("Original");
        p.setProductQuantity(2);
        Product created = service.create(p);

        Product updated = new Product();
        // updated.productId left null
        updated.setProductName("ShouldNotApply");
        updated.setProductQuantity(999);

        // Should not throw and should not change existing product
        service.update(updated);

        Product after = service.findById(created.getProductId());
        assertNotNull(after);
        assertEquals("Original", after.getProductName());
        assertEquals(2, after.getProductQuantity());
    }

    @Test
    void deleteShouldRemoveProductById() {
        Product p = new Product();
        p.setProductName("ToDelete");
        p.setProductQuantity(7);

        Product created = service.create(p);
        String id = created.getProductId();

        service.delete(id);
        assertNull(service.findById(id));
        assertTrue(service.findAll().isEmpty());
    }

    @Test
    void deleteWithNonExistingIdShouldNotThrowAndNotChangeList() {
        Product p = new Product();
        p.setProductName("Remain");
        p.setProductQuantity(4);
        service.create(p);

        // Should not throw
        service.delete("missing-id");
        assertEquals(1, service.findAll().size());
    }

    @Test
    void deleteWithNullIdShouldNotThrowAndNotChangeList() {
        Product p = new Product();
        p.setProductName("Remain2");
        p.setProductQuantity(8);
        service.create(p);

        // delete(null) should not throw with current implementation
        assertDoesNotThrow(() -> service.delete(null));
        assertEquals(1, service.findAll().size());
    }

    @Test
    void findAllReturnsInternalListReferenceAndIsMutable() {
        Product p = new Product();
        p.setProductName("Mutable");
        p.setProductQuantity(1);
        Product created = service.create(p);

        List<Product> returned = service.findAll();
        // modify returned list
        returned.clear();

        // because findAll returns the internal list, the service list is affected
        assertTrue(service.findAll().isEmpty(), "findAll returns internal list reference; external modification affects service");
    }
}