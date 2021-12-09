package pl.polsl.model;

/**
 * Interface for providing dataset statistics.
 *
 * @author Jakub Czajka
 * @version 1.1
 */
public interface Statistics {
    /**
     * Method that returns field name.
     *
     * @return field name
     */
    public String getFieldName();

    /**
     * Method that returns median of all not-empty elements of selected field.
     *
     * @return median of all not-empty elements of selected field
     */
    public Double getMedian();

    /**
     * Method that returns mean of all not-empty elements of selected field.
     *
     * @return mean of all not-empty elements of selected field
     */
    public Double getMean();

    /**
     * Method that returns value of minimum element of all not-empty elements of selected field.
     *
     * @return value of minimum element of all not-empty elements of selected field
     */
    public Double getMinimum();

    /**
     * Method that returns value of maximum element of all not-empty elements of selected field.
     *
     * @return value of maximum element of all not-empty elements of selected field
     */
    public Double getMaximum();

    /**
     * Method that returns number of empty elements of selected field.
     *
     * @return number of empty elements of selected field
     */
    public int getNumberOfMissingValues();

}
