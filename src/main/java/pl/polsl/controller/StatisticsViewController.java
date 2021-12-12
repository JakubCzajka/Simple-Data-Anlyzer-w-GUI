package pl.polsl.controller;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import pl.polsl.model.DataSet;
import pl.polsl.model.DataSetManipulator;
import pl.polsl.model.Statistics;
import pl.polsl.model.exceptions.DataSetNotOpenedException;
import pl.polsl.model.exceptions.NoFieldFoundException;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

@SuppressWarnings({"ClassEscapesDefinedScope"})
public class StatisticsViewController extends DataSetController implements Initializable {
    @FXML
    private TableView<Statistics> statisticsTableView;

    @FXML
    private ChoiceBox<String> fieldChoiceBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TableColumn<Statistics, String> fieldNameColumn = new TableColumn<>("Field Name");
        fieldNameColumn.setCellValueFactory(statistics -> {
            final String fieldName = statistics.getValue().getFieldName();
            return new ReadOnlyStringWrapper(fieldName);
        });

        TableColumn<Statistics, Double> minimumColumn = new TableColumn<>("Minimum");
        minimumColumn.setCellValueFactory(statistics -> {
            final Double fieldMinimum = statistics.getValue().getMinimum();
            return new SimpleDoubleProperty(fieldMinimum).asObject();
        });

        TableColumn<Statistics, Double> meanColumn = new TableColumn<>("Mean");
        meanColumn.setCellValueFactory(statistics -> {
            final Double fieldMean = statistics.getValue().getMean();
            return new SimpleDoubleProperty(fieldMean).asObject();
        });

        TableColumn<Statistics, Double> medianColumn = new TableColumn<>("Median");
        medianColumn.setCellValueFactory(statistics -> {
            final Double fieldMedian = statistics.getValue().getMedian();
            return new SimpleDoubleProperty(fieldMedian).asObject();
        });

        TableColumn<Statistics, Double> maximumColumn = new TableColumn<>("Maximum");
        maximumColumn.setCellValueFactory(statistics -> {
            final Double fieldMaximum = statistics.getValue().getMaximum();
            return new SimpleDoubleProperty(fieldMaximum).asObject();
        });

        TableColumn<Statistics, Integer> noOfMissingValuesColumn = new TableColumn<>("Number of missing values");
        noOfMissingValuesColumn.setCellValueFactory(statistics -> {
            final Integer numberOfMissingValues = statistics.getValue().getNumberOfMissingValues();
            return new SimpleIntegerProperty(numberOfMissingValues).asObject();
        });

        statisticsTableView.getColumns().add(fieldNameColumn);
        statisticsTableView.getColumns().add(minimumColumn);
        statisticsTableView.getColumns().add(meanColumn);
        statisticsTableView.getColumns().add(medianColumn);
        statisticsTableView.getColumns().add(maximumColumn);
        statisticsTableView.getColumns().add(noOfMissingValuesColumn);
    }

    @Override
    public void setDataSet(DataSet dataSet, DataSetManipulator dataSetManipulator) {
        super.setDataSet(dataSet, dataSetManipulator);

        try {
            Vector<String> fieldNames = dataSet.getFieldNames();

            fieldChoiceBox.getItems().add("ALL FIELDS");

            for (String fieldName : fieldNames)
                fieldChoiceBox.getItems().add(fieldName);

            fieldChoiceBox.setValue("ALL FIELDS");


        } catch (DataSetNotOpenedException e) {
            this.showPrompt(e.getMessage(),"Go to opening dataset screen","open-dataset-view.fxml");
        }
    }

    @FXML
    public void generateStatistics() {
        String selectedFieldName = fieldChoiceBox.getValue();
        statisticsTableView.getItems().clear();

        try {
            if (selectedFieldName.compareTo("ALL FIELDS") == 0) {
                Vector<Statistics> allStatistics = dataSetManipulator.getStatistics(dataSet, null, null);
                for (Statistics fieldStatistics : allStatistics)
                    statisticsTableView.getItems().add(fieldStatistics);
            } else {
                Statistics fieldStatistics = dataSetManipulator.getStatistics(dataSet, selectedFieldName, null, null);
                statisticsTableView.getItems().add(fieldStatistics);
            }

            statisticsTableView.setVisible(true);
        } catch (NoFieldFoundException e) {
            this.showPrompt(e.getMessage(),"OK","statistics-view.fxml");
        } catch (DataSetNotOpenedException e) {
            this.showPrompt(e.getMessage().concat("Go to opening dataset screen"),"OK", "open-dataset-view.fxml");
        }
    }

    @FXML
    public void goBack() {
        super.switchToScene("main-view.fxml");
    }
}
