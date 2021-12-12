package pl.polsl.controller;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import pl.polsl.model.exceptions.DatasetFileOpenException;

import java.io.IOException;


/**
 * Controller for dataset opening window.
 *
 * @version 1.2
 */
public class OpenDatasetViewController extends DataSetController {
    /**
     * Text field that is used to get dataset location from user.
     */
    @FXML
    private TextField pathTextField;

    /**
     * Text field that is used to get CSV file delimiter form user.
     */
    @FXML
    private TextField delimiterTextField;

    /**
     * Text field that is used to get timestamp column index from user.
     */
    @FXML
    private TextField timestampTextField;

    /**
     * Checkbox indicting whether dataset contains labels or not.
     */
    @FXML
    private CheckBox labelsCheckBox;

    /**
     * Method executed after user presses the open dataset button. It tries to open the dataset.
     */
    @FXML
    public void onClickLoadButton(){
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
