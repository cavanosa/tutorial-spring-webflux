package com.tutorial.tutorialweblux.service;

import com.tutorial.tutorialweblux.entity.Product;
import com.tutorial.tutorialweblux.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Flux<Product> getAll() {
        return productRepository.findAll();
    }

    public Mono<Product> getById(int id) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new Exception("product not found")));
    }

    public Mono<Product> save(Product product) {
        Mono<Boolean> existsName = productRepository.findByName(product.getName()).hasElement();
        return existsName.flatMap(exists -> exists ? Mono.error(new Exception("product name already in use"))
                : productRepository.save(product));
    }

    public Mono<Product> update(int id, Product product) {
        Mono<Boolean> productId = productRepository.findById(id).hasElement();
        Mono<Boolean> productRepeatedName = productRepository.repeatedName(id, product.getName()).hasElement();
        return productId.flatMap(
                existsId -> existsId ?
                        productRepeatedName.flatMap(existsName -> existsName ? Mono.error(new Exception("product name already in use"))
                                : productRepository.save(new Product(id, product.getName(), product.getPrice())))
        : Mono.error(new Exception("product not found")));
    }

    public Mono<Void> delete(int id) {
        Mono<Boolean> productId = productRepository.findById(id).hasElement();
        return productId.flatMap(exists -> exists ? productRepository.deleteById(id) : Mono.error(new Exception("product not found")));
    }
}
