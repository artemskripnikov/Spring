package org.skypro.skyshop.model.product;

import java.util.UUID;

public class DiscountedProduct extends Product {
    private final int basePrice;
    private final int discountPercent;

    public DiscountedProduct(UUID id, String name, int basePrice, int discountPercent) {
        super(id, name);
        this.basePrice = basePrice;
        this.discountPercent = discountPercent;
    }

    @Override
    public int getPriceOfProduct() {
        return basePrice * (100 - discountPercent) / 100;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public String toString() {
        return getName() + ": " + getPriceOfProduct() + " (" + discountPercent + "%)";
    }

    public int getBasePrice() {
        return basePrice;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }
}