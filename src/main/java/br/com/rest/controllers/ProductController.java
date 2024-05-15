package br.com.rest.controllers;

import br.com.rest.domain.product.Product;
import br.com.rest.domain.product.ProductRepository;
import br.com.rest.domain.product.RequestProduct;
import br.com.rest.exception.RequestExceptionHandler;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductRepository repository;

    @GetMapping
    public ResponseEntity getAllProducts(){
        var allProducts = repository.findAllByActiveTrue();
        return ResponseEntity.ok(allProducts);
    }

    @PostMapping
    public ResponseEntity registerProduct(@RequestBody @Valid RequestProduct data){
        Product newProduct = new Product(data);
        repository.save(newProduct);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    @Transactional
    public ResponseEntity updateProduct(@RequestBody @Valid RequestProduct data){
        Optional<Product> optinalProduct = repository.findById(data.id());
        if(optinalProduct.isPresent()){
            Product product = optinalProduct.get();
            product.setName(data.name());
            product.setPrice_in_cents(data.price_in_cents());
            return ResponseEntity.ok(product);
        }else{
            throw new EntityNotFoundException();
        }
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity deleteProduct(@RequestBody @Valid RequestProduct data){
        Optional<Product> optinalProduct = repository.findById(data.id());
        if(optinalProduct.isPresent()){
            Product product = optinalProduct.get();
            product.setActive(false);
            return ResponseEntity.noContent().build();
        }else{
            throw new EntityNotFoundException();
        }
    }

}
