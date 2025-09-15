package org.skypro.skyshop.service;

import org.skypro.skyshop.NoSuchProductException;
import org.skypro.skyshop.model.basket.BasketItem;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.basket.UserBasket;
import org.skypro.skyshop.model.product.Product;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BasketService {
    private final ProductBasket productBasket;
    private final StorageService storageService;

    public BasketService(ProductBasket productBasket, StorageService storageService) {
        this.productBasket = productBasket;
        this.storageService = storageService;
    }

    public void addProduct(UUID id) {
        try {
            Product product = storageService.getProductByIdOrThrow(id);
            productBasket.addProduct(id);
        } catch (NoSuchProductException e) {
            throw new NoSuchProductException("Не удалось добавить продукт в корзину: " + e.getMessage(), e);
        }
    }

    public UserBasket getUserBasket() {
        Map<UUID, Integer> basketItems = productBasket.getItems();

        List<BasketItem> items = basketItems.entrySet().stream()
                .map(entry -> {
                    Product product = storageService.getProductByIdOrThrow(entry.getKey());
                    return new BasketItem(product, entry.getValue());
                })
                .collect(Collectors.toList());

        return new UserBasket(items);
    }
}
