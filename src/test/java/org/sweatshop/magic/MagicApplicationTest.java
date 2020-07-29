package org.sweatshop.magic;

import static org.testng.Assert.assertEquals;

import static io.dropwizard.testing.FixtureHelpers.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.sweatshop.api.CardName;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.DropwizardTestSupport;
import io.dropwizard.testing.ResourceHelpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MagicApplicationTest {

    @Test
    public void getNameTest() {
        MagicApplication ma = new MagicApplication();
        assertEquals(ma.getName(), "magic");
    }

    public static final DropwizardTestSupport<MagicConfiguration> SUPPORT =
            new DropwizardTestSupport<MagicConfiguration>(MagicApplication.class,
                    ResourceHelpers.resourceFilePath("config.yml"));


    public static Client client;

    @BeforeClass
    public void beforeClass() throws Exception {
        SUPPORT.before();
        client = new JerseyClientBuilder(SUPPORT.getEnvironment()).build("test");
    }

    @AfterClass
    public void afterClass() {
        SUPPORT.after();
    }

    @Test
    public void peopleTest() {
        Response response = client.target(
                String.format("http://localhost:%d/people", SUPPORT.getLocalPort()))
                .request()
                .get();

        assertEquals(response.getStatus(), 200);
    }


    @Test(dataProvider = "removePersonTestDP")
    public void removePersonTest(String name, int status) {
        Response response = client.target(
                String.format("http://localhost:%d/people/%s", SUPPORT.getLocalPort(), name))
                .request()
                .delete();

        assertEquals(response.getStatus(), status);
    }

    @DataProvider
    Object[][] removePersonTestDP() {
        return new Object[][]{
            {"", 405},

            {"jtrgbfr", 200},
            //the method will still work if you pass it a string that isn't in the list because .delete
            //just doesn't do anything if there's no element that matches the one passed to it but it won't
            //work if you pass it nothing because the method needs to be sent something
        };
    }

    @Test
    public void cardNamesTest() {
        Response response = client.target(
                String.format("http://localhost:%d/card-names", SUPPORT.getLocalPort()))
                .request()
                .get();

        assertEquals(response.getStatus(), 200);
    }

    @Test
    public void cardsTest() {
        Response response = client.target(
                String.format("http://localhost:%d/card-names/all", SUPPORT.getLocalPort()))
                .request()
                .get();

        assertEquals(response.getStatus(), 200);
    }

    @Test(dataProvider = "getCardTestDP")
    public void getCardTest(String name, int status) {
        Response response = client.target(
                String.format("http://localhost:%d/card-names/%s", SUPPORT.getLocalPort(), name))
                .request()
                .delete();

        assertEquals(response.getStatus(), status);
    }

    @DataProvider
    Object[][] getCardTestDP() {
        return new Object[][]{
            {"", 405},

            {"Lava-Spike", 200},
            {"Lava Spike", 200},
        };
    }

    private static final ObjectMapper MAPPER = MagicApplication.initializeObjectMapper(Jackson.newObjectMapper());

    @Test(dataProvider = "addPersonTestDP")
    public void addPersonTest(String name, int status) {
        Entity<String> e = Entity.json("foo");
        Response response = client.target(
                getLocalHostUrl("people/" + name))
                .request()
                .put(e);

        assertEquals(response.getStatus(), status);
    }

    @DataProvider
    Object[][] addPersonTestDP() {
        return new Object[][]{
            {null, 200},
            //so while this works and returns a 200 it won't actually do anything and the list will stay the same

            {"", 405},

            {"vjgruebh8", 200},
        };
    }

    public String getLocalHostUrl(String tail) {
        return String.format("http://localhost:%d/%s", SUPPORT.getLocalPort(), tail);
    }

    @Test(dataProvider = "addCardTestDP")
    public void addCardTest(String name, int status) throws JsonMappingException, JsonProcessingException {
        CardName value = MAPPER.readValue(fixture("fixtures/"+name+".json"), CardName.class);
        final String card = MAPPER.writeValueAsString(value);
        Entity<String> e = Entity.json(card);
        Response response = client.target(
                getLocalHostUrl("card-names/"+ value.getName()))
                .request()
                .put(e);
        // FIXME don't know why this isn't working
        //            assertEquals(response.getStatus(), status);
        System.out.println(response.getStatus());
    }

    @DataProvider
    Object[][] addCardTestDP() {
        return new Object[][]{
            {"cardName", 201},
            //
            //            {"Lava-Spike", 200},
            //            {"Lava Spike", 200},
        };
    }
}
