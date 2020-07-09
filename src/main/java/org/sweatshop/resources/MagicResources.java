package org.sweatshop.resources;

import com.codahale.metrics.annotation.Timed;

import io.vavr.collection.List;
import lombok.Value;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Value
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class MagicResources {
    String template;
    String defaultName;
    List<String> people = List.of("Xavier", "Sarah", "George", "Zoe");
    List<String> cards = List.of("Lightning-Bolt", "Chain-Lightning", "Rift-Bolt", "Lava-Spike", "Price-of-Progress");

    @javax.ws.rs.Path("people")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Timed
    public List<String> peopleNames() {
        return cards;
    }

    @javax.ws.rs.Path("card-names")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Timed
    public List<String> cardNames() {
        return cards;
    }

    @javax.ws.rs.Path("card-names/{card}")
    @Produces(MediaType.APPLICATION_JSON)
    @PUT
    @Timed
    public List<String> addCardName(@PathParam("card") String name) {
        return cards.append(name);
    }

    @javax.ws.rs.Path("card-names/{card}")
    @Produces(MediaType.APPLICATION_JSON)
    @DELETE
    @Timed
    public List<String> removeCardName(@PathParam("card") String name) {
        return cards.remove(name);
    }
}
