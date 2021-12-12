package pl.polsl.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import pl.polsl.model.DataSet;
import pl.polsl.model.DataSetManipulator;

import java.io.IOException;


/**
 * Controller for main app view.
 *
 * @version 1.4
 */
@SuppressWarnings("ClassEscapesDefinedScope")
public class MainViewController extends DataSetController {

    /**
     * Button that redirects to statistics' window.
     */
    @FXML
    private Button statisticsButton;

    @Override
    public void setDataSet(DataSet dataSet, DataSetManipulator dataSetManipulator) {
        super.setDataSet(dataSet, dataSetManipulator);

        if (dataSet == null) {
            statisticsButton.setDisable(true);
        } else statisticsButton.setDisable(!dataSet.isOpen());
    }

    /**
     * Method that switches window to dataset opening window.
     */
    @FXML
    public void onClickOpenDataSetButton() {
        super.switchToScene("open-dataset-view.fxml");
    }

    /**
     * Method that switches window to statistics' window.
     */
    @FXML
    public void onClickStatisticsButton(){
        super.switchToScene("statistics-view.fxml");
    }
}