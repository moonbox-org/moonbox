package org.moonbox.productmodule.controllers;

import lombok.extern.slf4j.Slf4j;
import org.moonbox.productmodule.models.Product;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/products", produces = APPLICATION_JSON_VALUE)
public class ProductController {


    /* ----- PARAMETERS ----- */


    private final BeanFactory beanFactory;

    public ProductController(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }


    /* ----- METHODS ----- */

    /**
     * returns the list of all products by page
     */
    @GetMapping
    @PreAuthorize("hasRole('product:read) || hasRole('superuser'")
    ResponseEntity<Page<Product>> getProducts() {
        log.info("START getProducts");



        Page<Product> output = Page.empty();

        return ResponseEntity.ok(output);
    }

    /**
     * test endpoint
     */
    @GetMapping(value = "/test")
    @PreAuthorize("(hasRole('product:read') && hasRole('product:write')) || hasRole('superuser')")
    ResponseEntity<String> test() {
        return ResponseEntity.ok("Hello from Product Module test endpoint!");
    }

}
