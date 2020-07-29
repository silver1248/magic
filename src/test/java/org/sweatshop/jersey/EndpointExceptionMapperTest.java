package org.sweatshop.jersey;

import static org.sweatshop.resources.MagicResources.createMapTuple;
import static org.testng.Assert.assertEquals;

import java.net.URI;

import javax.ws.rs.core.Response;

import org.sweatshop.api.CardName;
import org.sweatshop.api.EndpointException;
import org.testng.annotations.Test;

import io.vavr.collection.HashMap;
import io.vavr.collection.HashSet;

public class EndpointExceptionMapperTest {
    HashMap<String, CardName> cardNames = HashMap.ofEntries(
            createMapTuple("Lightning-Bolt", "Red", HashSet.of("Instant"))
            , createMapTuple("Lava-Spike", "Red", HashSet.of("Sorcery")));

    @Test()
    public void toResponseTest() {
        Response eem = new EndpointExceptionMapper().toResponse(new EndpointException(400, URI.create("/no-name")
                , "There is no card name of the name you provided"
                , "You asked for an instance of card name Uro but the available names are "+cardNames.keySet()
                , HashMap.of("requested-card-name", "Uro",
                        "available-card-names", cardNames.keySet())));
        assertEquals(eem.getStatus(), 400);
    }
}
