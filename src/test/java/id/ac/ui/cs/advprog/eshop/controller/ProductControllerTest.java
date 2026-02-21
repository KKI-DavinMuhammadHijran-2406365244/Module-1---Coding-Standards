package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService service;

    @Test
    @DisplayName("GET /product/create returns createProduct view with empty product model")
    void getCreatePage() throws Exception {
        mockMvc.perform(get("/product/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("createProduct"))
                .andExpect(model().attributeExists("product"))
                .andExpect(model().attribute("product", hasProperty("productId", nullValue())))
                .andExpect(model().attribute("product", hasProperty("productName", nullValue())))
                .andExpect(model().attribute("product", hasProperty("productQuantity", is(0))));
        verifyNoInteractions(service);
    }

    @Test
    @DisplayName("POST /product/create calls service.create and redirects to /product/list")
    void postCreate() throws Exception {
        // prepare a product that service will return (service.create usually sets id)
        Product returned = new Product();
        returned.setProductId("generated-id");
        returned.setProductName("Widget");
        returned.setProductQuantity(5);

        when(service.create(any(Product.class))).thenReturn(returned);

        mockMvc.perform(post("/product/create")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("productName", "Widget")
                        .param("productQuantity", "5"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"));

        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        verify(service, times(1)).create(captor.capture());
        Product passed = captor.getValue();
        // controller binds form fields to Product; id is not set by form
        assert passed.getProductId() == null;
        assert "Widget".equals(passed.getProductName());
        assert passed.getProductQuantity() == 5;
    }

    @Test
    @DisplayName("GET /product/list returns productList view with products from service")
    void getListPage() throws Exception {
        Product a = new Product();
        a.setProductId("id1");
        a.setProductName("A");
        a.setProductQuantity(1);

        Product b = new Product();
        b.setProductId("id2");
        b.setProductName("B");
        b.setProductQuantity(2);

        when(service.findAll()).thenReturn(List.of(a, b));

        mockMvc.perform(get("/product/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("productList"))
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attribute("products", hasSize(2)))
                .andExpect(model().attribute("products", hasItem(
                        allOf(
                                hasProperty("productId", is("id1")),
                                hasProperty("productName", is("A"))
                        )
                )))
                .andExpect(model().attribute("products", hasItem(
                        allOf(
                                hasProperty("productId", is("id2")),
                                hasProperty("productName", is("B"))
                        )
                )));

        verify(service, times(1)).findAll();
    }

    @Test
    @DisplayName("GET /product/edit/{id} returns editProduct view with product from service")
    void getEditPage() throws Exception {
        Product p = new Product();
        p.setProductId("edit-id");
        p.setProductName("EditMe");
        p.setProductQuantity(3);

        when(service.findById("edit-id")).thenReturn(p);

        mockMvc.perform(get("/product/edit/{id}", "edit-id"))
                .andExpect(status().isOk())
                .andExpect(view().name("editProduct"))
                .andExpect(model().attributeExists("product"))
                .andExpect(model().attribute("product", hasProperty("productId", is("edit-id"))))
                .andExpect(model().attribute("product", hasProperty("productName", is("EditMe"))))
                .andExpect(model().attribute("product", hasProperty("productQuantity", is(3))));

        verify(service, times(1)).findById("edit-id");
    }

    @Test
    @DisplayName("POST /product/edit calls service.update and redirects to /product/list")
    void postEdit() throws Exception {
        // No need to stub service.update (void), just verify invocation
        mockMvc.perform(post("/product/edit")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("productId", "edit-id")
                        .param("productName", "Updated")
                        .param("productQuantity", "42"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"));

        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        verify(service, times(1)).update(captor.capture());
        Product passed = captor.getValue();
        assert "edit-id".equals(passed.getProductId());
        assert "Updated".equals(passed.getProductName());
        assert passed.getProductQuantity() == 42;
    }

    @Test
    @DisplayName("GET /product/delete/{id} calls service.delete and redirects to /product/list")
    void getDelete() throws Exception {
        mockMvc.perform(get("/product/delete/{id}", "del-id"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"));

        verify(service, times(1)).delete("del-id");
    }
}