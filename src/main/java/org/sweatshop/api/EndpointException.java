package org.sweatshop.api;

import java.net.URI;

import io.vavr.collection.Map;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper=false)
public class EndpointException extends Exception {
    int responseCode;
    Map<String, Object> responseMap;

    public EndpointException(int responseCode, URI type, String title, String detail, Map<String, Object> details) {
        this.responseCode = responseCode;
        this.responseMap = details.put("type", type).put("title", title).put("detail", detail);
    }
}
