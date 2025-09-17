package org.skypro.skyshop;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.model.basket.BasketItem;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.basket.UserBasket;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.service.BasketService;
import org.skypro.skyshop.service.StorageService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BasketServiceTest {

    @Mock
    private ProductBasket productBasket;

    @Mock
    private StorageService storageService;

    @InjectMocks
    private BasketService basketService;

    @Test
    void addProduct_WhenProductNotFound_ThrowsException() {

        UUID productId = UUID.randomUUID();
        when(storageService.getProductByIdOrThrow(productId))
                .thenThrow(new NoSuchProductException("Продукт не найден"));

        assertThrows(NoSuchProductException.class, () -> {
            basketService.addProduct(productId);
        });

        verify(storageService).getProductByIdOrThrow(productId);
        verifyNoInteractions(productBasket);
    }

    @Test
    void addProduct_WhenProductExists_AddsToBasket() {

        UUID productId = UUID.randomUUID();
        SimpleProduct product = new SimpleProduct(productId, "Молоко", 80);

        when(storageService.getProductByIdOrThrow(productId)).thenReturn(product);

        basketService.addProduct(productId);

        verify(storageService).getProductByIdOrThrow(productId);
        verify(productBasket).addProduct(productId);
    }

    @Test
    void getUserBasket_WhenBasketEmpty_ReturnsEmptyBasket() {

        when(productBasket.getItems()).thenReturn(Collections.emptyMap());

        UserBasket result = basketService.getUserBasket();

        assertTrue(result.getItems().isEmpty());
        assertEquals(0, result.getTotal());
        verify(productBasket).getItems();
        verifyNoInteractions(storageService);
    }

    @Test
    void getUserBasket_WhenBasketHasItems_ReturnsCorrectBasket() {

        UUID productId1 = UUID.randomUUID();
        UUID productId2 = UUID.randomUUID();

        SimpleProduct milk = new SimpleProduct(productId1, "Молоко", 80);
        SimpleProduct bread = new SimpleProduct(productId2, "Хлеб", 40);

        Map<UUID, Integer> basketItems = new HashMap<>();
        basketItems.put(productId1, 2);
        basketItems.put(productId2, 1);

        when(productBasket.getItems()).thenReturn(basketItems);
        when(storageService.getProductByIdOrThrow(productId1)).thenReturn(milk);
        when(storageService.getProductByIdOrThrow(productId2)).thenReturn(bread);

        UserBasket result = basketService.getUserBasket();

        assertEquals(2, result.getItems().size());
        assertEquals(200, result.getTotal());

        Optional<BasketItem> milkItem = result.getItems().stream()
                .filter(item -> item.getProduct().getName().equals("Молоко"))
                .findFirst();
        assertTrue(milkItem.isPresent());
        assertEquals(2, milkItem.get().getQuantity());

        verify(productBasket).getItems();
        verify(storageService, times(2)).getProductByIdOrThrow(any());
    }

    @Test
    void getUserBasket_WhenProductNotFoundInStorage_ThrowsException() {

        UUID productId = UUID.randomUUID();
        Map<UUID, Integer> basketItems = Collections.singletonMap(productId, 1);

        when(productBasket.getItems()).thenReturn(basketItems);
        when(storageService.getProductByIdOrThrow(productId))
                .thenThrow(new NoSuchProductException("Продукт не найден"));

        assertThrows(NoSuchProductException.class, () -> {
            basketService.getUserBasket();
        });

        verify(productBasket).getItems();
        verify(storageService).getProductByIdOrThrow(productId);
    }
}
