package com.orderSystem.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orderSystem.entity.Product;
import com.orderSystem.repository.ProductRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository productRepo;

    public ProductController(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    @PostMapping
    public Product createProduct(@Valid @RequestBody Product product) {

        validateProduct(product);

        return productRepo.save(product);
    }

    @PutMapping("/{id}")
    public Product updateProduct(
            @PathVariable Long id,
            @RequestBody Product updatedProduct) {

        Product existing = productRepo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Product not found"));

        validateProduct(updatedProduct);

        existing.setName(updatedProduct.getName());
        existing.setPrice(updatedProduct.getPrice());
        existing.setQuantity(updatedProduct.getQuantity());

        return productRepo.save(existing);
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) {
        return productRepo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Product not found"));
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
        if (!productRepo.existsById(id)) {
            throw new RuntimeException("Product not found");
        }
        productRepo.deleteById(id);
        return "Product deleted successfully";
    }

    private void validateProduct(Product product) {

        if (product.getPrice() == null || product.getPrice() <= 0) {
            throw new RuntimeException("Invalid price");
        }

        if (product.getQuantity() == null || product.getQuantity() < 0) {
            throw new RuntimeException("Invalid quantity");
        }

        if (product.getName() == null || product.getName().isBlank()) {
            throw new RuntimeException("Invalid name");
        }
    }
}
