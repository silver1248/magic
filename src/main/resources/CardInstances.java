import org.sweatshop.resources.MagicResources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.vavr.collection.List;
import lombok.Value;

@Value
public class CardInstances {
    String name;
    int condition;
    int id

    @JsonCreator
    public CardName(
            @JsonProperty("name") String name, 
            @JsonProperty("condition") int condition,
            @JsonProperty("id") int id
            {
                this.name = name;
                this.condition = condition;
                this.id = id;
            }
}
