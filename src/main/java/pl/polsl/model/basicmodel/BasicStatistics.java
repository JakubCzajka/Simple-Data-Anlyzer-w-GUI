package pl.polsl.model.basicmodel;

import pl.polsl.model.Statistics;

import java.util.Collections;
import java.util.Vector;

/**
 * Basic implementation of Statistics interface.
 *
 * @author Jakub Czajka
 * @version 1.7
 */
final class BasicStatistics implements Statistics {
    /**
     * Field name.
     */
    private String fieldName;
    /**
     * Mean of all not-empty elements of selected field.
     */
    private double mean;
    /**
     * Median of all not-empty elements of selected field.
     */
    private double median;
    /**
     * Minimum value of all not-empty elements of selected field.
     */
    private double minimum;
    /**
     * Maximum value of all not-empty elements of selected field.
     */
    private double maximum;
    /**
     * number of empty elements in the field.
     */
    private int numberOfNaN;


    /**
     * Constructor that calculates this class fields based on given numeric Field.
     *
     * @param numericField Field used to calculate statistics.
     */
    public BasicStatistics(Field<Double> numericField) {
        if (numericField != null) {
            this.fieldName = numericField.getName();
            Vector<Double> values = numericField.copyValues();
            this.numberOfNaN = 0;
            if (!values.isEmpty()) {
                Collections.sort(values);

                double sum = 0.0;
                for (Double value : values) {
                    if (value.compareTo(Double.NaN) == 0)
                        this.numberOfNaN += 1;
                    else
                        sum = sum + value;
                }
                if (this.numberOfNaN == values.size()) {
                    this.setNaN();
                } else {
                    int adjustedSize = values.size() - this.numberOfNaN;
                    this.minimum = values.elementAt(0);
                    this.maximum = values.elementAt(adjustedSize - 1);
                    this.mean = sum / adjustedSize;
                    int midIndex = adjustedSize / 2;
                    if ((adjustedSize % 2) == 0)
                        this.median = (values.elementAt(midIndex) + values.elementAt(midIndex - 1)) / 2.0;
                    else
                        this.median = values.elementAt(midIndex);
                }

            } else {
                this.setNaN();
            }
        } else {
            this.fieldName = "Field not found";
            this.setNaN();
        }
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }

    @Override
    public Double getMedian() {
        return this.median;
    }

    @Override
    public Double getMean() {
        return this.mean;
    }

    @Override
    public Double getMinimum() {
        return this.minimum;
    }

    @Override
    public Double getMaximum() {
        return this.maximum;
    }

    @Override
    public int getNumberOfMissingValues() {
        return this.numberOfNaN;
    }

    /**
     * Method that sets all Double fields to Double.NaN
     */
    private void setNaN() {
        this.minimum = Double.NaN;
        this.maximum = Double.NaN;
        this.mean = Double.NaN;
        this.median = Double.NaN;
    }
}
