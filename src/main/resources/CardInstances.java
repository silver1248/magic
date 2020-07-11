import org.sweatshop.resources.MagicResources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.vavr.collection.List;
import lombok.Value;

@Value
public class CardInstances {
    public class CardName {
        String name;
        String condition;

        @JsonCreator
        public CardName(
                @JsonProperty("name") String name, 
                @JsonProperty("condition") int condition
        {
            this.name = name;
            this.condition = condition;
        }
}
