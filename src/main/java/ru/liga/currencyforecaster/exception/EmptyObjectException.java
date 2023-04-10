package ru.liga.currencyforecaster.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EmptyObjectException extends RuntimeException {
    public EmptyObjectException(String message) {
        super(message);
    }
}
