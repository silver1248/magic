import org.sweatshop.resources.MagicResources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.vavr.collection.List;
import lombok.Value;

@Value
public class CardName {
    String name;
    String color;
    String type;

    @JsonCreator
    public CardName(
            @JsonProperty("name") String name, 
            @JsonProperty("color") String color,
            @JsonProperty("type") String type)
    {
        this.name = name;
        this.color = color;
        this.type = type;
    }

}
