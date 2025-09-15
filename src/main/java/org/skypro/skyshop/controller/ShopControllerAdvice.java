package org.skypro.skyshop.controller;

import org.skypro.skyshop.NoSuchProductException;
import org.skypro.skyshop.model.ShopError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ShopControllerAdvice {

    @ExceptionHandler(NoSuchProductException.class)
    public ResponseEntity<ShopError> handleNoSuchProduct(NoSuchProductException e) {
        ShopError error = new ShopError(
                "PRODUCT_NOT_FOUND",
                e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ShopError> handleIllegalArgument(IllegalArgumentException e) {
        ShopError error = new ShopError(
                "INVALID_INPUT",
                e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
