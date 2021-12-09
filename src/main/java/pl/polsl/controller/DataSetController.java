package pl.polsl.controller;

import javafx.stage.Stage;
import pl.polsl.model.DataSet;
import pl.polsl.model.DataSetManipulator;

@SuppressWarnings("ClassEscapesDefinedScope")
public interface DataSetController {
    void setDataSet(DataSet dataSet, DataSetManipulator dataSetManipulator);
    void setStage(Stage stage);
}
