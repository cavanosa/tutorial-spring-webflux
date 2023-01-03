package com.tutorial.tutorialweblux.handler;

import com.tutorial.tutorialweblux.dto.ProductDto;
import com.tutorial.tutorialweblux.entity.Product;
import com.tutorial.tutorialweblux.service.ProductService;
import com.tutorial.tutorialweblux.validation.ObjectValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProductHandler {

    private final ProductService productService;

    private final ObjectValidator objectValidator;

    public Mono<ServerResponse> getAll(ServerRequest request) {
        Flux<Product> products = productService.getAll();
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(products, Product.class);
    }

    public Mono<ServerResponse> getOne(ServerRequest request) {
        int id = Integer.valueOf(request.pathVariable("id"));
        Mono<Product> product = productService.getById(id);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(product, Product.class);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Mono<ServerResponse> save(ServerRequest request) {
        Mono<ProductDto> dtoMono = request.bodyToMono(ProductDto.class).doOnNext(objectValidator::validate);
        return dtoMono.flatMap(productDto -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(productService.save(productDto), Product.class));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Mono<ServerResponse> update(ServerRequest request) {
        int id = Integer.valueOf(request.pathVariable("id"));
        Mono<ProductDto> dtoMono = request.bodyToMono(ProductDto.class).doOnNext(objectValidator::validate);
        return dtoMono.flatMap(productDto -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(productService.update(id, productDto), Product.class));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Mono<ServerResponse> delete(ServerRequest request) {
        int id = Integer.valueOf(request.pathVariable("id"));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(productService.delete(id), Product.class);
    }

}
