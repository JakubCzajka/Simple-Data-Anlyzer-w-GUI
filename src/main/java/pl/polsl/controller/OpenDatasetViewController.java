package pl.polsl.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.polsl.model.DataSet;
import pl.polsl.model.DataSetManipulator;
import pl.polsl.model.basicmodel.BasicDataSet;
import pl.polsl.model.basicmodel.BasicDataSetManipulator;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

@SuppressWarnings("ClassEscapesDefinedScope")
public class OpenDatasetViewController implements Initializable, DataSetController {

    private DataSet dataSet;

    private DataSetManipulator dataSetManipulator;

    private Stage stage;

    @FXML
    private TextField pathTextField;

    @FXML
    private TextField delimiterTextField;

    @FXML
    private TextField timestampTextField;

    @FXML
    private CheckBox labelsCheckBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
    public void onClickLoadButton() {
        boolean embeddedLabels = labelsCheckBox.isSelected();
        String path = pathTextField.getText();
        String delimiter = pathTextField.getText();
        int timestampIndex = Integer.parseInt(timestampTextField.getText());




    }
}
