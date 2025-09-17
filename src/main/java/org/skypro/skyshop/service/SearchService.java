package org.skypro.skyshop.service;

import org.skypro.skyshop.model.search.SearchResult;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
public class SearchService {
    private final StorageService storageService;

    public SearchService(StorageService storageService) {
        this.storageService = storageService;
    }



    public Collection<SearchResult> search(String pattern) {
        if (pattern == null || pattern.trim().isEmpty()) {
            return Collections.emptyList();
        }

        String lowerPattern = pattern.toLowerCase();

        return storageService.getAllSearchables().stream()
                .filter(item -> {
                    String searchTerm = item.getSearchTerm().toLowerCase();

                    return searchTerm.contains(lowerPattern);
                })
                .map(SearchResult::fromSearchable)
                .collect(Collectors.toList());
    }
}
