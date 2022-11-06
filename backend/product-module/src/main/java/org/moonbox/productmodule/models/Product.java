package org.moonbox.productmodule.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collation = "products")
public class Product {

    @Id
    private String id;

}
