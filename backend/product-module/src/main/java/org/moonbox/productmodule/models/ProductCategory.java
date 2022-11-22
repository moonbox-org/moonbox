package org.moonbox.productmodule.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "product-categories")
public class ProductCategory {
}
