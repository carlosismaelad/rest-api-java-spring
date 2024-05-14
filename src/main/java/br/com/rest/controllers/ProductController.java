package br.com.rest.controllers;

import br.com.rest.domain.product.Product;
import br.com.rest.domain.product.ProductRepository;
import br.com.rest.domain.product.RequestProduct;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductRepository repository;

    @GetMapping
    public ResponseEntity getAllProducts(){
        var allProducts = repository.findAll();
        return ResponseEntity.ok(allProducts);
    }

    @PostMapping
    public ResponseEntity registerProduct(@RequestBody @Valid RequestProduct data){
        Product newProduct = new Product(data);
        repository.save(newProduct);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity updateProduct(@RequestBody @Valid RequestProduct data){
        Product existsProduct = repository.getReferenceById(data.id());
        existsProduct.setName(data.name());
        existsProduct.setPrice_in_cents(data.price_in_cents());
        Product updatedProduct = repository.save(existsProduct);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping
    public ResponseEntity deleteProduct(@RequestBody @Valid RequestProduct data){
        Product existsProduct = repository.getReferenceById(data.id());
        repository.delete(existsProduct);
        return ResponseEntity.noContent().build();
    }



}
