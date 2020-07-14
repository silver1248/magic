package org.sweatshop.resources;

import com.codahale.metrics.annotation.Timed;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.HashMap;
import io.vavr.collection.HashSet;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.collection.Set;
import lombok.Value;
import lombok.experimental.NonFinal;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.sweatshop.api.CardInstance;
import org.sweatshop.api.CardInstanceLessId;
import org.sweatshop.api.CardName;

@Value
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class MagicResources {
    @NonFinal static List<String> people = List.of("Xavier", "Sarah", "George", "Zoe");
    @NonFinal static Map<String, CardName> cardNames = HashMap.ofEntries(
            createMapTuple("Lightning-Bolt", "Red", HashSet.of("Instant"))
            , createMapTuple("Lava-Spike", "Red", HashSet.of("Sorcery"))
            , createMapTuple("Price-of-Progress", "Red", HashSet.of("Instant"))
            , createMapTuple("Vexing-Devil", "Red", HashSet.of("Creature")));
    @NonFinal static Map<Integer, CardInstance> cardInstances = HashMap.empty();

    public static Tuple2<String, CardName> createMapTuple(String name, String color, Set<String> type) {
        return Tuple.of(name, new CardName(name, color, type));
    }

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

    @javax.ws.rs.Path("card-names/")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Timed
    public Set<String> getCardNames() {
        return cardNames.keySet();
    }

    @javax.ws.rs.Path("card-names/{card}")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Timed
    public List<Tuple2<String,CardName>> getCardName(@PathParam("card") String name) {
        List<Tuple2<String,CardName>> allCardsWithName = cardNames.filter(x -> x._2().getName().equals(name)).toList();
        return allCardsWithName;
    }

    @javax.ws.rs.Path("card-names/{card}")
    @Produces(MediaType.APPLICATION_JSON)
    @PUT
    @Timed
    public Set<String> addCardName(@PathParam("card") String name, CardName cardName) {
        if (null != cardName) {
            cardNames = cardNames.put(name, cardName);
        }
        return getCardNames();
    }

    @javax.ws.rs.Path("card-names/{card}")
    @Produces(MediaType.APPLICATION_JSON)
    @DELETE
    @Timed
    public Set<String> removeCardName(@PathParam("card") String name) {
        cardNames = cardNames.remove(name);
        return getCardNames();
    }

    @javax.ws.rs.Path("cardinstances/")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Timed
    public Set<Integer> getCardInstances() {
        return cardInstances.keySet();
    }

    @javax.ws.rs.Path("cardinstances/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Timed
    public CardInstance getCardInstance(@PathParam("id") int id) {
        return cardInstances.get(id).get();
    }

    @javax.ws.rs.Path("cardinstances/new")
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Timed
    public CardInstance addCardInstance(CardInstanceLessId cardInstanceLessId) {
        int nextVal = cardInstances.keySet().max().getOrElse(-1) + 1;
        CardInstance cardInstance = cardInstanceLessId.createCardInstance(nextVal);
        cardInstances = cardInstances.put(nextVal, cardInstance);
        return cardInstance;
    }
}
