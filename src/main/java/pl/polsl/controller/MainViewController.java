package pl.polsl.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import pl.polsl.model.DataSet;
import pl.polsl.model.DataSetManipulator;

import java.io.IOException;


@SuppressWarnings("ClassEscapesDefinedScope")
public class MainViewController extends DataSetController {

    @FXML
    private Button statisticsButton;

    @Override
    public void setDataSet(DataSet dataSet, DataSetManipulator dataSetManipulator) {
        super.setDataSet(dataSet, dataSetManipulator);
        this.setStatisticsAvailability();
    }

    @FXML
    public void onClickOpenDataSetButton() throws IOException {
        super.switchToScene("open-dataset-view.fxml");
    }

    @FXML
    public void onClickStatisticsButton() throws IOException {
        super.switchToScene("statistics-view.fxml");
    }

    private void setStatisticsAvailability() {
        if (dataSet == null) {
            statisticsButton.setDisable(true);
        } else statisticsButton.setDisable(!dataSet.isOpen());
    }
}