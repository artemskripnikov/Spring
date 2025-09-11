package org.skypro.skyshop.model.article;

import org.skypro.skyshop.model.search.Searchable;

import java.util.UUID;

public final class Article implements Searchable {
    private final UUID id;
    private final String title;
    private final String content;

    public Article(UUID id, String title, String content) {
        if (id == null) {
            throw new IllegalArgumentException("id не может быть пустым");
        }
        this.id = id;
        this.title = title;
        this.content = content;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getSearchTerm() {
        return title + " " + content;
    }

    @Override
    public String getContentType() {
        return "ARTICLE";
    }

    @Override
    public String getName() {
        return title;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return title + "\n" + content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return title.equalsIgnoreCase(article.title);
    }

    @Override
    public int hashCode() {
        return title.toLowerCase().hashCode();
    }

}
