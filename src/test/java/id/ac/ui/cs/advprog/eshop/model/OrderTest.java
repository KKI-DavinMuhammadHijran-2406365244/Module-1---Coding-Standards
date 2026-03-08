package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

public class OrderTest {

    private List<Product> products;

    @BeforeEach
    public void setUp() {
        products = new ArrayList<>();

        Product p1 = new Product();
        p1.setProductId("cb55890f-1c39-460e-8860-71af6af63bd6");
        p1.setProductName("Sampo Cap Bambam");
        p1.setProductQuantity(2);
        products.add(p1);

        Product p2 = new Product();
        p2.setProductId("ca623628-4a37-4646-83c7-f23db4820155");
        p2.setProductName("Sabun Cap User");
        p2.setProductQuantity(1);
        products.add(p2);
    }

    @Test
    public void testCreateOrderWithValidProductsSucceeds() {
        Order order = new Order(
                "13652556-012a-4c07-b546-54eb139d479b",
                products,
                1708560800L,
                "Safira Sudrajat"
        );

        Assertions.assertEquals("13652556-012a-4c07-b546-54eb139d479b", order.getId());
        Assertions.assertEquals(2, order.getProducts().size());
        Assertions.assertEquals(products, order.getProducts());
    }

    @Test
    public void testCreateOrderDefaultStatus() {
        Order order = new Order(
                "13652556-012a-4c07-b546-54eb1396479b",
                products,
                1708560800L,
                "Safira Sudrajat"
        );

        Assertions.assertEquals(products, order.getProducts());
        Assertions.assertEquals(2, order.getProducts().size());
        Assertions.assertEquals("Sampo Cap Bambam", order.getProducts().get(0).getProductName());
        Assertions.assertEquals("Sabun Cap User", order.getProducts().get(1).getProductName());

        Assertions.assertEquals("13652556-012a-4c07-b546-54eb1396479b", order.getId());
        Assertions.assertEquals(1708560800L, order.getOrderTime());
        Assertions.assertEquals("Safira Sudrajat", order.getAuthor());
        Assertions.assertEquals("WAITING_PAYMENT", order.getStatus());
    }

    @Test
    public void testCreateOrderSuccessStatus() {
        Order order = new Order(
                "13652556-012a-4c07-b546-54eb1396479b",
                products,
                1708560800L,
                "Safira Sudrajat",
                "SUCCESS"
        );

        Assertions.assertEquals("SUCCESS", order.getStatus());
    }

    @Test
    public void testCreateOrderInvalidStatus() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Order(
                    "13652556-012a-4c07-b546-54eb1396479b",
                    products,
                    1708560800L,
                    "Safira Sudrajat",
                    "MEOW"
            );
        });
    }

    @Test
    public void testSetStatusToCancelled() {
        Order order = new Order(
                "13652556-012a-4c07-b546-54eb1396479b",
                products,
                1708560800L,
                "Safira Sudrajat"
        );

        order.setStatus("CANCELLED");
        Assertions.assertEquals("CANCELLED", order.getStatus());
    }

    @Test
    public void testSetStatusToInvalidStatus() {
        Order order = new Order(
                "13652556-012a-4c07-b546-54eb1396479b",
                products,
                1708560800L,
                "Safira Sudrajat"
        );

        Assertions.assertThrows(IllegalArgumentException.class, () -> order.setStatus("MEOW"));
    }

    @Test
    public void testCreateOrderEmptyProduct() {
        products.clear();

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Order(
                    "13652556-012a-4c07-b546-54eb139d479b",
                    products,
                    1708650000L,
                    "Safina Sundariat"
            );
        });
    }
}