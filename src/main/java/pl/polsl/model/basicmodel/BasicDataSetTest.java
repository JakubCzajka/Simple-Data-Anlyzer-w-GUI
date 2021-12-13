package pl.polsl.model.basicmodel;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import pl.polsl.model.Statistics;
import pl.polsl.model.exceptions.DataSetNotOpenedException;
import pl.polsl.model.exceptions.DatasetFileOpenException;
import pl.polsl.model.exceptions.NoFieldFoundException;


import java.io.IOException;
import java.time.Instant;
import java.util.Vector;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * Class containing unit tests for BasicDataSet class.
 *
 * @author Jakub Czajka
 * @version 1.1
 */
public class BasicDataSetTest {
    /**
     * BasicDataSet containing known dataset
     */
    static BasicDataSet knownDataSet;

    /**
     * Method that opens the known dataset.
     */
    @BeforeAll
    static void setUp(){
        knownDataSet = new BasicDataSet();
        try {
            knownDataSet.openCSV(".\\test\\resources\\knowndataset.csv",true,";",1);
        } catch (IOException | DatasetFileOpenException e) {
            e.printStackTrace();
        }
    }



    /**
     * Method that tests opening dataset out of a CSV file.
     *
     * @param filePath Path to dataset
     * @param embeddedLabels "true" if this dataset has labels in first row.
     * @param delimiter Delimiter in the CSV file.
     * @param timeStampIndex Number of column containing timestamp.
     * @param expectedException Name of expected exception. "none" in case of dataset that should open properly.
     */
    @ParameterizedTest
    @CsvFileSource(files = ".\\test\\resources\\openCSVtest.csv"  )
    public void testOpeningDatasets(String filePath, String embeddedLabels, String delimiter, int timeStampIndex, String expectedException) {
        BasicDataSet dataSet = new BasicDataSet();
        boolean el = embeddedLabels.compareTo("true") == 0;
        try {
            dataSet.openCSV(filePath,el,delimiter,timeStampIndex);
            if(expectedException.compareTo("none")!=0)
                fail("Opening of dataset didn't fail, even though it was either incorrect dataset or incorrect open parameters were provided.");
        } catch (DatasetFileOpenException e){
            if(expectedException.compareTo("DatasetFileOpenException")!=0)
                fail("Open method shouldn't throw DatasetFileOpenException in ths case, but it failed to.");
        }
        catch (IOException e){
            if(expectedException.compareTo("IOException")!=0)
                fail("Open method shouldn't throw IOException in ths case, but it failed to.");
        }
        catch (Exception e){
            fail("Open method threw unexpected exception.");
        }
    }



    /**
     * Method that tests opening dataset out of a CSV file with nulls instead of proper parameters.
     *
     * @param knownDataSetPath Path to proper dataset file.
     */
    @ParameterizedTest
    @ValueSource(strings = {".\\test\\resources\\knowndataset.csv"})
    public void testOpeningDatasetsWithNulls(String knownDataSetPath) {
        BasicDataSet dataSet = new BasicDataSet();
        try{
            dataSet.openCSV(null,true,";",1);
            fail("Open method didn't throw exception despite receiving null instead of file name");
        }catch(Exception e){}
        try{
            dataSet.openCSV(knownDataSetPath,true,null,1);
            fail("Open method didn't throw exception despite receiving null instead of delimiter");
        }catch(Exception e){}
    }



}
