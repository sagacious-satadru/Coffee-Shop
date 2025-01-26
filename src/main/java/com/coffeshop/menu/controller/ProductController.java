package com.coffeshop.menu.controller;

import com.coffeshop.menu.model.Product;
import com.coffeshop.menu.service.ProductService;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
//@RequestMapping("/products") // This means all URLs start with http://localhost:8080/products/ /* This organizes all product-related requests at the class level. This means that all routes within the ProductController will automatically have /products prefixed to them. This setup is like creating a dedicated “products” section in the application. */
/* Adding @RequestMapping("/products") at the top of the ProductController class makes every route in the class begin with /products. For example, if we add a method within this class that includes@RequestMapping("/list"), the full URL to access it would be /products/list.
* */
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String viewHomePage(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "menu";
    }

    @GetMapping("/showNewProductForm")
    public String showNewProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "add-new-product";
    }

    @PostMapping("/addNewProduct")
    public String addProduct(@ModelAttribute("product") Product product) {
        productService.saveProduct(product);
        return "redirect:/";
    }

    @GetMapping("/featured")
    public String getFeaturedProducts(Model model) {
        // We can consider moving this logic to the service layer
        List<Product> featuredProducts = productService.getAllProducts().stream()
                .filter(p -> p.getPrice() > 2.50)
                .collect(Collectors.toList());
        model.addAttribute("products", featuredProducts);
        return "menu";
    }
}