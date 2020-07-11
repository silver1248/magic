package org.sweatshop.resources;

import com.codahale.metrics.annotation.Timed;

import io.vavr.collection.List;
import lombok.Value;
import lombok.experimental.NonFinal;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.sweatshop.api.CardName;

@Value
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class MagicResources {
    List<String> people = List.of("Xavier", "Sarah", "George", "Zoe");
    @NonFinal static List<CardName> cardNames = List.of(new CardName("Lightning-Bolt", "Red", "Instant"), new CardName("Lava-Spike", "Red", "Sorcery")
            , new CardName("Price-of-Progress", "Red", "Instant"), new CardName("Vexing-Devil", "Red", "Creature"));

    @javax.ws.rs.Path("people")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Timed
    public List<String> peopleNames() {
        return people;
    }

    @javax.ws.rs.Path("people/{person}")
    @Produces(MediaType.APPLICATION_JSON)
    @PUT
    @Timed
    public List<String> addPerson(@PathParam("person") String person) {
        return people.append(person);
    }

    @javax.ws.rs.Path("people/{person}")
    @Produces(MediaType.APPLICATION_JSON)
    @DELETE
    @Timed
    public List<String> removePerson(@PathParam("person") String person) {
        return people.remove(person);
    }

    @javax.ws.rs.Path("card-names")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Timed
    public List<CardName> cardNames() {
        return cardNames;
    }

    @javax.ws.rs.Path("card-names/{card}")
    @Produces(MediaType.APPLICATION_JSON)
    @PUT
    @Timed
    public List<CardName> addCardName(@PathParam("card") String name) {
//        return cardNames.append(name);
        return null;
    }

    @javax.ws.rs.Path("card-names/{card}")
    @Produces(MediaType.APPLICATION_JSON)
    @DELETE
    @Timed
    public List<CardName> removeCardName(@PathParam("card") String name) {
        for (int i = 0; i < cardNames.length() - 1; i++) {
            if (cardNames.get(i).getName() == name) {
                cardNames = cardNames.remove(cardNames.get(i));
                break;
            }
        }
        return cardNames;
    }

    @javax.ws.rs.Path("card-names/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Timed
    public List<CardName> cardInstances(@PathParam("id") int id) {
        return cardNames;
    }

    @javax.ws.rs.Path("card-names/")
    @Produces(MediaType.APPLICATION_JSON)
    @PUT
    @Timed
    public List<CardName> addCardInstance(CardName cardName) {
        return cardNames.append(cardName);
    }
}
