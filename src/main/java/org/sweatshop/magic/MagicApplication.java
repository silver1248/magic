package org.sweatshop.magic;

import static org.sweatshop.resources.MagicResources.createMapTuple;
import org.sweatshop.jersey.EndpointExceptionMapper;
import org.sweatshop.resources.MagicResources;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.vavr.collection.HashMap;
import io.vavr.collection.HashSet;
import io.vavr.collection.List;
import io.vavr.jackson.datatype.VavrModule;
import lombok.Generated;

public class MagicApplication extends Application<MagicConfiguration> {

    @Generated
    public static void main(String[] args) throws Exception {
        new MagicApplication().run(args);
    }

    @Override
    public String getName() {
        return "magic";
    }

    @Override
    public void initialize(Bootstrap<MagicConfiguration> bootstrap) {
        initializeObjectMapper(bootstrap.getObjectMapper());
    }

    static ObjectMapper initializeObjectMapper(ObjectMapper om) {
        om.registerModule(new VavrModule());
        return om;
    }

    @Override
    public void run(MagicConfiguration configuration, Environment environment) {
        final MagicResources resource = new MagicResources(
                List.of("Xavier", "Sarah", "George", "Zoe")
                , HashMap.ofEntries(
                        createMapTuple("Lightning-Bolt", "Red", HashSet.of("Instant"))
                        , createMapTuple("Lava-Spike", "Red", HashSet.of("Sorcery"))
                        , createMapTuple("Price-of-Progress", "Red", HashSet.of("Instant"))
                        , createMapTuple("Vexing-Devil", "Red", HashSet.of("Creature")))
                , HashMap.empty());
        environment.jersey().register(resource);
        environment.jersey().register(new EndpointExceptionMapper());
    }
}