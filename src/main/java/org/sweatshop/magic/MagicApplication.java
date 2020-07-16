package org.sweatshop.magic;

import org.sweatshop.jersey.EndpointExceptionMapper;
import org.sweatshop.resources.MagicResources;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
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
        bootstrap.getObjectMapper().registerModule(new VavrModule());
    }

    @Override
    public void run(MagicConfiguration configuration,
            Environment environment) {
        final MagicResources resource = new MagicResources();
        environment.jersey().register(resource);
        environment.jersey().register(new EndpointExceptionMapper());
    }
}