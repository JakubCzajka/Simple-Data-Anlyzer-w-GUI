package pl.polsl.model.exceptions;

/**
 * Class for an exception thrown when user tries to access field that doesn't exist in dataset.
 *
 * @author Jakub Czajka
 * @version 1.1
 */
public class NoFieldFoundException extends Exception {
    /**
     * @param fieldName Name of the non-existent field that user tried to access.
     */
    public NoFieldFoundException(String fieldName) {
       super("No field with name " + fieldName + " was found in the dataset.");
    }
}
