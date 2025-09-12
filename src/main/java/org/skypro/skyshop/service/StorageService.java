package org.skypro.skyshop.service;
import java.util.stream.Stream;
import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.DiscountedProduct;
import org.skypro.skyshop.model.product.FixPriceProduct;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.search.Searchable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StorageService {
    private final Map<UUID, Product> products = new HashMap<>();
    private final Map<UUID, Article> articles = new HashMap<>();

    public Optional<Product> getProductById(UUID id) {
        return Optional.ofNullable(products.get(id));
    }

    public StorageService() {
        initializeTestData();
    }

    private void initializeTestData() {
        addProduct(new SimpleProduct(UUID.randomUUID(), "Молоко", 80));
        addProduct(new SimpleProduct(UUID.randomUUID(), "Хлеб", 40));
        addProduct(new DiscountedProduct(UUID.randomUUID(), "Сыр", 120, 15));
        addProduct(new FixPriceProduct(UUID.randomUUID(), "Соль"));
        addProduct(new SimpleProduct(UUID.randomUUID(), "Яйца", 50));

        addArticle(new Article(UUID.randomUUID(),
                "Выбор молочных продуктов",
                "Как выбрать качественное молоко, сыр и другие молочные продукты"));

        addArticle(new Article(UUID.randomUUID(),
                "Польза здорового питания",
                "Статья о преимуществах правильного питания и выборе продуктов"));
    }

    public Collection<Product> getAllProducts() {
        return Collections.unmodifiableCollection(products.values());
    }

    public Collection<Article> getAllArticles() {
        return Collections.unmodifiableCollection(articles.values());
    }


    public void addProduct(Product product) {
        if (product != null) {
            products.put(product.getId(), product);
        }
    }

    public void addArticle(Article article) {
        if (article != null) {
            articles.put(article.getId(), article);
        }
    }

    public Collection<Searchable> getAllSearchables() {
        return Stream.concat(
                products.values().stream(),
                articles.values().stream()
        ).collect(Collectors.toList());
    }
}
