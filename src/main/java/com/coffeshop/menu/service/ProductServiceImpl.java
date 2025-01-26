package com.coffeshop.menu.service;

import com.coffeshop.menu.model.Product;
import com.coffeshop.menu.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

        @Override
        public List<Product> getAllProducts() {
            return productRepository.findAll();
        }

        @Override
        public void saveProduct(Product product) {
               System.out.println("Saving product: " + product.getName() + " with price: " + product.getPrice());
                validateProduct(product);
                productRepository.save(product);
                System.out.println("Product saved successfully with ID: " + product.getId());
            }
        private void validateProduct(Product product) {
            if (product.getName() == null || product.getName().trim().length() < 2) {
                throw new IllegalArgumentException("Product name must be at least 2 characters long");
            }
            if (product.getPrice() <= 0) {
                throw new IllegalArgumentException("Price must be greater than 0");
            }
        }
    }


