package org.sweatshop.magic;

import org.sweatshop.resources.MagicResources;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class MagicApplication extends Application<MagicConfiguration> {

    public static void main(String[] args) throws Exception {
        new MagicApplication().run(args);
    }

    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<MagicConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(MagicConfiguration configuration,
                    Environment environment) {
        final MagicResources resource = new MagicResources(
            configuration.getTemplate(),
            configuration.getDefaultName()
        );
        environment.jersey().register(resource);
    }

}
