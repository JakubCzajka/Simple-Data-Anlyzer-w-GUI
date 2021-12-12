package pl.polsl.model.basicmodel;

import pl.polsl.model.DataSet;
import pl.polsl.model.DataSetManipulator;
import pl.polsl.model.Statistics;
import pl.polsl.model.exceptions.DataSetNotOpenedException;
import pl.polsl.model.exceptions.NoFieldFoundException;

import java.time.Instant;
import java.util.Vector;

public class BasicDataSetManipulator implements DataSetManipulator {
    @Override
    public Vector<Statistics> getStatistics(DataSet dataSet, Instant from, Instant to) throws DataSetNotOpenedException {
        if (!dataSet.isOpen())
            throw new DataSetNotOpenedException();


        Vector<Statistics> result = new Vector<Statistics>();
        try {
            Vector<String> fieldNames = dataSet.getFieldNames();

            for (String fieldName : fieldNames) {
                result.add(this.getStatistics(dataSet, fieldName, from, to));
            }
        } catch (NoFieldFoundException e) {
            e.printStackTrace(); //that should not happen...
        }
        return result;
    }

    @Override
    public Statistics getStatistics(DataSet dataSet, String fieldName, Instant from, Instant to) throws NoFieldFoundException, DataSetNotOpenedException {
        if (!dataSet.isOpen())
            throw new DataSetNotOpenedException();
        if (fieldName == null)
            throw new NoFieldFoundException("null");
        if (from == null)
            from = Instant.MIN;
        if (to == null)
            to = Instant.MAX;

        if(from.isAfter(to))
        {
            Instant temp = from;
            from = to;
            to = temp;
        }


        Vector<Double> fieldValues = dataSet.getFieldCopy(fieldName);
        Vector<Instant> timeStamps = dataSet.getTimeStampsCopy();

        Vector<Double> filteredValues = new Vector<>();

        for (int i = 0; i < timeStamps.size(); i++) {
            if (timeStamps.get(i).isAfter(from) && timeStamps.get(i).isBefore(to))
                filteredValues.add(fieldValues.elementAt(i));
        }

        Field<Double> filteredField = new Field<>(fieldName, filteredValues);
        return new BasicStatistics(filteredField);
    }
}
