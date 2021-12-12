package pl.polsl.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;


/**
 * Controller for prompt window.
 *
 * @version 1.2
 */
public class PromptViewController extends DataSetController {
    /**
     * Prompt to be shown to user.
     */
    @FXML
    private TextArea promptTextArea;

    /**
     * Prompt button.
     */
    @FXML
    private Button promptButton;

    /**
     * Path to fxml file containing next active window.
     */
    private String nextSceneFxmlPath;

    /**
     * Setter for nextSceneFxmlPath.
     *
     * @param nextSceneFxmlPath New nextSceneFxmlPath value.
     */
    public void setNextSceneFxmlPath(String nextSceneFxmlPath) {
        this.nextSceneFxmlPath = nextSceneFxmlPath;
    }

    /**
     * Setter for promptButton text.
     *
     * @param buttonLabel New promptButton text.
     */
    public void setButtonLabel(String buttonLabel) {
        this.promptButton.setText(buttonLabel);
    }

    /**
     * Setter for promptTextArea text.
     *
     * @param promptLabel New promptTextArea text.
     */
    public void setPromptLabel(String promptLabel) {
        this.promptTextArea.setText(promptLabel);
    }

    /**
     * Method that is executed after user presses the promptButton. Switches to window described in nextSceneFxmlPath.
     */
    public void buttonPressed() {
        super.switchToScene(nextSceneFxmlPath);
    }
}
