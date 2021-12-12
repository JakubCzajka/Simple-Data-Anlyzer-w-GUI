package pl.polsl.controller;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import pl.polsl.model.DataSet;
import pl.polsl.model.DataSetManipulator;
import pl.polsl.model.Statistics;
import pl.polsl.model.exceptions.DataSetNotOpenedException;
import pl.polsl.model.exceptions.NoFieldFoundException;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ResourceBundle;
import java.util.Vector;

/**
 * Controller for statistics' window.
 *
 * @version 1.2
 */
@SuppressWarnings({"ClassEscapesDefinedScope"})
public class StatisticsViewController extends DataSetController implements Initializable {
    /**
     *  TableView containing generated statistics.
     */
    @FXML
    private TableView<Statistics> statisticsTableView;

    /**
     *  ChoiceBox used to get field name (or indication that user wants to get statistics for all fields).
     */
    @FXML
    private ChoiceBox<String> fieldChoiceBox;

    /**
     *  CheckBox indicating whether user wants to get statistics from certain date.
     */
    @FXML
    private CheckBox startDateCheckBox;

    /**
     *  CheckBox indicating whether user wants to get statistics to certain date.
     */
    @FXML
    private CheckBox endDateCheckBox;

    /**
     *  DatePicker containing the lower time bound for statistics.
     */
    @FXML
    private DatePicker startDatePicker;

    /**
     *  DatePicker containing the upper time bound for statistics.
     */
    @FXML
    private DatePicker endDatePicker;

    /**
     * Method that initializes statisticsTableView columns, sets listeners for CheckBoxes and default values for DatePickers.
     */
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



        startDateCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                                                             @Override
                                                             public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                                                                 startDatePicker.setDisable(!newValue);
                                                             }
                                                         });
        endDateCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                                                            @Override
                                                            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                                                                endDatePicker.setDisable(!newValue);
                                                            }
                                                        });

        startDatePicker.setValue(LocalDate.now());
        endDatePicker.setValue(LocalDate.now());
    }

    /**
     * Method that sets user DataSet and DataSetManipulator. It also sets the contents and default value of fieldChoiceBox.
     *
     * @see DataSetController
     *
     * @param dataSet            User DataSet. If null, an empty BasicDataSet is created.
     * @param dataSetManipulator Chosen DataSetManipulator. If null, BasicDataSetManipulator.
     */
    @Override
    public void setDataSet(DataSet dataSet, DataSetManipulator dataSetManipulator) {
        super.setDataSet(dataSet, dataSetManipulator);

        try {
            Vector<String> fieldNames = dataSet.getFieldNames();

            fieldChoiceBox.getItems().add("<<ALL FIELDS>>");

            for (String fieldName : fieldNames)
                fieldChoiceBox.getItems().add(fieldName);

            fieldChoiceBox.setValue("<<ALL FIELDS>>");


        } catch (DataSetNotOpenedException e) {
            this.showPrompt(e.getMessage(),"Go to opening dataset screen","open-dataset-view.fxml");
        }
    }

    /**
     *  Method that is executed after user presses the "generate" button. Calculates requested statistics and shows them in statisticsTableView.
     */
    @FXML
    public void generateStatistics() {
        String selectedFieldName = fieldChoiceBox.getValue();

        Instant startDateInstant = Instant.MIN;
        Instant endDateInstant = Instant.MAX;

        if(startDateCheckBox.isSelected())
            startDateInstant = Instant.from(startDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()));

        if(endDateCheckBox.isSelected())
            endDateInstant = Instant.from(endDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()));

        statisticsTableView.getItems().clear();
        try {
            if (selectedFieldName.compareTo("<<ALL FIELDS>>") == 0) {
                Vector<Statistics> allStatistics = dataSetManipulator.getStatistics(dataSet,
                        startDateInstant,
                        endDateInstant);
                for (Statistics fieldStatistics : allStatistics)
                    statisticsTableView.getItems().add(fieldStatistics);
            } else {
                Statistics fieldStatistics = dataSetManipulator.getStatistics(dataSet,
                        selectedFieldName,
                        startDateInstant,
                        endDateInstant);
                statisticsTableView.getItems().add(fieldStatistics);
            }

            statisticsTableView.setVisible(true);
        } catch (NoFieldFoundException e) {
            this.showPrompt(e.getMessage(),"OK","statistics-view.fxml");
        } catch (DataSetNotOpenedException e) {
            this.showPrompt(e.getMessage().concat("\nGo to opening dataset screen"),"OK", "open-dataset-view.fxml");
        }
    }

    /**
     *
     */
    @FXML
    public void goBack() {
        super.switchToScene("main-view.fxml");
    }
}
