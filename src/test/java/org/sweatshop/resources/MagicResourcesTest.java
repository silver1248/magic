package org.sweatshop.resources;

import static org.sweatshop.resources.MagicResources.createMapTuple;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import java.net.URI;
import java.util.NoSuchElementException;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.sweatshop.api.CardInstance;
import org.sweatshop.api.CardInstanceLessId;
import org.sweatshop.api.CardName;
import org.sweatshop.api.EndpointException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.vavr.Tuple2;
import io.vavr.collection.HashMap;
import io.vavr.collection.HashSet;
import io.vavr.collection.List;
import io.vavr.collection.Set;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MagicResourcesTest {
    HashMap<String, CardName> cardNames = HashMap.ofEntries(
            createMapTuple("Lightning-Bolt", "Red", HashSet.of("Instant"))
            , createMapTuple("Lava-Spike", "Red", HashSet.of("Sorcery"))
            , createMapTuple("Price-of-Progress", "Red", HashSet.of("Instant"))
            , createMapTuple("Vexing-Devil", "Red", HashSet.of("Creature")));



    @Test(dataProvider = "addCardInstanceTestDP")
    public void addCardInstanceTest(CardInstanceLessId noID, Response expected, Exception expectedE) throws EndpointException {
        MagicResources mr = new MagicResources(List.of("Xavier", "Sarah", "George", "Zoe")
                , cardNames
                , HashMap.empty());
        try {
            Response actual = mr.addCardInstance(noID);
            assertEquals(actual.getStatus(), expected.getStatus());
            assertEquals(actual.getEntity(), expected.getEntity());
            assertEquals(actual.getHeaderString("Location"), expected.getHeaderString("Location"));
            assertNull(expectedE);
            return;
        } catch (Exception e) {
            log.error("problem ", e);
            assertEquals(e.getClass(), expectedE.getClass());
            assertEquals(e.getMessage(), expectedE.getMessage());
        }
    }

    @DataProvider
    Object[][] addCardInstanceTestDP() {
        return new Object[][] {
            {new CardInstanceLessId("Uro", 5), Response.status(Status.BAD_REQUEST).build(), new EndpointException(400, URI.create("/no-name")
                    , "There is no card name of the name you provided"
                    , "You asked for an instance of card name Uro but the available names are "+cardNames.keySet()
                    , HashMap.of("requested-card-name", "Uro",
                            "available-card-names", cardNames.keySet()))},
            {new CardInstanceLessId("Lightning-Bolt", 5), Response.created(URI.create("cardinstances/0")).build(), null}
        };
    }

    @Test(dataProvider = "addCardNameTestDP")
    public void addCardNameTest(String name, String cardName, String color, Set<String> types, int expectedStatus, Exception expectedE) {
        MagicResources mr = new MagicResources(List.of("Xavier", "Sarah", "George", "Zoe")
                , cardNames
                , HashMap.empty());
        try {
            CardName card = new CardName(cardName, color, types);
            assertEquals(mr.addCardName(name, card).getStatus(), expectedStatus);
            assertNull(expectedE);
            return;
        } catch (Exception e) {
            log.error("problem ", e);
            assertEquals(e.getClass(), expectedE.getClass());
            assertEquals(e.getMessage(), expectedE.getMessage());
        }
    }

    @DataProvider
    Object[][] addCardNameTestDP() {
        return new Object[][] {
            {"Lightning-Bolt", "Lightning-Bolt", "Red", HashSet.of("Instant"), 201, null},
            {"Lightning-Bolt", "Lightning-Bolt", "orange", HashSet.of("Instant"), 400, new IllegalArgumentException("No enum constant org.sweatshop.api.CardColor.ORANGE")},
            {null, null, null, null, 400, new NullPointerException()},
        };
    }

    @Test(dataProvider = "addPersonTestDP")
    public void addPersonTest(String name, List<String> expected) {
        MagicResources mr = new MagicResources(List.of("Xavier", "Sarah", "George", "Zoe")
                , cardNames
                , HashMap.empty());
        assertEquals(mr.addPerson(name), expected);
    }

    @DataProvider
    Object[][] addPersonTestDP() {
        return new Object[][] {
            {"door", List.of("Xavier", "Sarah", "George", "Zoe", "door")},
            {"flumph", List.of("Xavier", "Sarah", "George", "Zoe", "flumph")}
        };
    }

    @Test(dataProvider="getCardInstanceTestDP")
    public void getCardInstanceTest(int id, CardInstance expected, Exception expectedE) {
        MagicResources mr = new MagicResources(List.of("Xavier", "Sarah", "George", "Zoe")
                , cardNames
                , HashMap.of(0, new CardInstance("Lightning Bolt", 9, 0), 1, new CardInstance("Lava Spike", 7, 1)));
        try {
            assertEquals(mr.getCardInstance(id), expected);
            assertNull(expectedE);
            return;
        } catch (Exception e) {
            log.error("problem ", e);
            assertEquals(e.getClass(), expectedE.getClass());
            assertEquals(e.getMessage(), expectedE.getMessage());
        }
    }

    @DataProvider
    Object[][] getCardInstanceTestDP() {
        return new Object[][] {
            {1, new CardInstance("Lava Spike", 7, 1), null},
            {7, null, new NoSuchElementException("No value present")}
        };
    }

    @Test
    public void getCardInstancesTest() {
        MagicResources mr = new MagicResources(List.of("Xavier", "Sarah", "George", "Zoe")
                , cardNames
                , HashMap.of(0, new CardInstance("Lightning Bolt", 9, 0), 1, new CardInstance("Lava Spike", 7, 1)));
        assertEquals(mr.getCardInstances(), HashSet.of(0, 1));
    }

    @Test(dataProvider="getCardNameTestDP")
    public void getCardNameTest(String name, List<Tuple2<String,CardName>> expected, Exception expectedE) {
        MagicResources mr = new MagicResources(List.of("Xavier", "Sarah", "George", "Zoe")
                , cardNames
                , HashMap.of(0, new CardInstance("Lightning Bolt", 9, 0), 1, new CardInstance("Lava Spike", 7, 1)));
        try {
            assertEquals(mr.getCardName(name), expected);
            assertNull(expectedE);
            return;
        } catch (Exception e) {
            log.error("problem ", e);
            assertEquals(e.getClass(), expectedE.getClass());
            assertEquals(e.getMessage(), expectedE.getMessage());
        }
    }

    @DataProvider
    Object[][] getCardNameTestDP() {
        return new Object[][] {
            {"Lightning-Bolt", List.of(createMapTuple("Lightning-Bolt", "Red", HashSet.of("Instant"))), null},
            {"Uro", List.empty(), null}
        };
    }

    @Test
    public void getCardNamesTest() {
        MagicResources mr = new MagicResources(List.of("Xavier", "Sarah", "George", "Zoe")
                , cardNames
                , HashMap.of(0, new CardInstance("Lightning Bolt", 9, 0), 1, new CardInstance("Lava Spike", 7, 1)));
        assertEquals(mr.getCardNames(), cardNames.keySet());
    }

    @Test
    public void getCardsTest() {
        MagicResources mr = new MagicResources(List.of("Xavier", "Sarah", "George", "Zoe")
                , cardNames
                , HashMap.of(0, new CardInstance("Lightning Bolt", 9, 0), 1, new CardInstance("Lava Spike", 7, 1)));
        assertEquals(mr.getCards(), cardNames.toSet());
    }

    @Test
    public void getInstancesTest() {
        MagicResources mr = new MagicResources(List.of("Xavier", "Sarah", "George", "Zoe")
                , cardNames
                , HashMap.of(0, new CardInstance("Lightning Bolt", 9, 0), 1, new CardInstance("Lava Spike", 7, 1)));
        assertEquals(mr.getInstances(), HashMap.of(0, new CardInstance("Lightning Bolt", 9, 0), 1, new CardInstance("Lava Spike", 7, 1)).toSet());
    }
    //java.lang.AssertionError: expected [HashSet(0, 1, CardInstance(name=Lightning Bolt, condition=9, id=0), CardInstance(name=Lava Spike, condition=7, id=1))] but found [HashSet((0, CardInstance(name=Lightning Bolt, condition=9, id=0)), (1, CardInstance(name=Lava Spike, condition=7, id=1)))]


    @Test
    public void peopleNamesTest() {
        MagicResources mr = new MagicResources(List.of("Xavier", "Sarah", "George", "Zoe")
                , cardNames
                , HashMap.of(0, new CardInstance("Lightning Bolt", 9, 0), 1, new CardInstance("Lava Spike", 7, 1)));
        assertEquals(mr.peopleNames(), List.of("Xavier", "Sarah", "George", "Zoe"));
    }

    @Test(dataProvider="removeCardNameTestDP")
    public void removeCardNameTest(String name, Set<String> expected) {
        MagicResources mr = new MagicResources(List.of("Xavier", "Sarah", "George", "Zoe")
                , cardNames
                , HashMap.empty());
        Set<String> actual = mr.removeCardName(name);
        assertEquals(actual, expected);
    }

    @DataProvider
    Object[][] removeCardNameTestDP() {
        return new Object[][] {
            {"Lightning-Bolt", HashSet.of("Vexing-Devil", "Lava-Spike", "Price-of-Progress")},
            {"Uro", HashSet.of("Vexing-Devil", "Lightning-Bolt", "Lava-Spike", "Price-of-Progress")}
        };
    }

    @Test(dataProvider="removePersonTestDP")
    public void removePersonTest(String name, List<String> expected) {
        MagicResources mr = new MagicResources(List.of("Xavier", "Sarah", "George", "Zoe")
                , cardNames
                , HashMap.empty());
        assertEquals(mr.removePerson(name), expected);
    }

    @DataProvider
    Object[][] removePersonTestDP() {
        return new Object[][] {
            {"Xavier", List.of("Sarah", "George", "Zoe")},
            {"door", List.of("Xavier", "Sarah", "George", "Zoe")}
        };
    }
}
