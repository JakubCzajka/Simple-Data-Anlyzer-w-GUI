package pl.polsl.model.basicmodel;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
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
import static pl.polsl.model.basicmodel.BasicDataSetTest.knownDataSet;

/**
 * Class containing tests for BasicDataSetManipulator.
 *
 * @version 1.1
 */
public class BasicManipulatorTest {

    /**
     * Method that tests getting statistics for a single field.
     *
     * @param fieldName Name of the field.
     * @param from Lower bound for time of statistics.
     * @param to Upper bound for time of statistics.
     * @param expectedMean Proper mean of field values.
     * @param expectedMedian Proper median of field values.
     * @param expectedMinimum Proper minimum of field values.
     * @param expectedMaximum Proper maximum of field values.
     * @param expectedNumberOfNaNs Proper number of missing values of field values.
     */
    @ParameterizedTest
    @CsvFileSource(files = ".\\test\\resources\\knowndatasetstatistics.csv"  )
    public void testGettingFieldStatistics(String fieldName, Instant from, Instant to, Double expectedMean, Double expectedMedian, Double expectedMinimum, Double expectedMaximum, int expectedNumberOfNaNs) {
        try {
            BasicDataSetManipulator manipulator = new BasicDataSetManipulator();
            Statistics statistics = manipulator.getStatistics(knownDataSet, fieldName,from,to);
            assertEquals(statistics.getFieldName(), fieldName, "Statistics saved incorrect field name.");
            assertEquals(statistics.getMean(), expectedMean, 0.01, "Statistics calculated incorrect mean.");
            assertEquals(statistics.getMedian(), expectedMedian, 0.01, "Statistics calculated incorrect median.");
            assertEquals(statistics.getMinimum(), expectedMinimum, 0.01, "Statistics calculated incorrect minimum.");
            assertEquals(statistics.getMaximum(), expectedMaximum, 0.01, "Statistics calculated incorrect maximum.");
            assertEquals(statistics.getNumberOfMissingValues(), expectedNumberOfNaNs, "Statistics calculated incorrect number of NaNs.");
        } catch (NoFieldFoundException | DataSetNotOpenedException e) {
            fail("Getting field statistics threw exception despite receiving proper dataset and parameters.");
        }
    }



    /**
     * Method that tests getting statistics for a single field, but with nulls instead of proper parameters.
     *
     * @param testedDataSet Known dataset.
     * @param fieldName Name of the field.
     * @param exceptionName Name of expected exception to occur.
     */
    @ParameterizedTest
    @MethodSource("fieldStatisticsProvider")
    public void testGettingFieldStatisticsExceptions(BasicDataSet testedDataSet, String fieldName, String exceptionName){
        BasicDataSetManipulator manipulator = new BasicDataSetManipulator();
        try {
            Statistics stats = manipulator.getStatistics(testedDataSet, fieldName,Instant.MIN, Instant.MAX);
            fail("getStatistics method didn't throw exception, even if it was supposed to.");
        } catch (NoFieldFoundException e) {
            if(exceptionName.compareTo("NoFieldFoundException")!=0)
                fail("getStatistics method threw incorrect exception.");
        } catch (DataSetNotOpenedException e) {
            if(exceptionName.compareTo("DataSetNotOpenedException")!=0)
                fail("getStatistics method threw incorrect exception.");
        }
    }

    /*
     * Method that supplies testGettingFieldStatisticsExceptions.
     */

    /**
     * Method that supplies testGettingFieldStatisticsExceptions.
     *
     * @return Stream of arguments for testGettingFieldStatisticsExceptions.
     */
    static Stream<Arguments> fieldStatisticsProvider(){
        BasicDataSet knownDataSet = new BasicDataSet();
        try {
            knownDataSet.openCSV(".\\test\\resources\\knowndataset.csv",true,";",1);
        } catch (IOException | DatasetFileOpenException e) {
            e.printStackTrace();
        }
        BasicDataSet emptyDataSet = new BasicDataSet();
        return Stream.of(arguments(knownDataSet, "ThisFieldIsNotInDataSet", "NoFieldFoundException"),
                arguments(knownDataSet, null, "NoFieldFoundException"),
                arguments(emptyDataSet, "Field0", "DataSetNotOpenedException"));
    }

    /**
     * Method that tests getting statistics for a single field, but with nulls instead of proper parameters.
     * Please note that due to structure of tested method, it requires no further tests, as it's done by testing getting statistics out of single field.
     */
    @Test
    public void testGettingAllFieldStatisticsExceptions() {
        BasicDataSet emptyDataSet = new BasicDataSet();
        BasicDataSetManipulator manipulator = new BasicDataSetManipulator();
        try {
            Vector<Statistics> stats = manipulator.getStatistics(emptyDataSet, Instant.MIN, Instant.MAX);
            fail("getStatistics method didn't throw exception, even if it was supposed to.");
        } catch (DataSetNotOpenedException e) {
        }
    }
}
