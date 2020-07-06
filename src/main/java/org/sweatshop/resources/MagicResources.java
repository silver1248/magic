package org.sweatshop.resources;

import com.codahale.metrics.annotation.Timed;

import lombok.Value;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.sweatshop.api.MagicSaying;

import java.util.Optional;

@Value
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class MagicResources {
    String template;
    String defaultName;

    @Path("hello-world")
    @GET
    @Timed
    public MagicSaying sayHello(@QueryParam("name") Optional<String> name) {
        final String value = String.format(template, name.orElse(defaultName));
        return new MagicSaying(2, value);
    }
}
