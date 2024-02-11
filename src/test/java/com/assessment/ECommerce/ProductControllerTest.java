package com.assessment.ECommerce;

import com.assessment.ECommerce.Controller.ProductController;
import com.assessment.ECommerce.Service.ProductService;
import com.assessment.ECommerce.model.Product;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @Test
    public void testCreateProduct(){
        Product product =new Product(1L,"y1","engine",897.7,2);
        when(productService.createProduct(product))
                .thenReturn(product);
        ResponseEntity<Product> responseEntity = productController.createProduct(product);

        assertEquals(HttpStatus.CREATED,responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(product,responseEntity.getBody());
    }

    @Test
    public void testReadProduct() {
        long productId = 1;
        Product product = new Product(productId, "KTM", "Horn", 2023.0, 10);
        when(productService.getProductById(productId))
                .thenReturn(product);

        ResponseEntity<?> response = productController.getProductById(productId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(product, response.getBody());
    }

    @Test
    public void testUpdateProduct() {
        long productId = 1;
        Product product = new Product(productId, "KTM", "Horn", 2023.0, 10);
        when(productService.updateProduct(productId,product)).thenReturn(product);

        ResponseEntity<?> response = productController.updateProduct(productId,product);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(product, response.getBody());
    }

    @Test
    void testDeleteProduct() {
        long productId = 1L;
        when(productService.deleteProductById(productId)).thenReturn(null);

        ResponseEntity<Void> response = productController.deleteProduct(productId);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
    }
