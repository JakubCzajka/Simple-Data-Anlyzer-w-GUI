package pl.polsl.model.basicmodel;

import pl.polsl.model.DataSet;
import pl.polsl.model.Statistics;
import pl.polsl.model.exceptions.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.Vector;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Basic implementation od DataSet interface.
 *
 * @author Jakub Czajka
 * @version 1.6
 */
public final class BasicDataSet implements DataSet {
    /**
     * Vector of Fields that contain numeric data.
     *
     * @see Field
     */
    private Vector<Field<Double>> dataFields;
    /**
     * Field containing records' timestamps
     *
     * @see Field
     */
    private Field<Instant> timeStamps;

    /**
     * Boolean indicating status of dataset.
     */
    private boolean opened;

    /**
     * Simple class constructor.
     */
    public BasicDataSet() {
        this.dataFields = new Vector<>();
        this.timeStamps = new Field<>("");
        this.opened = false;
    }

    @Override
    public String openCSV(String fileName, boolean embeddedLabels, String delimiter, int timeStampIndex) throws IOException, DatasetFileOpenException {
        this.opened = false;
        this.dataFields = new Vector<>();
        this.timeStamps = new Field<>("");
        if(delimiter == null)
            throw new DatasetFileOpenException("The delimiter parameter was null.");
        if(timeStampIndex<=0)
            throw new DatasetFileOpenException("The timestamp index parameter was equal or less than zero.");
        int numberOfMissingValues = 0;
        try {
            String line = null;
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            boolean fieldNamesRead = false;


            while ((line = br.readLine()) != null) {
                if (!fieldNamesRead && embeddedLabels) {
                    String[] fieldNames = line.split(delimiter, -1);
                    for (int fieldIndex = 0; fieldIndex < fieldNames.length; fieldIndex++) {
                        if (fieldNames[fieldIndex].compareTo("") == 0) {
                            numberOfMissingValues += 1;
                            fieldNames[fieldIndex] = new String("Field" + fieldIndex);
                        }
                    }
                    List<String> fieldNamesList = Arrays.asList(fieldNames);
                    Set<String> fieldNamesSet = new HashSet<String>(fieldNamesList);
                    if (fieldNamesList.size() != fieldNamesSet.size())
                        throw new DatasetFileOpenException("File: " + fileName + " contains invalid dataset - field names are redundant.");

                    if (timeStampIndex > fieldNames.length)
                        throw new DatasetFileOpenException("File: " + fileName + " contains " + fieldNames.length + " fields, which is less than timestamps field index: " + timeStampIndex);

                    for (int fieldIndex = 0; fieldIndex < fieldNames.length; fieldIndex++) {
                        if ((fieldIndex + 1) != timeStampIndex)
                            this.dataFields.add(new Field<>(fieldNames[fieldIndex]));
                        else
                            this.timeStamps = new Field<>(fieldNames[fieldIndex]);
                    }

                    fieldNamesRead = true;
                } else {

                    String[] record = line.split(delimiter, -1);
                    //noinspection ConstantConditions
                    if (!fieldNamesRead && !embeddedLabels) {
                        for (int fieldIndex = 0; fieldIndex < record.length; fieldIndex++) {
                            if ((fieldIndex + 1) != timeStampIndex)
                                this.dataFields.add(new Field<>(new String("Field" + fieldIndex)));
                            else
                                this.timeStamps = new Field<>(new String("Field" + fieldIndex));
                        }
                        fieldNamesRead = true;
                    }
                    if (record.length != this.dataFields.size() + 1) {
                        this.dataFields = new Vector<>();
                        this.timeStamps = new Field<>("");
                        throw new DatasetFileOpenException("File: " + fileName + " contains invalid dataset - number of fields across records is inconsistent.");
                    }
                    for (int fieldIndex = 0; fieldIndex < record.length; fieldIndex++) {
                        if ((fieldIndex + 1) != timeStampIndex)
                            if (fieldIndex < timeStampIndex)
                                try {
                                    this.dataFields.elementAt(fieldIndex).addValue(Double.valueOf(record[fieldIndex]));
                                } catch (NumberFormatException e) {
                                    numberOfMissingValues += 1;
                                    this.dataFields.elementAt(fieldIndex).addValue(Double.NaN);
                                }
                            else
                                try {
                                    this.dataFields.elementAt(fieldIndex - 1).addValue(Double.valueOf(record[fieldIndex]));
                                } catch (NumberFormatException e) {
                                    numberOfMissingValues += 1;
                                    this.dataFields.elementAt(fieldIndex - 1).addValue(Double.NaN);
                                }
                        else
                            try {
                                this.timeStamps.addValue(Instant.parse(record[fieldIndex]));
                            } catch (DateTimeParseException e) {
                                numberOfMissingValues += 1;
                                this.timeStamps.addValue(Instant.MIN);
                            }
                    }
                }

            }
        } catch (IOException e) {
            this.dataFields = new Vector<>();
            this.timeStamps = new Field<>("");
            throw e;
        }
        this.opened = true;
        return "Dataset successfully opened: " + fileName + "\nNumber of missing values: " + numberOfMissingValues;

    }

    @Override
    public boolean isOpen() {
        return opened;
    }

    @Override
    public Vector<String> getFieldNames() throws DataSetNotOpenedException {
        if (!opened)
            throw new DataSetNotOpenedException();

        Vector<String> result  = new Vector<>();

        for(Field<Double> dataField : dataFields){
            result.add(dataField.getName());
        }

        return result;
    }

    @Override
    public Vector<Double> getFieldCopy(String fieldName) throws NoFieldFoundException, DataSetNotOpenedException {
        if (!opened)
            throw new DataSetNotOpenedException();

        List<Field<Double>> filteredFields = dataFields
                .stream()
                .filter(f -> f.getName().compareTo(fieldName) == 0)
                .collect(Collectors.toList());

        if(filteredFields.size()==0)
            throw new NoFieldFoundException(fieldName);

        return filteredFields.get(0).copyValues();
    }

    @Override
    public Vector<Instant> getTimeStampsCopy() throws DataSetNotOpenedException {
        if (!opened)
            throw new DataSetNotOpenedException();
        return timeStamps.copyValues();
    }



}
