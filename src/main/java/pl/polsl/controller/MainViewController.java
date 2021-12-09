package pl.polsl.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import pl.polsl.DataSetAnalyzerApplication;
import pl.polsl.model.DataSet;
import pl.polsl.model.DataSetManipulator;
import pl.polsl.model.basicmodel.BasicDataSet;
import pl.polsl.model.basicmodel.BasicDataSetManipulator;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

@SuppressWarnings("ClassEscapesDefinedScope")
public class MainViewController implements Initializable, DataSetController {
    @SuppressWarnings("FieldCanBeLocal")
    private DataSet dataSet;

    private DataSetManipulator dataSetManipulator;

    private Stage stage;

    @FXML
    private HBox mainView;

    @FXML
    private Button statisticsButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setStatisticsAvailability();
    }

    @Override
    public void setDataSet(DataSet dataSet, DataSetManipulator dataSetManipulator) {
        this.dataSet = Objects.requireNonNullElseGet(dataSet, BasicDataSet::new);
        this.dataSetManipulator = Objects.requireNonNullElseGet(dataSetManipulator, BasicDataSetManipulator::new);
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void onClickOpenDataSetButton() throws IOException {
        //statisticsButton.setDisable(false);
        FXMLLoader fxmlLoader = new FXMLLoader(DataSetAnalyzerApplication.class.getResource("open-dataset-view.fxml"));
        Scene openDataSetScene = new Scene(fxmlLoader.load(), 500, 300);

        DataSetController nextController = fxmlLoader.getController();

        nextController.setDataSet(dataSet, dataSetManipulator);
        nextController.setStage(stage);

        stage.setScene(openDataSetScene);
    }

    @FXML
    public void onClickStatisticsButton() {
    }

    private void setStatisticsAvailability() {
        if (dataSet == null) {
            statisticsButton.setDisable(true);
        } else statisticsButton.setDisable(!dataSet.isOpen());
    }
}