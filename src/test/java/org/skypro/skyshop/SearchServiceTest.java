package org.skypro.skyshop;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.search.SearchResult;
import org.skypro.skyshop.service.SearchService;
import org.skypro.skyshop.service.StorageService;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SearchServiceTest {

    @Mock
    private StorageService storageService;

    @InjectMocks
    private SearchService searchService;

    @Test
    void search_WhenNoObjectsInStorage_ReturnsEmptyList() {

        when(storageService.getAllSearchables()).thenReturn(Collections.emptyList());

        Collection<SearchResult> results = searchService.search("молоко");

        assertTrue(results.isEmpty());
        verify(storageService).getAllSearchables();
    }

    @Test
    void search_WhenNoMatchingObjects_ReturnsEmptyList() {

        SimpleProduct product = new SimpleProduct(UUID.randomUUID(), "Хлеб", 40);
        Article article = new Article(UUID.randomUUID(), "Выбор сыра", "Как выбрать хороший сыр");

        when(storageService.getAllSearchables()).thenReturn(Arrays.asList(product, article));

        Collection<SearchResult> results = searchService.search("молоко");

        assertTrue(results.isEmpty());
        verify(storageService).getAllSearchables();
    }

    @Test
    void search_WhenMatchingObjectsExists_ReturnsResults() {

        SimpleProduct milk = new SimpleProduct(UUID.randomUUID(), "Молоко", 80);
        Article article = new Article(UUID.randomUUID(), "Выбор молоко и молочных продуктов", "Статья о молоке");


        when(storageService.getAllSearchables()).thenReturn(Arrays.asList(milk, article));

        Collection<SearchResult> results = searchService.search("молоко");

        assertEquals(2, results.size());
        assertTrue(results.stream().anyMatch(r -> r.getName().equals("Молоко")));
        assertTrue(results.stream().anyMatch(r -> r.getName().equals("Выбор молоко и молочных продуктов")));
        verify(storageService).getAllSearchables();
    }

    @Test
    void search_WithEmptyPattern_ReturnsEmptyList() {

        Collection<SearchResult> results = searchService.search("");

        assertTrue(results.isEmpty());
        verifyNoInteractions(storageService);
    }

    @Test
    void search_WithNullPattern_ReturnsEmptyList() {

        Collection<SearchResult> results = searchService.search(null);

        assertTrue(results.isEmpty());
        verifyNoInteractions(storageService);
    }

    @Test
    void search_InCaseInsensitive_ReturnResults() {

        SimpleProduct product = new SimpleProduct(UUID.randomUUID(), "Молоко", 80);
        when(storageService.getAllSearchables()).thenReturn(Collections.singletonList(product));

        Collection<SearchResult> results = searchService.search("МОЛОКО");

        assertEquals(1, results.size());
        verify(storageService).getAllSearchables();
    }
}
