package com.assessment.ECommerce.Service;

import com.assessment.ECommerce.Repository.ProductRepository;
import com.assessment.ECommerce.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;
    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product getProductById(Long productId) {
        return productRepository.findById(productId).get();
    }

    @Override
    public Product updateProduct(Long productId, Product product) {
        Product pr = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("No Product Found"));

        pr.setName(product.getName());
        pr.setDescription(product.getDescription());
        pr.setPrice(product.getPrice());
        pr.setQuantityAvailable(product.getQuantityAvailable());

        return productRepository.save(pr);
    }

    @Override
    public Product deleteProductById(Long productId) {
      productRepository.deleteById(productId);
        return null;
    }

    @Override
    public boolean applyDiscount(Long productId, double discount) {
       Product product = productRepository.findById(productId).orElse(null);

       if (product == null){
           return false;
       }
       double originalPrice = product.getPrice();
       System.out.println("The Original price of the product is :"+originalPrice);
       double discountPrice = originalPrice * (1 - (discount / 100));

       product.setPrice(discountPrice);
       System.out.println("After discount the price is : "+discountPrice);
       productRepository.save(product);
       return true;
    }

    @Override
    public boolean applyTax(Long productId, double tax) {
        Product product = productRepository.findById(productId).orElse(null);

        if (product == null){
            return false;
        }
        double originalPrice = product.getPrice();
        System.out.println("The Original price of the product is :"+originalPrice);
        double taxedPrice = originalPrice * (1 - (tax / 100));

        product.setPrice(taxedPrice);
        System.out.println("After applying tax the price is : "+taxedPrice);
        productRepository.save(product);
        return true;
    }

   /* public String applyDiscountOrTax(Long productId, double rate, boolean isDiscount) {
        Product product = getProductById(productId);
        if (product == null) {
            return "Product not found";
        }

        double price = product.getPrice();
        if (isDiscount) {
            price -= price * rate / 100; // Applying discount
        } else {
            price += price * rate / 100; // Applying tax
        }

        product.setPrice(price);
        updateProduct(product);
        return "Discount/Tax applied successfully. Updated price: " + price;
    }*/
}
