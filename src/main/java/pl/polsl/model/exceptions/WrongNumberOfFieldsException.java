package pl.polsl.model.exceptions;

/**
 * Class for an exception thrown when user provides dataset with varying-length records.
 *
 * @author Jakub Czajka
 * @version 1.1
 * @deprecated Replaced by WrongDatasetFileException.
 */
public class WrongNumberOfFieldsException extends Exception {
    /**
     * Class constructor.
     *
     * @param fileName Name of the file with flawed dataset.
     */
    public WrongNumberOfFieldsException(String fileName) {
        super("File: " + fileName + " contains invalid dataset - number of fields across records is inconsistent.");
    }
}
