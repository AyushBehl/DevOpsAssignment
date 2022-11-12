package com.c4c.demo;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.List;
@Data
@Document
public class ProductSave {
    @Id
    private String productId;
    private String name;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    private String description;
    private String imgUrl;

    public List<ProductHistory> getProductHistory() {
        return productHistory;
    }

    public void setProductHistory(List<ProductHistory> productHistory) {
        this.productHistory = productHistory;
    }

    private List<ProductHistory> productHistory;

}
