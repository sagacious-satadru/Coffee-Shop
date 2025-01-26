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
    private List<Product> productsList = new ArrayList<>(List.of(
            new Product(1, "Espresso", 2.50),
            new Product(2, "Chlorinde's Latte", 3.50),
            new Product(3, "Furina's Croissant deluxe", 2.00),
            new Product(4, "Chocolate Muffin", 2.25),
            new Product(5, "Navia's Americano", 2.75)
    ));


//    @ResponseBody
//    public String home()
//    {
//        return "Welcome to the Coffee Shop! Greetings from Peter Griffin! He says hola!";
//    }
    @Autowired
    private ProductService productService;

    @GetMapping("/") // this will be the default route for the ProductController class, i.e. http://localhost:8080/ -> our home route
    public String viewHomePage(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "menu";
    }

//    @RequestMapping("/") // this will be the default route for the ProductController class, i.e. http://localhost:8080/ -> our home route
//    public String listProducts(Model productListModel) {
//        try {
//            List<Product> products = productsList;
//            // Updated logging to reflect Thymeleaf template usage
//            System.out.println("Products loaded: " + products.size());
//            System.out.println("Rendering Thymeleaf template: menu.html");
//
//            productListModel.addAttribute("products", products);
//            return "menu";
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw e;
//        }
//    }
    @RequestMapping("/add")
    public String showProductForm(Model productAddFormModel) {
        productAddFormModel.addAttribute("product", new Product());
        return "add-new-product";  // This should exactly match your template name
    }

    @PostMapping("/addNewProduct")
    public String addProduct(Product product) {
        // Update to use service instead of list
        productService.saveProduct(product); // We'll be implementing a saveProduct method in the ProductService interface and ProductServiceImpl class
        return "redirect:/";
    }
    @GetMapping("/showNewProductForm")
    public String showNewProductForm(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "add-new-product";
    }
    @RequestMapping("/test")
    public String testJsp() {
        return "test";  // This will look for test.jsp
    }
    /* Note:

    model.addAttribute("products", productsList); -> adds productsList to the model with the name products, making it accessible in menu.jsp as ${products}.

    return "menu"; -> returns "menu", directing the View Resolver to locate menu.jsp in WEB-INF/jsp/.

    Important:  To avoid errors, don’t forget to import the Model interface from the org.springframework.ui package.
    * */

    @RequestMapping("/featured")
    public String getFeaturedProducts(Model model) {
        // Get only products priced above $2.50
        List<Product> featuredProducts = productsList.stream()
                .filter(p -> p.getPrice() > 2.50)
                .collect(Collectors.toList());

        model.addAttribute("products", featuredProducts);
        System.out.println("Rendering featured products view");
        return "menu";  // Reuse the same JSP
    }

    @RequestMapping("/details/{id}") // this is the route for displaying details of a specific product, based on the product id which was passed, i.e. http://localhost:8080/products/details/1
    @ResponseBody
    public String getProductDetailsById(@PathVariable int id)
    {
        for (Product product : productsList)
        {
            if (product.getId() == id)
            {
                return "<strong>Product details: </strong><br>" + "Product: " + product.getName() + ", ID: " + product.getId() + "<br>" + "Price: " + product.getPrice();
            }
        }
        return "Product not found! Sorry :(";
    }

}
