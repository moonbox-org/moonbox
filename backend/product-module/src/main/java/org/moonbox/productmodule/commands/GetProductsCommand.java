package org.moonbox.productmodule.commands;

import lombok.extern.slf4j.Slf4j;
import org.moonbox.productmodule.models.Product;
import org.moonbox.productmodule.services.ProductService;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Scope("prototype")
public class GetProductsCommand {

    /* ----- PARAMETERS ----- */


    private final ProductService productService;


    /* ----- CONSTRUCTOR ----- */


    public GetProductsCommand(ProductService productService) {
        this.productService = productService;
    }


    /* ----- METHODS ----- */


    public ResponseEntity<Page<Product>> execute() {
        log.info("START execute() of GetProductsCommand");

        Page<Product> output = Page.empty();

        return ResponseEntity.ok(output);
    }

}
