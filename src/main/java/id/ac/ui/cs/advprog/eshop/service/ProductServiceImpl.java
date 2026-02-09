package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    private final List<Product> products = new ArrayList<>();

    @Override
    public Product create(Product product) {
        product.setProductId(UUID.randomUUID().toString());
        products.add(product);
        return product;
    }

    @Override
    public List<Product> findAll() {
        return products;
    }

    @Override
    public Product findById(String productId) {
        return products.stream()
                .filter(p -> p.getProductId().equals(productId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void update(Product updatedProduct) {
        for (Product product : products) {
            if (product.getProductId().equals(updatedProduct.getProductId())) {
                product.setProductName(updatedProduct.getProductName());
                product.setProductQuantity(updatedProduct.getProductQuantity());
                break;
            }
        }
    }
}
