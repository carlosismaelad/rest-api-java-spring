package br.com.rest.controllers;

import br.com.rest.domain.product.Product;
import br.com.rest.domain.product.ProductRepository;
import br.com.rest.domain.product.RequestProduct;
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
        var allProducts = repository.findAll();
        return ResponseEntity.ok(allProducts);
    }

    @PostMapping
    public ResponseEntity registerProduct(@RequestBody @Valid RequestProduct data){
        Product newProduct = new Product(data);
        repository.save(newProduct);
        return ResponseEntity.ok().build();
    }

//    Usando getReferenceById no PUT
//    @PutMapping
//    public ResponseEntity updateProduct(@RequestBody @Valid RequestProduct data){
//        Product existsProduct = repository.getReferenceById(data.id());
//        existsProduct.setName(data.name());
//        existsProduct.setPrice_in_cents(data.price_in_cents());
//        Product updatedProduct = repository.save(existsProduct);
//        return ResponseEntity.ok(updatedProduct);
//    }

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
            return ResponseEntity.notFound().build();
        }
    }

//    Delete usando PathVariable
//    @DeleteMapping("/{id}")
//    public ResponseEntity deleteProduct(@PathVariable String id){
//        repository.deleteById(id);
//        return ResponseEntity.noContent().build();
//    }

    @DeleteMapping
    public ResponseEntity deleteProduct(@RequestBody @Valid RequestProduct data){
        Optional<Product> existsProduct = repository.findById(data.id());
        if(existsProduct.isPresent()){
            repository.delete(existsProduct.get());
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.noContent().build();
        }
    }

}
