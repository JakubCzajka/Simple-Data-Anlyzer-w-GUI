package pl.polsl.model.exceptions;

/**
 * Class for an exception thrown when user tries to access empty dataset.
 *
 * @author Jakub Czajka
 * @version 1.1
 */
public class DataSetNotOpenedException extends Exception {
    /**
     * Class constructor.
     */
    public DataSetNotOpenedException()
    {
        super("User tried to access empty dataset.");
    }
}
