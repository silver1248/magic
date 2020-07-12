package org.sweatshop.api;

public enum CardColor {
    RED, GREEN, WHITE, BLUE, BLACK, COLORLESS;

    public static CardColor of(String color) {
        return CardColor.valueOf(color.toUpperCase());
    }
}
