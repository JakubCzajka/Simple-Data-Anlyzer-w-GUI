package pl.polsl.model.exceptions;

/**
 * Class for the exception thrown when user tries to open dataset with recurrent field names.
 *
 * @author Jakub Czajka
 * @version 1.1
 * @deprecated Replaced by WrongDatasetFileException.
 */
public class RedundantFieldNames extends Exception {
    /**
     * Class constructor.
     *
     * @param fileName Name of the file with flawed dataset.
     */
    public RedundantFieldNames(String fileName) {
        super("File: "+fileName+" contains invalid dataset - field names are redundant.");
    }
}
