package com.assessment.ECommerce.Controller;
import com.assessment.ECommerce.Exception.ProductNotCreatedException;
import com.assessment.ECommerce.Exception.ProductNotFoundException;
import com.assessment.ECommerce.Service.ProductService;
import com.assessment.ECommerce.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        try {
            Product createdProduct = productService.createProduct(product);
            System.out.println("The Created Product is : "+createdProduct);
            return  ResponseEntity.status(HttpStatus.CREATED)
                    .body(createdProduct);
        } catch (Exception e){
           throw new ProductNotCreatedException("Failed to Create Product: "+e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProduct();
        System.out.println("List of all Products are : "+products);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable Long productId) {
       try {
           Product product = productService.getProductById(productId);
           System.out.println("For productId: "+productId+" the product is: "+product);
           return new ResponseEntity<>(product, HttpStatus.OK);
       } catch (Exception e) {
           throw new ProductNotFoundException("No Product Found :"+ e.getMessage());
       }

    }

    @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable("productId") Long productId,
                                                  @RequestBody Product product){
       try {
           Product updatedProduct = productService.updateProduct(productId,product);
           System.out.println("The Updated product is : "+updatedProduct);
           return new ResponseEntity<>(updatedProduct, HttpStatus.ACCEPTED);
       } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                   .body("Failed to Update Product");
       }
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.deleteProductById(productId);
        System.out.println("Product deleted Successfully");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @PostMapping("/{productId}/apply-discount")
    public ResponseEntity<?> applyDiscount(@PathVariable Long productId, @RequestParam double discount) {
        boolean success = productService.applyDiscount(productId, discount);
        if (success) {
            return new ResponseEntity<>("Discount applied successfully ", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to apply discount, product not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{productId}/apply-tax")
    public ResponseEntity<?> applyTax(@PathVariable Long productId, @RequestParam double tax) {
        boolean success = productService.applyTax(productId, tax);
        if (success) {
            return new ResponseEntity<>("Tax applied successfully ", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to apply tax, product not found", HttpStatus.NOT_FOUND);
        }
    }
  /*  @PostMapping("/{productId}/apply")
    public ResponseEntity<?> applyDiscountOrTax(
            @PathVariable Long productId,
            @RequestParam double rate,
            @RequestParam boolean isDiscount
    ) {
        String result = productService.applyDiscountOrTax(productId, rate, isDiscount);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }*/
}
