package org.sweatshop.health;

import static org.sweatshop.resources.MagicResources.createMapTuple;
import static org.testng.Assert.assertEquals;

import org.sweatshop.api.CardName;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.codahale.metrics.health.HealthCheck.Result;

import io.vavr.collection.HashMap;
import io.vavr.collection.HashSet;

public class MagicHealthCheckTest {
    HashMap<String, CardName> cardNames = HashMap.ofEntries(
            createMapTuple("Lightning-Bolt", "Red", HashSet.of("Instant"))
            , createMapTuple("Lava-Spike", "Red", HashSet.of("Sorcery"))
            , createMapTuple("Price-of-Progress", "Red", HashSet.of("Instant"))
            , createMapTuple("Vexing-Devil", "Red", HashSet.of("Creature")));

    @Test(expectedExceptions = NullPointerException.class)
    public void checkNullTest() throws Exception {
        MagicHealthCheck ma = new MagicHealthCheck(null);
        ma.check();
    }

    @Test(dataProvider = "checkTestDP")
    public void checkTest(HashMap<String, CardName> cards, Result expected) throws Exception {
        MagicHealthCheck ma = new MagicHealthCheck(cards);
        Result actual = ma.check();
        assertEquals(actual.isHealthy(), expected.isHealthy());
        assertEquals(actual.getDuration(), expected.getDuration());
        assertEquals(actual.getMessage(), expected.getMessage());
        assertEquals(actual.getError(), expected.getError());
    }

    @DataProvider
    Object[][] checkTestDP() {
        Result unhealthy = Result.unhealthy("cardNames has no elements also this is a bad health check but I don't have a better one");
        return new Object[][] {
            {HashMap.empty(), unhealthy},

            {cardNames, Result.healthy()},
            {HashMap.ofEntries(createMapTuple("Lightning-Bolt", "Red", HashSet.of("Instant")), createMapTuple("Lava-Spike", "red", HashSet.of("Instant"))), Result.healthy()},
            {HashMap.ofEntries(createMapTuple("Lightning-Bolt", "Red", HashSet.of("Instant"))), Result.healthy()},
        };
    }
}
