package pl.polsl.model.basicmodel;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;


import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * Class containing unit tests for BasicStatistics class
 *
 * @author Jakub Czajka
 * @version 1.2
 */
public class BasicStatisticsTest {



    /**
     * Method that tests calculating statistics for given field.
     *
     * @param field Field with known statistics.
     * @param expectedFieldName Proper name of the field.
     * @param expectedMean Proper mean of field values.
     * @param expectedMedian Proper median of field values.
     * @param expectedMinimum Proper minimum of field values.
     * @param expectedMaximum Proper maximum of field values.
     * @param expectedNumberOfNaNs Proper number of missing values of field values.
     */
    @ParameterizedTest
    @MethodSource("fieldAndResultsProvider")
    public void testBasicStatistics(Field<Double> field, String expectedFieldName, Double expectedMean, Double expectedMedian, Double expectedMinimum, Double expectedMaximum, int expectedNumberOfNaNs) {
        BasicStatistics statistics = new BasicStatistics(field);
        assertEquals(statistics.getFieldName(), expectedFieldName, "Statistics saved incorrect field name.");
        assertEquals(statistics.getMean(), expectedMean, 0.01, "Statistics calculated incorrect mean.");
        assertEquals(statistics.getMedian(), expectedMedian, 0.01, "Statistics calculated incorrect median.");
        assertEquals(statistics.getMinimum(), expectedMinimum, 0.01, "Statistics calculated incorrect minimum.");
        assertEquals(statistics.getMaximum(), expectedMaximum, 0.01, "Statistics calculated incorrect maximum.");
        assertEquals(statistics.getNumberOfMissingValues(), expectedNumberOfNaNs, "Statistics calculated incorrect number of NaNs.");
    }

    /**
     * Arguments provider for testBasicStatistics test.
     *
     * @return Stream of fields and their corresponding statistics.
     */
    static Stream<Arguments> fieldAndResultsProvider() {
        Field<Double> fullFieldOdd = new Field<>("fullFieldOdd");
        fullFieldOdd.addValue(-1.0);
        fullFieldOdd.addValue(2.0);
        fullFieldOdd.addValue(4.0);

        Field<Double> fullFieldEven = new Field<>("fullFieldEven");
        fullFieldEven.addValue(2.0);
        fullFieldEven.addValue(3.0);
        fullFieldEven.addValue(4.0);
        fullFieldEven.addValue(-1.0);

        Field<Double> emptyField = new Field<>("emptyField");

        Field<Double> fieldWithNaNValues = new Field<>("fieldWithNaNValues");
        fieldWithNaNValues.addValue(-1.0);
        fieldWithNaNValues.addValue(Double.NaN);
        fieldWithNaNValues.addValue(4.0);

        Field<Double> fieldWithNaNValuesOnly = new Field<>("fieldWithNaNValuesOnly");
        fieldWithNaNValuesOnly.addValue(Double.NaN);
        fieldWithNaNValuesOnly.addValue(Double.NaN);
        fieldWithNaNValuesOnly.addValue(Double.NaN);

        return Stream.of(arguments(fullFieldOdd, "fullFieldOdd", 1.667, 2.0, -1.0, 4.0, 0),
                arguments(fullFieldEven, "fullFieldEven", 2.0, 2.5, -1.0, 4.0, 0),
                arguments(emptyField, "emptyField", Double.NaN, Double.NaN, Double.NaN, Double.NaN, 0),
                arguments(fieldWithNaNValues, "fieldWithNaNValues", 1.5, 1.5, -1.0, 4.0, 1),
                arguments(fieldWithNaNValuesOnly, "fieldWithNaNValuesOnly", Double.NaN, Double.NaN, Double.NaN, Double.NaN, 3),
                arguments(null, "Field not found", Double.NaN, Double.NaN, Double.NaN, Double.NaN, 0));
    }
}
