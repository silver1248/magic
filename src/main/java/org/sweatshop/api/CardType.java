package org.sweatshop.api;

import io.vavr.collection.Set;

public enum CardType {
    ARTIFACT, CREATURE, ENCHANTMENT, INSTANT, SORCERY;

    public static CardType of(String type) {
        return CardType.valueOf(type.toUpperCase());
    }

    public static Set<CardType> of(Set<String> types) {
        return types.map(x -> CardType.of(x));
    }
}
