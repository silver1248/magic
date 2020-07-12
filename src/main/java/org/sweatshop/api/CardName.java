package org.sweatshop.api;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.vavr.collection.Set;
import lombok.Value;

@Value
public class CardName {
    String name;
    CardColor color;
    Set<CardType> types;

    @JsonCreator
    public CardName(
            @JsonProperty("name") String name, 
            @JsonProperty("color") String color,
            @JsonProperty("type") Set<String> types)
    {
        this.name = name;
        this.color = CardColor.of(color);
        this.types = CardType.of(types);
    }
}
