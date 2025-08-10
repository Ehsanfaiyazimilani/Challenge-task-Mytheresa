package productapi.exception;

// Custom exception class for resource not found errors
// Extends RuntimeException, so it's an unchecked exception
public class ResourceNotFoundException extends RuntimeException {

    // Constructor that accepts a message describing the error
    public ResourceNotFoundException(String message) {
        // Call the superclass constructor with the message
        super(message);
    }
}
