package org.moonbox.productmodule.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "products")
public class Product {

    @Id
    private String id;
    private String productName;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdate;
    private

}
