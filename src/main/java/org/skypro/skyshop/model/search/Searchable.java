package org.skypro.skyshop.model.search;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;

public interface Searchable extends Comparable<Searchable> {
    @JsonIgnore
    String getSearchTerm();

    @JsonIgnore
    String getContentType();

    String getName();

    UUID getId();

    @JsonIgnore
    default String getStringRepresentation() {
        return getName() + " - " + getContentType();
    }

    @Override
    default int compareTo(Searchable other) {
        int lengthCompare = Integer.compare(other.getName().length(), this.getName().length());
        if (lengthCompare != 0) {
            return lengthCompare;
        }
        return this.getName().compareTo(other.getName());
    }


}
