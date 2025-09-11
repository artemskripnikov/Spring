package org.skypro.skyshop;

import jakarta.el.BeanNameResolver;
import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.DiscountedProduct;
import org.skypro.skyshop.model.product.FixPriceProduct;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.service.StorageService;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collection;
import java.util.UUID;

@SpringBootApplication
public class SkyshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkyshopApplication.class, args);

	}
}


