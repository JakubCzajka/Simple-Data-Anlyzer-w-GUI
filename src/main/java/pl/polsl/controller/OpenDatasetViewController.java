package pl.polsl.controller;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import pl.polsl.model.exceptions.DatasetFileOpenException;

import java.io.IOException;


public class OpenDatasetViewController extends DataSetController {
    @FXML
    private TextField pathTextField;

    @FXML
    private TextField delimiterTextField;

    @FXML
    private TextField timestampTextField;

    @FXML
    private CheckBox labelsCheckBox;

    @FXML
    public void onClickLoadButton() throws IOException {
        boolean embeddedLabels = labelsCheckBox.isSelected();
        String path = pathTextField.getText();
        String delimiter = delimiterTextField.getText();
        int timestampIndex = Integer.parseInt(timestampTextField.getText());

        try {
            String message = dataSet.openCSV(path,embeddedLabels,delimiter,timestampIndex);
            this.showPrompt(message, "OK", "main-view.fxml");
        } catch (IOException | DatasetFileOpenException e) {
            this.showPrompt("Failed to open dataset.\nError details:\n".concat(e.getMessage()), "OK", "open-dataset-view.fxml");
        }


    }
}
