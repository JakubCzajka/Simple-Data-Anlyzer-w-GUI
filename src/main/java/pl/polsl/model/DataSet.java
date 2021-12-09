package pl.polsl.model;


import pl.polsl.model.exceptions.*;

import java.io.IOException;
import java.time.Instant;
import java.util.Vector;

/**
 * Interface for datasets.
 *
 * @author Jakub Czajka
 * @version 1.12
 */
public interface DataSet {
    /**
     * Method that opens dataset based on parameters provided by user.
     *
     * On failure the dataset should be empty, even if it had data prior to calling this method.
     *
     * @param fileName dataset file name
     * @param embeddedLabels true if first row of the dataset contains field names
     * @param delimiter delimiter in the CSV file
     * @param timeStampIndex index of field containing timestamps
     * @return String with message after successfully opening specified dataset
     * @throws IOException if fails to read the dataset file
     * @throws DatasetFileOpenException if file contains improper dataset or wrong arguments were passed
     */
    String openCSV(String fileName, boolean embeddedLabels, String delimiter, int timeStampIndex) throws IOException, DatasetFileOpenException;

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean isOpen();

    Vector<String> getFieldNames() throws DataSetNotOpenedException;

    Vector<Double> getFieldCopy(String fieldName) throws NoFieldFoundException, DataSetNotOpenedException;

    Vector<Instant> getTimeStampsCopy() throws DataSetNotOpenedException;
}
