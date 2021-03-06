package org.sweatshop.api;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Value;

@Value
public class CardInstance {
    String name;
    int condition;
    int id;

    @JsonCreator
    public CardInstance(
            @JsonProperty("name") String name, 
            @JsonProperty("condition") int condition,
            @JsonProperty("id") int id)
    {
        this.name = name;
        this.condition = condition;
        this.id = id;
    }
}
