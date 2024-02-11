package com.assessment.ECommerce.Service;

import com.assessment.ECommerce.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAllProduct();
    Product createProduct(Product product);
    Product getProductById(Long productId);
    Product updateProduct(Long productId,Product product);
    Product deleteProductById(Long productId);
    boolean applyDiscount(Long productId,double discount);
    boolean applyTax(Long productId,double taxs);
}
