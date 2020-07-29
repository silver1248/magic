package org.sweatshop.resources;

import java.net.URI;

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
import org.sweatshop.api.EndpointException;

import com.codahale.metrics.annotation.Timed;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.collection.Set;
import lombok.Value;
import lombok.experimental.NonFinal;

@Value
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class MagicResources {
    @NonFinal List<String> people;
    @NonFinal Map<String, CardName> cardNames;
    @NonFinal Map<Integer, CardInstance> cardInstances;

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

    @javax.ws.rs.Path("card-names/all")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Timed
    public Set<Tuple2<String, CardName>> getCards() {
        return cardNames.toSet();
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
    public Response addCardName(@PathParam("card") String name, CardName cardName) {
        cardNames = cardNames.put(name, cardName);
        return Response.created(URI.create("card-name/")).build();
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

    @javax.ws.rs.Path("cardinstances/all")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Timed
    public Set<Tuple2<Integer, CardInstance>> getInstances() {
        return cardInstances.toSet();
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
    public Response addCardInstance(CardInstanceLessId cardInstanceLessId) throws EndpointException {
        if (cardNames.keySet().contains(cardInstanceLessId.getName())) {
            int nextVal = cardInstances.keySet().max().getOrElse(-1) + 1;
            CardInstance cardInstance = cardInstanceLessId.createCardInstance(nextVal);
            cardInstances = cardInstances.put(nextVal, cardInstance);
            return Response.created(URI.create("cardinstances/" + nextVal)).build();
        } else {
            throw new EndpointException(400, URI.create("/no-name")
                    , "There is no card name of the name you provided"
                    , "You asked for an instance of card name "+cardInstanceLessId.getName()+" but the available names are "+cardNames.keySet()
                    , HashMap.of("requested-card-name", cardInstanceLessId.getName(),
                            "available-card-names", cardNames.keySet()));
        }
    }
}
