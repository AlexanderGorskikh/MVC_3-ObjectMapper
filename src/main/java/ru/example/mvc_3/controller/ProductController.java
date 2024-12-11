package ru.example.mvc_3.controller;

import lombok.RequiredArgsConstructor;
import ru.example.mvc_3.entity.Product;
import ru.example.mvc_3.service.ProductService;
import ru.example.mvc_3.util.ProductJsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<String> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return products.stream()
                .map(ProductJsonUtil::toJson)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(ProductJsonUtil.toJson(product));
    }

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody String productJson) {
        Product product = ProductJsonUtil.fromJson(productJson);
        Product savedProduct = productService.createProduct(product);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(ProductJsonUtil.toJson(savedProduct));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id, @RequestBody String productJson) {
        Product product = ProductJsonUtil.fromJson(productJson);
        Product updatedProduct = productService.updateProduct(id, product);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(ProductJsonUtil.toJson(updatedProduct));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
