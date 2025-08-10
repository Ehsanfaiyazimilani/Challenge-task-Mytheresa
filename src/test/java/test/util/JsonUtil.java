package test.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
    // Create a reusable ObjectMapper instance for JSON serialization
    private static final ObjectMapper mapper = new ObjectMapper();

    // Converts any Java object to its JSON string representation
    public static String toJson(Object object) {
        try {
            // Use ObjectMapper to serialize the object into JSON string
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            // Wrap and rethrow any exceptions as unchecked runtime exceptions
            throw new RuntimeException(e);
        }
    }
}
