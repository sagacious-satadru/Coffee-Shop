package com.coffeshop.menu;

import com.coffeshop.menu.model.Product;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CoffeshopMenuApplication {

	public static void main(String[] args) {

		SpringApplication.run(CoffeshopMenuApplication.class, args);
		Product espresso = new Product(1, "Espresso", 2.99);
		Product croissant = new Product(2, "Croissant", 3.49);
		System.out.println("Product ID: " + espresso.getId() + " Name: " + espresso.getName() + " Price: " + espresso.getPrice());
		System.out.println("Product ID: " + croissant.getId() + " Name: " + croissant.getName() + " Price: " + croissant.getPrice());
	}

}
