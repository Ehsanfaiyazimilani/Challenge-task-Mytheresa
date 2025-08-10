package productapi.exception;

// Import HTTP status codes from Spring
import org.springframework.http.HttpStatus;
// Import ResponseEntity to send HTTP responses
import org.springframework.http.ResponseEntity;
// Import Spring annotations for exception handling
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

// Marks this class as a global exception handler for all controllers
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle exceptions of type IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleBadRequest(IllegalArgumentException ex) {
        // Return a response with 400 Bad Request status and the exception message
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    // Handle exceptions of type ResourceNotFoundException
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleNotFound(ResourceNotFoundException ex) {
        // Return a response with 404 Not Found status and the exception message
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // Handle all other exceptions that are not caught by specific handlers
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        // Return a response with 500 Internal Server Error and a generic message
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error occurred.");
    }

    // Helper method to build the error response body and wrap it in ResponseEntity
    private ResponseEntity<Object> buildResponse(HttpStatus status, String message) {
        Map<String, Object> body = new HashMap<>();    // Create a map for JSON body
        body.put("timestamp", LocalDateTime.now());    // Current timestamp
        body.put("status", status.value());            // HTTP status code (e.g. 404)
        body.put("error", status.getReasonPhrase());  // Status description (e.g. Not Found)
        body.put("message", message);                   // Error message text
        return new ResponseEntity<>(body, status);     // Return ResponseEntity with body and status
    }
}
