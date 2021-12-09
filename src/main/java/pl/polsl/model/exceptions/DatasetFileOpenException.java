package pl.polsl.model.exceptions;
/**
 * Class for an exception thrown when user provides file with incorrect dataset or incorrect parameters to open the file.
 *
 * @author Jakub Czajka
 * @version 1.1
 */
public class DatasetFileOpenException extends Exception{
    /**
     * Class constructor.
     *
     * @param message message to be passed by exception.
     */
    public DatasetFileOpenException(String message){super(message);};
}
