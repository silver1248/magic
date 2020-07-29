package org.sweatshop.health;

import org.sweatshop.api.CardName;
import com.codahale.metrics.health.HealthCheck;
import io.vavr.collection.HashMap;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper=false)
public class MagicHealthCheck extends HealthCheck {
    HashMap<String, CardName> cardNames;

    @Override
    protected Result check() throws Exception {
        if (cardNames.length() >= 1) {
            return Result.healthy();
        } else {
            return Result.unhealthy("cardNames has no elements also this is a bad health check but I don't have a better one");
        }
    }

}
