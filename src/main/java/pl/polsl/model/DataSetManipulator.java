package pl.polsl.model;

import pl.polsl.model.exceptions.DataSetNotOpenedException;
import pl.polsl.model.exceptions.NoFieldFoundException;

import java.time.Instant;
import java.util.Vector;

public interface DataSetManipulator {
    /**
     * Method that returns statistics for all fields in the dataset.
     *
     * @see Statistics
     *
     * @param from Beginning of the time period for the statistics default - Instant.MIN.
     * @param to End of the time period of the statistics default - Instant.MIN.
     * @return Vector of Statistics, each element represents different field
     * @throws DataSetNotOpenedException if user tries to get statistics before successfully opening a dataset
     */
    public Vector<Statistics> getStatistics(DataSet dataSet,Instant from, Instant to) throws DataSetNotOpenedException;

    /**
     * Method that returns statistics for selected field in the dataset.
     *
     * @see Statistics
     *
     * @param fieldName Name of the selected field.
     * @param from Beginning of the time period for the statistics default - Instant.MIN.
     * @param to End of the time period of the statistics default - Instant.MIN.
     * @return Statistics for selected field and time period
     * @throws NoFieldFoundException if there is no field with provided fieldName in the dataset
     * @throws DataSetNotOpenedException if user tries to get statistics before successfully opening a dataset
     */
    public Statistics getStatistics(DataSet dataSet, String fieldName, Instant from, Instant to) throws NoFieldFoundException, DataSetNotOpenedException;
}
