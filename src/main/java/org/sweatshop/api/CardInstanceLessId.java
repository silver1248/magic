package org.sweatshop.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Value;

@Value
public class CardInstanceLessId {
    String name;
    int condition;

    @JsonCreator
    public CardInstanceLessId(
            @JsonProperty("name") String name, 
            @JsonProperty("condition") int condition)
    {
        this.name = name;
        this.condition = condition;
    }

    public CardInstance createCardInstance(int id) {
        return new CardInstance(name, condition, id);
    }
}
